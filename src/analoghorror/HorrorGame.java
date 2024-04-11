package analoghorror;
import edu.macalester.graphics.*;

public class HorrorGame {
    private static final int CANVAS_WIDTH  = 854;
    private static final int CANVAS_HEIGHT  = 480;
    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("game", CANVAS_WIDTH, CANVAS_HEIGHT);
        
        Rectangle rectangle = new Rectangle(20, 20, 10, 10);
        canvas.add(rectangle);
        canvas.draw();
    }
}
