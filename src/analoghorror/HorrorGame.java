package analoghorror;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;

import java.awt.Color;
import java.io.File;

import analoghorror.inhabitants.*;
import analoghorror.rooms.*;

public class HorrorGame {
    private static final int CANVAS_WIDTH = 854;
    private static final int CANVAS_HEIGHT = 480;

    // Booleans to manage one-time events.
    private boolean timerStarted;
    private boolean laptopSpawned;
    private boolean finalMonster;
    private boolean bagpipeNoise;
    private boolean handScare;

    private double randomDouble;  // Controls eventual laptop location and what hand texture you get.
    private long startTime;
    
    private CanvasWindow canvas;

    // The layers drawn upon canvas to isolate elements for click logic.
    private Room activeRoom;
    private Inventory inventory;
    private GraphicsGroup cursor;
    private GraphicsGroup displayOverlay;
    private GraphicsGroup timer;

    private GraphicsText timerText;

    private Cursor activeCursor;
    private Collectable hand;

    // Every game room.
    private HallwayRoom hallway;
    private GreenChairsRoom greenChairsRoom;
    private LectureHallRoom lectureHallRoom;
    private WindowedClassRoom windowedClassRoom;

    private Sound primarySound;
    private Sound secondarySound;


    public static void main(String[] args) {
        new HorrorGame();
    }

    /**
     * An analog horror inspired point-and-click game. For the final project
     * assigned in COMP 127 with Professor Marsh.
     * 
     * This class handles the larger big-picture logic and game events that aren't tied to specific rooms.
     * 
     * @Author Daisy Chan, Moyartu Manley, Wren Kuratana
     */
    public HorrorGame() {
        // Initialize ↓
        timerStarted = false;
        laptopSpawned = false;
        finalMonster = false;
        bagpipeNoise = false;
        handScare = false;

        randomDouble = Math.random();

        primarySound = new Sound();
        secondarySound = new Sound();
        primarySound.playSound("res" + File.separator + "assets" + File.separator + "audio" + File.separator + "ambientBG.wav");

        canvas = new CanvasWindow("Biology Majors Be Like", CANVAS_WIDTH, CANVAS_HEIGHT);
        cursor = new GraphicsGroup();

        handAssignment();

        activeCursor = new Cursor(hand);
        activeCursor.resetCursor();

        cursor.add(activeCursor);

        displayOverlay = new GraphicsGroup();
        timer = new GraphicsGroup();
        timerText = new GraphicsText("", 23, 39);
        timerText.setFillColor(Color.RED);
        timerText.setFontSize(30);

        timer.add(timerText);

        inventory = new Inventory(0, 0, 742, 68, "assets" + File.separator + "inventoryBar.png");
        inventory.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT - inventory.getHeight() / 2);

        hallway = new HallwayRoom(hand,
            "assets" + File.separator + "HallwayRoom" + File.separator + "hallwayRoomBG.png", inventory,
            displayOverlay, primarySound);
        greenChairsRoom = new GreenChairsRoom(hallway, hand,
            "assets" + File.separator + "GreenChairsRoom" + File.separator + "defaultGreenChairsRoomBG.png", inventory,
            displayOverlay, primarySound);
        lectureHallRoom = new LectureHallRoom(hallway, hand,
            "assets" + File.separator + "LectureHallRoom" + File.separator + "lectureHallRoomBG.png", inventory,
            displayOverlay, primarySound);
        windowedClassRoom = new WindowedClassRoom(hallway, hand,
            "assets" + File.separator + "WindowedClassRoom" + File.separator + "windowedClassRoomBG.png", inventory,
            displayOverlay, primarySound);

        hallway.addChairClassroom(greenChairsRoom);
        hallway.addLectureHall(lectureHallRoom);
        hallway.addWindowedClassroom(windowedClassRoom);

        activeRoom = hallway.getActiveRoom();

        canvas.add(activeRoom);
        canvas.add(inventory);
        canvas.add(cursor);
        canvas.add(displayOverlay);
        canvas.add(timer);
        // canvas.draw();

        activeRoom.updateRoom();
        // *****

