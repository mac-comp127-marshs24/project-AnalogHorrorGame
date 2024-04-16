package analoghorror;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;
import analoghorror.item.*;

public class HorrorGame {
    private static final int CANVAS_WIDTH = 854;
    private static final int CANVAS_HEIGHT = 480;

    private CanvasWindow canvas;
    Collectable hand;
    Cursor activeCursor;
    GraphicsObject cursorDefault;  // TODO: Make the default cursor our hands? 
    GraphicsGroup cursor;

    /**
     * TODO: Handle differently using Inventory/UI class
     */
    private int inventoryHeight = getCanvasHeight() / 7;
    private int inventoryWidth = getCanvasWidth() / 8 * 7;
    Rectangle inventoryBar;
    Image uiTexture;
    GraphicsGroup ui;
    // *****

    /**
     * TODO: The following will exist in a future Room class
     */
    GraphicsGroup game;
    Item box;
    Collectable key;
    Item door;
    Collectable doorBell;
    // *****


    public static void main(String[] args) {
        new HorrorGame();
    }

    public HorrorGame() {
        canvas = new CanvasWindow("Game Test", CANVAS_WIDTH, CANVAS_HEIGHT);

        game = new GraphicsGroup();  // TODO: Probably Room in the future
        cursor = new GraphicsGroup();
        ui = new GraphicsGroup();

        /**
         * TODO: Handle differently using Inventory/UI class 
         */
        inventoryBar = new Rectangle(0, 0, inventoryWidth, inventoryHeight);
        inventoryBar.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 8 * 7);
        inventoryBar.setStroked(false);
        inventoryBar.setFilled(false);;
        uiTexture = new Image("assets/testBar.png");
        uiTexture.setPosition(inventoryBar.getPosition());
        // Maybe including this? Unsure ↓
        ui.add(uiTexture);
        ui.add(inventoryBar);
        // *****

        hand = new Collectable(0, 0, "assets/hand.png", "hand");
        activeCursor = new Cursor(hand);
        activeCursor.resetCursor();
        cursor.add(activeCursor);

        /**
         * Item/Collectable constructor example.
         */
        box = new Item(300, 100, "assets/chestClosed.png", "assets/chestOpen.png", false);
        game.add(box);  // Add to "Room" (GraphicsGroup for now)

        key = new Collectable(200, 180, "assets/key.png", "key01");
        key.setInventorySlot(inventoryBar, 108);
        game.add(key);
        box.addValidInitCollectable(key);  // Add the Collectable to the internal validCollectable Set for the Item

        door = new Item(400, 200, "assets/doorClosed.png", "assets/doorOpen.png", false);
        game.add(door);

        doorBell = new Collectable(600, 40, "assets/doorbell.png", "doorbell01");
        doorBell.setInventorySlot(inventoryBar, 200);
        game.add(doorBell);
        door.addValidInitCollectable(doorBell);
        
        door.addValidInitCollectable(key);

        box.addValidSubCollectable(hand);
        door.addValidSubCollectable(hand);
        // *****

        canvas.add(ui);
        canvas.add(game);
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
            if (check(event, game) instanceof Collectable) {
                // If the element under the click is a Collectable
                Collectable collectable = (Collectable) check(event, game);
                collectable.inventoryLogic(event, game, cursor, activeCursor);
                // It is added to inventory if it isn't already collected, otherwise it becomes the cursor
            }
            if (check(event, cursor) instanceof Collectable) {
                // If the Collectable is the cursor
                Collectable collectable = (Collectable) check(event, cursor);
                if (check(event, game) instanceof Item) {
                    // ...and there is an Item underneath it...
                    Item item = (Item) check(event, game);
                    item.interaction(collectable);
                    // Try to interact with the Item
                    if (collectable != hand) {
                        // Reset the cursor if there isn't an item under it and it isn't the hand
                        collectable.resetCursor(game, cursor, activeCursor);
                    } else if (collectable == hand) {
                        item.interaction(collectable);
                    }
                } else if (collectable != hand) {
                    // Reset the cursor if there isn't an item under it and it isn't the hand
                    collectable.resetCursorIfOverRoom(event, game, cursor, activeCursor, ui, inventoryBar);
                    // TODO: Improve w/ Inventory class
                }
            }
        });
    }

    public GraphicsObject check(MouseButtonEvent event, GraphicsGroup group) {
        GraphicsObject item = group.getElementAt(event.getPosition());
        return item;
    }
}
