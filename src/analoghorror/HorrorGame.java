package analoghorror;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;

import java.io.File;
import java.util.Arrays;

import analoghorror.item.*;

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
    // *****

    /**
     * TODO: The following will exist in a future Room class
     */
    GraphicsGroup game;
    Item box;
    Collectable key;
    Item door;
    Collectable card;
    Item sonic;
    // *****


    public static void main(String[] args) {
        new HorrorGame();
    }

    public HorrorGame() {
        canvas = new CanvasWindow("Game Test", CANVAS_WIDTH, CANVAS_HEIGHT);

        game = new GraphicsGroup();  // TODO: Probably Room in the future
        cursor = new GraphicsGroup();

        inventory = new Inventory(0, 0, 742, 68, "assets" + File.separator + "testBar.png");
        inventory.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT - inventory.getHeight() / 2);

        background = new Image("assets" + File.separator + "hall.png");
        canvas.add(background);
        // *****

        hand = new Collectable(0, 0, "assets" + File.separator + "hand.png", "hand");
        activeCursor = new Cursor(hand);
        activeCursor.resetCursor();
        cursor.add(activeCursor);

        /**
         * Item/Collectable constructor example.
         */
        box = new Item(255, 286, "assets" + File.separator + "chestClosed.png", false, 2);
        box.setStatePaths(Arrays.asList("assets" + File.separator + "chestClosed.png", "assets" + File.separator + "chestOpen.png"));
        game.add(box);  // Add to "Room" (GraphicsGroup for now)

        key = new Collectable(60, 205, "assets" + File.separator + "silverKey.png", "key01");
        game.add(key);
        box.addValidInitCollectable(key);  // Add the Collectable to the internal validCollectable Sets for the Item

        door = new Item(385, 120, "assets" + File.separator + "doorClosed.png", false, 2);
        door.setStatePaths(Arrays.asList("assets" + File.separator + "doorClosed.png", "assets" + File.separator + "doorOpen.png"));
        game.add(door);

        sonic = new Item(778, 70, "assets" + File.separator + "sonicForward.png", true, 4);
        sonic.setStatePaths(Arrays.asList("assets" + File.separator + "sonicForward.png", "assets" + File.separator + "sonicDown.png",
        "assets" + File.separator + "sonicBack.png", "assets" + File.separator + "sonicUp.png"));
        game.add(sonic);
        sonic.addValidInitCollectable(hand);
        sonic.addValidSubCollectable(hand);

        card = new Collectable(528, 325, "assets" + File.separator + "studentCard.png", "card01");
        game.add(card);
        door.addValidInitCollectable(card);
        
        door.addValidInitCollectable(key);

        box.addValidSubCollectable(hand);
        door.addValidSubCollectable(hand);
        // *****

        canvas.add(game);
        canvas.add(inventory);
        canvas.add(cursor);
        canvas.draw();
        itemLogic();
    }

    public static int getCanvasWidth() {
        return CANVAS_WIDTH;
    }

    public static int getCanvasHeight() {
        return CANVAS_HEIGHT;
    }

    public void itemLogic() {
        // Move cursor with the mouse
        canvas.onMouseMove(event -> {
            activeCursor.setCenter(event.getPosition());
        });
        canvas.draw();
        // Click logic
        canvas.onClick(event -> {
            if (check(event, game) instanceof Collectable && ((Collectable) check(event, cursor)).getIDString() == hand.getIDString()) {
                // If the element under the click is a Collectable
                Collectable collectable = (Collectable) check(event, game);
                inventory.acquireCollectable(collectable, activeCursor);  // NEW
                game.remove(collectable);
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
                if (check(event, game) instanceof Item) {
                    // ...and there is an Item underneath it...
                    Item item = (Item) check(event, game);
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
