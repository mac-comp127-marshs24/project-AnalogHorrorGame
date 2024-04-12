package analoghorror;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;

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
    Ellipse key;  // pretend this is an Item object —W

    /**
     * ideally these should be in an Item class w/ getters and setters —W
     */
    boolean boxBool = false;  // true if box is "open"
    boolean keyBool = false;  // true if in inventory —W
    Point keyHome;  // inventory space —W
    GraphicsObject cursorObject;
    GraphicsObject cursorDefault;
    GraphicsGroup game;
    GraphicsGroup cursor;
    GraphicsGroup ui;

    public static void main(String[] args) {
        new HorrorGame();
    }

    private HorrorGame() {
        canvas = new CanvasWindow("game", CANVAS_WIDTH, CANVAS_HEIGHT);

        game = new GraphicsGroup();
        cursor = new GraphicsGroup();
        ui = new GraphicsGroup();

        inventoryBar = new Rectangle(0, 0, inventoryWidth, inventoryHeight);
        ui.add(inventoryBar);
        inventoryBar.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 6 * 5);

        keyHome = new Point(200, inventoryBar.getCenter().getY());
        cursorDefault = new Ellipse(0, 0, 10, 10);
        cursorObject = cursorDefault;


        box = new Rectangle(300, 100, 100, 100);
        box.setStrokeWidth(1);
        game.add(box);

        key = new Ellipse(200, 180, 40, 40);
        game.add(key);

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

    public void itemLogic() {  // just to get a general idea on what an interaction could look like —W
        canvas.animate(() -> {
            canvas.onMouseMove(event -> {
                // if the key isn't set as the cursor object, uses cursorDefault to prevent exception errors
                // (invisible, but could be hand l8r) —W
                cursorObject.setCenter(event.getPosition());
            });
            canvas.draw();
        });
        canvas.onClick(event -> {
            // if the object under the click is key and key isn't in the inventory —W
            if (check(event, game) == key && keyBool == false) {
                // put key in inventory
                game.getElementAt(event.getPosition()).setCenter(keyHome); // spot in inventory (use a Set or smthn) in Item —W
                cursorObject = cursorDefault;
                keyBool = true;  // key is now in inventory —W
            }
            // if key is under click and key is in inventory —W
            if (check(event, game) == key && keyBool == true) {
                // cursor is now key and key is now part of the cursor group —W
                cursorObject = check(event, game);
                game.remove(cursorObject);
                cursor.add(cursorObject);
                keyBool = false;  // key is out of inventory —W
            }
            // if the cursor is key and clicked over box —W
            if (check(event, cursor) == key) {
                if (check(event, game) == box) {
                    // change box and reset key in inventory —W
                    if (!boxBool) {
                        box.setStrokeWidth(10);
                    }
                    if (boxBool){
                        box.setStrokeWidth(1);
                    }
                    cursor.remove(cursorObject);
                    game.add(cursorObject);
                    key.setCenter(keyHome);
                    cursorObject = cursorDefault;
                    boxBool = true;
                    keyBool = true;
                }
                // I wanted the key to reset upon a click even if you aren't using it over a box —W
                if (check(event, ui) != inventoryBar && keyBool == false) {
                    cursor.remove(cursorObject);
                    game.add(cursorObject);
                    key.setCenter(keyHome);
                    cursorObject = cursorDefault;
                    keyBool = true;
                }
            }
        });

    }

    public GraphicsObject check(MouseButtonEvent event, GraphicsGroup group) {
        GraphicsObject item = group.getElementAt(event.getPosition());
        return item;
    }
}
