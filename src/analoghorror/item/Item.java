package analoghorror.item;

import edu.macalester.graphics.events.*;
import analoghorror.Cursor;
import edu.macalester.graphics.*;

public abstract class Item extends Image{
    boolean defaultState;  // true if not modified

    public Item(double x, double y, String path) {
        super(x, y);
        setImagePath(path);
        defaultState = true;
    }

    public void interaction(MouseButtonEvent event, GraphicsGroup checkedGroup, boolean itemInteractionBool, Item interactable, GraphicsGroup cursor, 
        Cursor cursorObject, GraphicsGroup ui, Rectangle inventoryBar){  // listen, I'm sorry; this is stupid but I gotta make do with the garbage temp boxBool shit I made earlier
        }

    /**
     * Returns true if not modified
     * 
     * @return
     */
    public boolean getState(){
        return defaultState;
    }
}
