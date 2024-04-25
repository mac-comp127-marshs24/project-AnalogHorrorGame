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

    Inventory inventory;
    Image background;
    Room activeRoom;

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

        activeRoom = new HallwayRoom(new GreenChairsRoom("assets" + File.separator + "hall.png"), hand,"assets" + File.separator + "hall.png");

        inventory = new Inventory(0, 0, 742, 68, "assets" + File.separator + "testBar.png");
        inventory.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT - inventory.getHeight() / 2);


        canvas.add(activeRoom);
        canvas.add(inventory);
        canvas.add(cursor);
        canvas.draw();
        itemLogic();
    }

    public void itemLogic() {
        // Move cursor with the mouse
        canvas.onMouseMove(event -> {
            activeCursor.setCenter(event.getPosition());
        });
        canvas.draw();
        // Click logic
        canvas.onClick(event -> {
            if (check(event, activeRoom.getRoomInhabitants()) instanceof Collectable && ((Collectable) check(event, cursor)).getIDString() == hand.getIDString()) {
                // If the element under the click is a Collectable
                Collectable collectable = (Collectable) check(event, activeRoom);
                inventory.acquireCollectable(collectable, activeCursor);
                activeRoom.getRoomInhabitants().remove(collectable);
                // It is added to inventory if it isn't already collected
            }
            else if (check(event, inventory) instanceof Collectable && ((Collectable) check(event, cursor)).getIDString() == hand.getIDString()) {
                // If the element under the click is a Collectable and in Inventory
                Collectable collectable = (Collectable) check(event, inventory);
                inventory.assignCollectable(collectable, cursor, activeCursor, event);  // NEW
                // It becomes the cursor
            }
            if (check(event, cursor) instanceof Collectable) {
                // If the Collectable is the cursor
                Collectable collectable = (Collectable) check(event, cursor);
                if (check(event, activeRoom.getRoomInhabitants()) instanceof Item) {
                    // ...and there is an Item underneath it...
                    Item item = (Item) check(event, activeRoom);
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
            // System.out.println(event.getPosition());  // Testing and used to find asset coordinates
        });
    }

    public GraphicsObject check(MouseButtonEvent event, GraphicsGroup group) {
        GraphicsObject item = group.getElementAt(event.getPosition());
        return item;
    }
}
