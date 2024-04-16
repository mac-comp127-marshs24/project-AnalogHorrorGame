package analoghorror.item;

import edu.macalester.graphics.*;

public class Cursor extends GraphicsGroup{
    GraphicsObject cursorDefault;
    GraphicsObject cursor;

    public Cursor(GraphicsObject gameCursor){
        cursorDefault = gameCursor;
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
