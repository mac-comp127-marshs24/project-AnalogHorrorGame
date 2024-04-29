package analoghorror;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;

import java.io.File;

import analoghorror.inhabitants.*;
import analoghorror.rooms.*;

public class HorrorGame {
    private static final int CANVAS_WIDTH = 854;
    private static final int CANVAS_HEIGHT = 480;

    private CanvasWindow canvas;
    Collectable hand;  // TODO: Random hand asset
    Cursor activeCursor;
    GraphicsObject cursorDefault;
    GraphicsGroup cursor;

    GraphicsGroup displayText;
    
    Image background;
    Inventory inventory;

    Room activeRoom;
    HallwayRoom hallway;
    GreenChairsRoom greenChairsRoom;
    LectureHallRoom lectureHallRoom;
    WindowedClassRoom windowedClassRoom;

    public static void main(String[] args) {
        new HorrorGame();
    }

    public HorrorGame() {
        canvas = new CanvasWindow("Game Test", CANVAS_WIDTH, CANVAS_HEIGHT);
        cursor = new GraphicsGroup();

        hand = new Collectable(0, 0, "assets" + File.separator + "hand.png", "hand");
        activeCursor = new Cursor(hand);
        activeCursor.resetCursor();
        cursor.add(activeCursor);

        displayText = new GraphicsGroup();

        //given that we start in hallway, hallway should always have a val and shouldnt be null when greenchairs is called?
        hallway = new HallwayRoom(hand,"assets" + File.separator + "hall.png");
        greenChairsRoom = new GreenChairsRoom(hallway, hand, "assets" + File.separator + "roombase.png");
        lectureHallRoom = new LectureHallRoom(hallway, hand, "assets" + File.separator + "templecturehall.jpg");
        windowedClassRoom = new WindowedClassRoom(hallway, hand,  "assets" + File.separator + "tempwindowroom.jpg");
        hallway.addChairClassroom(greenChairsRoom);
        hallway.addLectureHall(lectureHallRoom);
        hallway.addWindowedClassroom(windowedClassRoom);

        activeRoom = hallway.getActiveRoom();

        //activeRoom = new HallwayRoom(new GreenChairsRoom("assets" + File.separator + "hall.png"), hand,"assets" + File.separator + "hall.png");

        inventory = new Inventory(0, 0, 742, 68, "assets" + File.separator + "testBar.png");
        inventory.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT - inventory.getHeight() / 2);

        canvas.add(activeRoom);
        canvas.add(inventory);
        canvas.add(cursor);
        canvas.add(displayText);
        canvas.draw();
        gameLogic();
    }

    private void gameLogic() {
        // Move cursor with the mouse
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
        canvas.draw();
        activeRoom.updateRoom(displayText);
        if (activeRoom != activeRoom.getActiveRoom()) {
            canvas.removeAll();
            activeRoom = activeRoom.getActiveRoom();
            canvas.add(activeRoom);
            canvas.add(inventory);
            canvas.add(cursor);
        }
        // System.out.println(activeRoom.getBackgroundImage() + " active room");  // TESTING
        System.out.println(event.getPosition());  // TESTING and used to find asset coordinates
    }

    private void clickInventoryCollectableInteractions(MouseButtonEvent event){
        if (check(event, activeRoom) instanceof Collectable && ((Collectable) check(event, cursor)).getIDString() == hand.getIDString()) {
            // If the element under the click is a Collectable
            Collectable collectable = (Collectable) check(event, activeRoom);
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
}
