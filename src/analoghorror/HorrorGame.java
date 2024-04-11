package analoghorror;
import edu.macalester.graphics.*;
import edu.macalester.graphics.events.MouseButtonEvent;

public class HorrorGame {
    private static final int CANVAS_WIDTH = 854;
    private static final int CANVAS_HEIGHT = 480;
    
    private static int inventoryHeight = getCanvasHeight() / 7; // Using methods instead while thinking about refactoring l8r
    private static int inventoryWidth = getCanvasWidth() / 8 * 7;
    private static CanvasWindow canvas;
    private static Rectangle inventoryBar;
    public static void main(String[] args) {
        new HorrorGame();
    }

    private HorrorGame() {
        canvas = new CanvasWindow("game", CANVAS_WIDTH, CANVAS_HEIGHT);

        // Generally just thinking we work on click/item interactions with some basic shapes for now; move the logic to the proper classes after?
        inventoryBar = new Rectangle(0, 0, inventoryWidth, inventoryHeight);
        canvas.add(inventoryBar);
        inventoryBar.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 6 * 5);

        Rectangle box = new Rectangle(300, 100, 100, 100);
        canvas.add(box);

        Ellipse key = new Ellipse(200, 180, 40,40);
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
            check(event).setCenter((inventoryBar.getCenter()));
            canvas.draw();
        });
    }

    public GraphicsObject check(MouseButtonEvent event){
        return canvas.getElementAt(event.getPosition());
    }
}
