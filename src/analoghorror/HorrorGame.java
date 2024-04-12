package analoghorror;
import edu.macalester.graphics.*;
import edu.macalester.graphics.events.MouseButtonEvent;

public class HorrorGame {
    private static final int CANVAS_WIDTH = 854;
    private static final int CANVAS_HEIGHT = 480;
    
    private int inventoryHeight = getCanvasHeight() / 7; // Using methods instead while thinking about refactoring l8r
    private int inventoryWidth = getCanvasWidth() / 8 * 7;
    private CanvasWindow canvas;
    private Rectangle inventoryBar;
    Rectangle box;
    Ellipse key;
    
    boolean boxBool = false; // ideally these should be in an Item class w/ getters and setters
    boolean keyBool = false;
    Point boxHome;
    Point keyHome;

    public static void main(String[] args) {
        new HorrorGame();
    }

    private HorrorGame() {
        canvas = new CanvasWindow("game", CANVAS_WIDTH, CANVAS_HEIGHT);

        // Generally just thinking we work on click/item interactions with some basic shapes for now; move the logic to the proper classes after?
        inventoryBar = new Rectangle(0, 0, inventoryWidth, inventoryHeight);
        canvas.add(inventoryBar);
        inventoryBar.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 6 * 5);
        boxHome = new Point(300, 100);
        keyHome = new Point(200, 180);

        box = new Rectangle(300, 100, 100, 100);
        canvas.add(box);

        key = new Ellipse(200, 180, 40,40);
        canvas.add(key);

        canvas.draw();
        itemLogic();

    }

    public static int getCanvasWidth(){
        return CANVAS_WIDTH;
    }

    public static int getCanvasHeight(){
        return CANVAS_HEIGHT;
    }

    public void itemLogic(){  // Draft
        canvas.onClick(event -> {
            if (check(event) == box && boxBool == false){
                canvas.getElementAt(event.getPosition()).setCenter((inventoryBar.getCenter())); // proper spot in inventory (use set or smthn)
                boxBool = true;
            }
            if (check(event) == key && keyBool == false){
                canvas.getElementAt(event.getPosition()).setCenter((inventoryBar.getCenter())); // proper spot in inventory (use set or smthn)
                keyBool = true;
            } 
            if (check(event) == box && boxBool == true) {
                check(event).setPosition(boxHome); // proper spot in room (track using Item info)
                boxBool = false;
            }
            if (check(event) == key && keyBool == true) {
                check(event).setPosition(keyHome);; // proper spot in room (track using Item info)
                keyBool = false;
            }
            canvas.draw();
        });
    }

    public GraphicsObject check(MouseButtonEvent event){
        GraphicsObject item = canvas.getElementAt(event.getPosition());
        // maybe also add a bool to each Item object so we can check if it's in the inventory
        // that way we can change behavior
        // if (item == box && boxBool == true) {
        //     System.out.println("boxBool is now " + boxBool);
        // } 
        // if (item == key && keyBool == true) {
        //     System.out.println("keyBool is now " + keyBool);
        // } else {
        //     if (item == box) {
        //         System.out.println("boxBool is now " + boxBool);
        //     } 
        //     if (item == key) {
        //         System.out.println("keyBool is now " + keyBool);
        //     }
        // }
        return item;
    }
}
