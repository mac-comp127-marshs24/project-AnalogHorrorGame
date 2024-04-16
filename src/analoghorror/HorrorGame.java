package analoghorror;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;
import analoghorror.item.*;

public class HorrorGame {
    private static final int CANVAS_WIDTH = 854;
    private static final int CANVAS_HEIGHT = 480;

    private CanvasWindow canvas;
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

        activeCursor = new Cursor(new Ellipse(0, 0, 10, 10));
        activeCursor.resetCursor();
        cursor.add(activeCursor);

        /**
         * Item/Collectable example.
         */
        box = new Item(300, 100, "assets/chestClosed.png", "assets/chestOpen.png", false);
        game.add(box);  // Add to "Room" (GraphicsGroup for now)

        key = new Collectable(200, 180, inventoryBar, "assets/key.png", "key01");
        game.add(key);
        box.addValidCollectable(key);  // Add the Collectable to the internal validCollectable Set for the Item
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
        // TODO: Add annotation
        canvas.onClick(event -> {
            if (check(event, game) instanceof Collectable) {
                Collectable collectable = (Collectable) check(event, game);
                collectable.inventoryLogic(event, game, cursor, activeCursor);
            }
            if (check(event, cursor) instanceof Collectable) {
                Collectable collectable = (Collectable) check(event, cursor);
                if (check(event, game) instanceof Item) {
                    Item item = (Item) check(event, game);
                    item.interaction(collectable);
                    collectable.resetCursor(game, cursor, activeCursor);
                } else {
                    collectable.resetCursorIfOverRoom(event, game, cursor, activeCursor, ui, inventoryBar);
                }
            }
        });
    }

    public GraphicsObject check(MouseButtonEvent event, GraphicsGroup group) {
        GraphicsObject item = group.getElementAt(event.getPosition());
        return item;
    }
}
