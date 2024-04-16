package analoghorror;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;
// import analoghorror.*;
import analoghorror.item.Cursor;
import analoghorror.item.KeyItem;

public class HorrorGame {
    private static final int CANVAS_WIDTH = 854;
    private static final int CANVAS_HEIGHT = 480;

    private int inventoryHeight = getCanvasHeight() / 7; // using methods instead while thinking about refactoring l8r —W
    private int inventoryWidth = getCanvasWidth() / 8 * 7;
    private CanvasWindow canvas;

    /**
     * you are now entering the land of bullshit ugly code made to test the skeleton interaction —W
     */
    Rectangle inventoryBar;  // I imagine this wouldn't live here —W
    Rectangle box;  // pretend this is an Interactable object or whatever —W
    KeyItem key;  // pretend this is an Item object —W

    /**
     * ideally these should be in an Item class w/ getters and setters —W
     */
    boolean boxBool = false;  // true if box is "open"
    
    Cursor activeCursor;
    // GraphicsObject cursorObject;
    GraphicsObject cursorDefault;
    GraphicsGroup game;
    GraphicsGroup cursor;
    GraphicsGroup ui;

    /**
     * working on image asset tests
     */
    Image uiTexture;

    public static void main(String[] args) {
        new HorrorGame();
    }

    public HorrorGame() {
        canvas = new CanvasWindow("game", CANVAS_WIDTH, CANVAS_HEIGHT);

        game = new GraphicsGroup();
        cursor = new GraphicsGroup();
        ui = new GraphicsGroup();

        inventoryBar = new Rectangle(0, 0, inventoryWidth, inventoryHeight);
        inventoryBar.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 8 * 7);

        uiTexture = new Image("assets/testBar.png");
        uiTexture.setPosition(inventoryBar.getPosition());

        ui.add(uiTexture);
        ui.add(inventoryBar);

        activeCursor = new Cursor(new Ellipse(0, 0, 10, 10));
        activeCursor.resetCursor();
        cursor.add(activeCursor);

        box = new Rectangle(300, 100, 100, 100);
        box.setStrokeWidth(1);
        game.add(box);

        key = new KeyItem(200, 180, 40, 40, inventoryBar);
        game.add(key);

        canvas.add(ui);
        canvas.add(game);
        canvas.add(cursor);
        canvas.draw();
        itemLogic();

        System.out.println(inventoryBar.getWidth());
        System.out.println(inventoryBar.getHeight());

    }

    public static int getCanvasWidth() {
        return CANVAS_WIDTH;
    }

    public static int getCanvasHeight() {
        return CANVAS_HEIGHT;
    }

    public void itemLogic() {  // just to get a general idea on what an interaction could look like —W
        canvas.onMouseMove(event -> {
            // if the key isn't set as the cursor object, uses cursorDefault to prevent exception errors
            // (invisible, but could be hand l8r) —W
            activeCursor.setCenter(event.getPosition());
        });
        canvas.draw();
        canvas.onClick(event -> {  // TODO: make so calls if(check() != null){check().interaction()}
            // if the object under the click is key and key isn't in the inventory —W
            if (check(event, game) == key) {
                key.inventoryLogic(event, game, cursor, activeCursor);
            }
            // if the cursor is key and clicked over box —W
            if (check(event, cursor) == key) {
                if (check(event, game) == box) {
                    boxBool = key.interaction(event, game, boxBool, box, cursor, activeCursor, ui, inventoryBar);  // this bool implementation brings shame to my ancestors
                    key.resetCursorAfterInteraction(event, game, cursor, activeCursor, ui, inventoryBar);
                } else {
                    key.resetCursor(event, game, cursor, activeCursor, ui, inventoryBar);
                }
            }
        });
    }

    public GraphicsObject check(MouseButtonEvent event, GraphicsGroup group) {
        GraphicsObject item = group.getElementAt(event.getPosition());
        return item;
    }
}