        gameLogic();
    }

    /**
     * Start the game; handles interaction updates.
     */
    private void gameLogic() {
        canvas.animate(() -> {
            // Check these regularly because they can't be implemented properly using updateRoom() in a Room due to when updateRoom() is called ↓
            if (inventory.getCollectableWithID("laptop") == null) {
                if (inventory.getCollectableWithID("rat01") != null) {
                    startTimer();
                    // If the laptop isn't in the inventory, but the rat is, then start the game timer and update the text.
                    canvas.draw();
                }
            } else {
                // If the laptop is in the inventory…
                timerText.setText("");
                bagpipeNoise();
                // Play scary pipe scream;
                finalScare();
                // Start the final monster sequence and play monster music when you first return to the hallway.
            }
            if (activeRoom == hallway && inventory.getCollectableWithID("lecturePoison") != null) {
                hallway.hallucination();
            }
        });
        canvas.onMouseMove(event -> {
            // Cursor follows the mouse.
            activeCursor.setCenter(event.getPosition());
        });
        canvas.draw();  // Draw everything from the initialization as well.
        canvas.onClick(event -> {
            clickLogic(event);
            // Perform checks and handle behavior on clicks.
        });
    }

    /**
     * Return a GraphicsObject if there is an element at the event position in the specified group.
     * 
     * Used with clieckCollectableItemInteractions() and clickInventoryCollectableInteractions();
     * 
     * @param event click
     * @param group layer to check
     * @return
     */
    private GraphicsObject check(MouseButtonEvent event, GraphicsGroup group) {
        GraphicsObject item = group.getElementAt(event.getPosition());
        return item;
    }

    /**
     * Called on every click.
     * 
     * Organizes interactions between collectables, items, and the inventory through method calls.
     * 
     * @param event click
     */
    private void clickLogic(MouseButtonEvent event){
        clickCollectableItemInteractions(event); 
        clickInventoryCollectableInteractions(event);
        spawnLaptop();  
        // Once you get the rat in the game, a laptop is spawned in a random location. Called after click logic that would give you the rat.
        canvas.draw();  
        // Update after interactions.
        activeRoom.updateRoom();  
        // Update the current room so new interactions can take place after prior calls.
        refreshRoom();
        // Redraw everything when you change rooms.
    }

    /**
     * Collect a Collectable if it isn't in the inventory already, or sets it as the cursor if you click on it in the inventory.
     * 
     * @param event click
     */
    private void clickInventoryCollectableInteractions(MouseButtonEvent event){
        if (check(event, activeRoom) instanceof Collectable && ((Collectable) check(event, cursor)).getIDString() == hand.getIDString()) {
            // If the element under the click is a Collectable…
            Collectable collectable = (Collectable) check(event, activeRoom);
            collectable.changePathOnCollection();
            inventory.acquireCollectable(collectable, activeCursor);
            activeRoom.getRoomInhabitants().remove(collectable);
            // It is added to inventory if it isn't already collected.
        }
        else if (check(event, inventory) instanceof Collectable && ((Collectable) check(event, cursor)).getIDString() == hand.getIDString()) {
            // If the element under the click is a Collectable and in Inventory…
            Collectable collectable = (Collectable) check(event, inventory);
            inventory.assignCollectable(collectable, cursor, activeCursor, event);
            // It becomes the cursor.
        }
    }

    /**
     * Interacts with the Item that is clicked on if the Collectable being used as a cursor is valid, then resets the Collectable.
     * If there isn't an item under it, the Collectable is reset.
     * 
     * @param event click
     */
    private void clickCollectableItemInteractions(MouseButtonEvent event){
        if (check(event, cursor) instanceof Collectable) {
            // If the Collectable is the cursor…
            Collectable collectable = (Collectable) check(event, cursor);
            if (check(event, activeRoom) instanceof Item) {
                // …and there is an Item underneath it…
                Item item = (Item) check(event, activeRoom);
                // Try to interact with the Item.
                if (collectable != hand) {
                    // Reset the cursor if there isn't an item under it and it isn't the hand.
                    item.interaction(collectable);
                    collectable.resetCursor(inventory, activeCursor);
                    activeCursor.setCenter(event.getPosition());
                    // Keep everything aligned.
                } else if (collectable == hand) {
                    item.interaction(collectable);
                }
            } else if (collectable != hand) {
                // Reset the cursor if there isn't an item under it and it isn't the hand.
                collectable.resetCursorIfOverRoom(event, activeCursor, inventory);
                activeCursor.setCenter(event.getPosition());
                // Keep everything aligned.
            }
        }
    }

    /**
     * A random hand is assigned to be used as the main cursor.
     * 
     * Each hand belongs to one of the developers.
     * 
     * There is a 10% chance your hand will be Pinky Pie's hoof.
     */
    private void handAssignment(){
        if (randomDouble < 0.3) {
            hand = new Collectable(0, 0, "assets" + File.separator + "hands" + File.separator + "daisyHand.png", "hand");
        }
        else if (randomDouble < 0.6) {
            hand = new Collectable(0, 0, "assets" + File.separator + "hands" + File.separator + "moyartuHand.png", "hand");
        }
        else if (randomDouble < 0.9) {
            hand = new Collectable(0, 0, "assets" + File.separator + "hands" + File.separator + "wrenHand.png", "hand");
        }
        else {
            hand = new Collectable(0, 0, "assets" + File.separator + "hands" + File.separator + "pinkyHand.png", "hand");
        }
    }

    /**
     * A random location is called to spawn a laptop just before the timer sequence.
     */
    private void spawnLaptop(){
        if (inventory.getCollectableWithID("rat01") != null && !laptopSpawned) {
            if (randomDouble < 0.2) {
                greenChairsRoom.spawnOpenLaptop();
                laptopSpawned = true;
            }
            else if (randomDouble < 0.4) {
                windowedClassRoom.spawnOpenLaptop();
                laptopSpawned = true;
            }
            else if (randomDouble < 0.6) {
                windowedClassRoom.spawnClosedLaptop();
                laptopSpawned = true;
            }
            else if (randomDouble < 0.8) {
                lectureHallRoom.spawnOpenLaptop();
                laptopSpawned = true;
            }
            else {
                lectureHallRoom.spawnClosedLaptop();
                laptopSpawned = true;
            }
        }
    }

    /**
     * Start a 30 second timer that will end the game if it reaches 0. Timer is updated each time it is called.
     */
    private void startTimer() {
        if (!timerStarted) {
            startTime = System.currentTimeMillis();
            timerStarted = true;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        long secondsDisplay = elapsedSeconds % 60;
        if (30 - secondsDisplay == 0 && !handScare) {
            // If the timer runs out, call the appropriate scare and remove the timer.
            activeRoom.jumpscare();
            timer.removeAll();
            handScare = true;
        }
        timerText.setText(
            "You have " + (30 - secondsDisplay) + " seconds.");
    }

    /**
     * Play the bagpipe noise once and overlay the appropriate asset.
     */
    private void bagpipeNoise(){
        if (!bagpipeNoise) {
            secondarySound.playSound("res" + File.separator + "assets" + File.separator + "audio" + File.separator + "pipe.wav");
            displayOverlay.add(new Image ("assets" + File.separator + "overlays" + File.separator + "bagpipeNoise.png"));
            bagpipeNoise = true;
        }
    }

    /**
     * Once you reenter the hallway, start the finalScare sequence in hallway and play the appropriate audio.
     */
    private void finalScare(){
        if (activeRoom == hallway && !finalMonster) {
            hallway.finalScare();
            primarySound = new Sound();
            primarySound.playSound("res" + File.separator + "assets" + File.separator + "audio" + File.separator + "franticHallway.wav");
            finalMonster = true;
        }
    }

    private void refreshRoom(){
        if (activeRoom != activeRoom.getActiveRoom()) {
            canvas.removeAll();
            activeRoom = activeRoom.getActiveRoom();
            canvas.add(activeRoom);
            canvas.add(inventory);
            canvas.add(cursor);
            canvas.add(displayOverlay);
            canvas.add(timer);
        }
    }

}
