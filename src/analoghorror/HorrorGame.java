package analoghorror;
import edu.macalester.graphics.*;

public class HorrorGame {
    private static final int CANVAS_WIDTH = 854;
    private static final int CANVAS_HEIGHT = 480;
    
    private static int inventoryHeight = getCanvasHeight() / 7; 
    private static int inventoryWidth = getCanvasWidth() / 8 * 7;
    public static void main(String[] args) {
        skeletonTest();
    }

    public static void skeletonTest() {
        CanvasWindow canvas = new CanvasWindow("game", CANVAS_WIDTH, CANVAS_HEIGHT);

        Rectangle inventoryBar = new Rectangle(0, 0, inventoryWidth, inventoryHeight);
        canvas.add(inventoryBar);
        inventoryBar.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 6 * 5);

        Rectangle box = new Rectangle(300, 100, 100, 100);
        canvas.add(box);

        Ellipse key = new Ellipse(200, 180, 40,40);
        canvas.add(key);

        canvas.draw();

    }

    public static int getCanvasWidth(){
        return CANVAS_WIDTH;
    }

    public static int getCanvasHeight(){
        return CANVAS_HEIGHT;
    }
}
