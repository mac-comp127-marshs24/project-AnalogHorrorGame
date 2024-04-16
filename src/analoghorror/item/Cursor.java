package analoghorror.item;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;

public class Cursor extends GraphicsGroup{
    GraphicsObject cursorDefault;
    GraphicsObject cursor;

    public Cursor(GraphicsObject gameCursor){
        // cursorDefault = gameCursor;
        cursorDefault = new Ellipse(0, 0, 40, 40);
    }

    public void setCursor(GraphicsObject newCursor){
        removeAll();
        cursor = newCursor;
        add(cursor);
    }

    public GraphicsObject getCursor(){
        return cursor;
    }

    public void resetCursor(){
        setCursor(cursorDefault);
    }
}
