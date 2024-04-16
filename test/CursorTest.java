import analoghorror.Cursor;
import edu.macalester.graphics.*;

public class CursorTest {
    Cursor activeCursor;
    GraphicsObject cursorDefault;
    GraphicsGroup cursor;

    public CursorTest(){
        cursor = new GraphicsGroup();

        activeCursor = new Cursor(new Ellipse(0, 0, 10, 10));
        activeCursor.resetCursor();
        cursor.add(activeCursor);

        
    }


}
