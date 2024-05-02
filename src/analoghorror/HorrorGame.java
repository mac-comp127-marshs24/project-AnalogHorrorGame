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

    private CanvasWindow canvas;
    boolean timerStarted;
    boolean laptopSpawned;
    Collectable hand;
    Cursor activeCursor;
    GraphicsObject cursorDefault;
    GraphicsGroup cursor;
    GraphicsGroup displayOverlay;
    GraphicsGroup timer;
    long startTime;
    GraphicsText timerText;
    
    Image background;
    Inventory inventory;

    Room activeRoom;
    HallwayRoom hallway;
    GreenChairsRoom greenChairsRoom;
    LectureHallRoom lectureHallRoom;
    WindowedClassRoom windowedClassRoom;
    double randomDouble;

    public static void main(String[] args) {
        new HorrorGame();
    }

    public HorrorGame() {
        timerStarted = false;
        laptopSpawned = false;
        randomDouble = Math.random();
        canvas = new CanvasWindow("Game Test", CANVAS_WIDTH, CANVAS_HEIGHT);
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

        //given that we start in hallway, hallway should always have a val and shouldnt be null when greenchairs is called?
        hallway = new HallwayRoom(hand,"assets" + File.separator + "Corridor.png", displayOverlay);
        greenChairsRoom = new GreenChairsRoom(hallway, hand, "assets" + File.separator + "GreenChairsRoom" + File.separator + "defaultGreenChairsRoomBG.png", inventory, displayOverlay);
        lectureHallRoom = new LectureHallRoom(hallway, hand, "assets" + File.separator + "LectureHallRoom" + File.separator + "lectureHallRoomBG.png", inventory, displayOverlay);
        windowedClassRoom = new WindowedClassRoom(hallway, hand,  "assets" + File.separator + "WindowedClassRoom" + File.separator + "windowedClassRoomBG.png", inventory, displayOverlay);
        hallway.addChairClassroom(greenChairsRoom);
        hallway.addLectureHall(lectureHallRoom);
        hallway.addWindowedClassroom(windowedClassRoom);

        activeRoom = hallway.getActiveRoom();

        //activeRoom = new HallwayRoom(new GreenChairsRoom("assets" + File.separator + "hall.png"), hand,"assets" + File.separator + "hall.png");
    
        canvas.add(activeRoom);
        canvas.add(inventory);
        canvas.add(cursor);
        canvas.add(displayOverlay);
        canvas.add(timer);
        canvas.draw();
        gameLogic();
    }

    private void gameLogic() {
        // Move cursor with the mouse
        canvas.animate(() -> {
            if (inventory.getCollectableWithID("laptop") == null) {
                if (inventory.getCollectableWithID("rat01") != null) {
                    if (!timerStarted) {
                        startTime = System.currentTimeMillis();
                        timerStarted = true;
                    }
                    
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    long elapsedSeconds = elapsedTime / 1000;
                    long secondsDisplay = elapsedSeconds % 60;
                    // long elapsedMinutes = elapsedSeconds / 60;
                    if (30 - secondsDisplay == 0) {
                        activeRoom.jumpscare();
                        timer.removeAll();
                    }
                    timerText.setText("You should probably find your supplies within " + (30 - secondsDisplay) + " seconds.");
                    canvas.draw();
                }
            } else {
                timerText.setText("You're safe");
            }
        });       
        canvas.onMouseMove(event -> {
            activeCursor.setCenter(event.getPosition());
        });
        canvas.draw();
        canvas.onClick(event -> {
            clickLogic(event);
        });
    }

    private GraphicsObject check(MouseButtonEvent event, GraphicsGroup group) {
        GraphicsObject item = group.getElementAt(event.getPosition());
        return item;
    }

    private void clickLogic(MouseButtonEvent event){
        clickInventoryCollectableInteractions(event);
        clickCollectableItemInteractions(event);
        spawnLaptop();
        canvas.draw();
        activeRoom.updateRoom();
        if (activeRoom != activeRoom.getActiveRoom()) {
            canvas.removeAll();
            activeRoom = activeRoom.getActiveRoom();
            canvas.add(activeRoom);
            canvas.add(inventory);
            canvas.add(cursor);
            canvas.add(displayOverlay);
            canvas.add(timer);
        }
        // System.out.println(activeRoom.getBackgroundImage() + " active room");  // TESTING
        System.out.println(event.getPosition());  // TESTING and used to find asset coordinates
    }

    private void clickInventoryCollectableInteractions(MouseButtonEvent event){
        if (check(event, activeRoom) instanceof Collectable && ((Collectable) check(event, cursor)).getIDString() == hand.getIDString()) {
            // If the element under the click is a Collectable
            Collectable collectable = (Collectable) check(event, activeRoom);
            collectable.changePathOnCollection();
            // System.out.println(collectable + " collectable in clickInventoryCollectable...");  // TESTING
            inventory.acquireCollectable(collectable, activeCursor);
            // System.out.println(activeRoom.getRoomInhabitants() + " activeRoom inhabitants");  // TESTING
            activeRoom.getRoomInhabitants().remove(collectable);
            // It is added to inventory if it isn't already collected
        }
        else if (check(event, inventory) instanceof Collectable && ((Collectable) check(event, cursor)).getIDString() == hand.getIDString()) {
            // If the element under the click is a Collectable and in Inventory
            Collectable collectable = (Collectable) check(event, inventory);
            inventory.assignCollectable(collectable, cursor, activeCursor, event);
            // It becomes the cursor
        }
    }

    private void clickCollectableItemInteractions(MouseButtonEvent event){
        if (check(event, cursor) instanceof Collectable) {
            // If the Collectable is the cursor
            Collectable collectable = (Collectable) check(event, cursor);
            if (check(event, activeRoom) instanceof Item) {
                // ...and there is an Item underneath it...
                Item item = (Item) check(event, activeRoom);
                // System.out.println(item + " item in clickCollectableItemInterface");  // TESTING
                // Try to interact with the Item
                if (collectable != hand) {
                    // Reset the cursor if there isn't an item under it and it isn't the hand
                    item.interaction(collectable);
                    collectable.resetCursor(inventory, activeCursor);
                    activeCursor.setCenter(event.getPosition()); // TODO: Look at improving resetCursor() (maybe move from Collectable?)
                    // Keep everything aligned
                } else if (collectable == hand) {
                    item.interaction(collectable);
                }
            } else if (collectable != hand) {
                // Reset the cursor if there isn't an item under it and it isn't the hand
                collectable.resetCursorIfOverRoom(event, activeCursor, inventory);
                activeCursor.setCenter(event.getPosition()); // TODO: Look at improving resetCursor() (maybe move from Collectable?)
                // Keep everything aligned
            }
        }
    }

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

    private void spawnLaptop(){
        if (inventory.getCollectableWithID("rat01") != null && !laptopSpawned) {
            if (randomDouble < 0.2) {
                greenChairsRoom.spawnOpenLaptop();
                laptopSpawned = true;
            }
            else if (randomDouble < 0.4) {
                // windowedClassRoom.spawnOpenLaptop();
                greenChairsRoom.spawnOpenLaptop();
                laptopSpawned = true;
            }
            else if (randomDouble < 0.6) {
                // windowedClassRoom.spawnClosedLaptop();
                greenChairsRoom.spawnOpenLaptop();
                laptopSpawned = true;
            }
            else if (randomDouble < 0.8) {
                // lectureHallRoom.spawnOpenLaptop();
                greenChairsRoom.spawnOpenLaptop();
                laptopSpawned = true;
            }
            else {
                // lectureHallRoom.spawnClosedLaptop();
                greenChairsRoom.spawnOpenLaptop();
                laptopSpawned = true;
            }
        }
    }

    
}
