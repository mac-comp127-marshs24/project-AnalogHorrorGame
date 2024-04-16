package analoghorror.item;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;

public class Cursor extends GraphicsGroup{
    GraphicsObject cursorDefault;
    GraphicsObject cursor;

    public Cursor(GraphicsObject cursorDefault){
        this.cursorDefault = cursorDefault;
    }

    public void setCursor(GraphicsObject cursor){
        this.removeAll();
        this.add(cursor);
        this.cursor = cursor;
    }

    public GraphicsObject getCursor(){
        return cursor;
    }

    public void resetCursor(){
        this.removeAll();
        this.add(cursorDefault);
        cursor = cursorDefault;
    }
}
