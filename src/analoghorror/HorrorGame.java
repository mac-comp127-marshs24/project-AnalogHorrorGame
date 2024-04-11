package analoghorror;
import edu.macalester.graphics.*;

public class HorrorGame {
    private static final int CANVAS_WIDTH = 854;
    private static final int CANVAS_HEIGHT = 480;
    
    private static int inventoryHeight = getCanvasHeight() / 7; 
    private static int inventoryWidth = getCanvasWidth() / 8 * 7;
    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("game", CANVAS_WIDTH, CANVAS_HEIGHT);
        
        Rectangle rectangle = new Rectangle(10, 10, inventoryWidth, inventoryHeight);
        canvas.add(rectangle);
        rectangle.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 6 * 5);
        canvas.draw();
    }

    public static int getCanvasWidth(){
        return CANVAS_WIDTH;
    }

    public static int getCanvasHeight(){
        return CANVAS_HEIGHT;
    }
}
