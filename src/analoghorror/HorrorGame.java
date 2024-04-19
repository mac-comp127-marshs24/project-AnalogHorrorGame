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

    /**
     * TODO: Handle differently using Inventory/UI class
     */
    Inventory inventory;
    Image uiTexture;
    Image background;
    GraphicsGroup ui;
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
        ui = new GraphicsGroup();

        /**
         * TODO: Handle differently using Inventory/UI class 
         */
        inventory = new Inventory(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        inventory.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT - inventory.getHeight() / 2);
        background = new Image("assets" + File.separator + "hall.png");
        canvas.add(background);
        uiTexture = new Image("assets" + File.separator + "testBar.png");
        uiTexture.setPosition(inventory.getPosition());
        // Maybe including this? Unsure ↓
        ui.add(uiTexture);
        ui.add(inventory);
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
        key.setInventorySlot(inventory, 111);
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
        card.setInventorySlot(inventory, 174);
        game.add(card);
        door.addValidInitCollectable(card);
        
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
            if (check(event, game) instanceof Collectable && ((Collectable) check(event, cursor)).getIDString() == hand.getIDString()) {
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
                    // Try to interact with the Item
                    if (collectable != hand) {
                        // Reset the cursor if there isn't an item under it and it isn't the hand
                        item.interaction(collectable);
                        collectable.resetCursor(game, activeCursor);
                    } else if (collectable == hand) {
                        item.interaction(collectable);
                    }
                } else if (collectable != hand) {
                    // Reset the cursor if there isn't an item under it and it isn't the hand
                    collectable.resetCursorIfOverRoom(event, game, activeCursor, inventory);
                }
            }
            // System.out.println(event.getPosition());
        });
    }

    public GraphicsObject check(MouseButtonEvent event, GraphicsGroup group) {
        GraphicsObject item = group.getElementAt(event.getPosition());
        return item;
    }
}
