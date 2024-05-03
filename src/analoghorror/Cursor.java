package analoghorror;

import edu.macalester.graphics.*;

public class Cursor extends GraphicsGroup{
    GraphicsObject cursorDefault;
    GraphicsObject cursor;

    /**
     * An object to be moved along with a mouse cursor's coordinates. 
     * 
     * @param gameCursor GraphicsObject you want to act as the cursor when an item isn't held
     */
    public Cursor(GraphicsObject gameCursor){
        cursorDefault = gameCursor;
    }

    /**
     * Replaces the current cursor with a new GraphicsObject.
     * 
     * @param newCursor
     */
    public void setCursor(GraphicsObject newCursor){
        removeAll();
        cursor = newCursor;
        add(cursor);
    }

    public GraphicsObject getCursor(){
        return cursor;
    }

    /**
     * Resets cursor according to default cursor set on creation.
     */
    public void resetCursor(){
        setCursor(cursorDefault);
    }
}
