package analoghorror.item;

import analoghorror.Cursor;
import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;

public class BoxItem extends Item{  // maybe make a new RoomItem class that includes two paths for the constructor
    String defaultImagePath;
    String modifiedImagePath;

    public BoxItem(double x, double y, String defaultPath, String modifiedPath) {
        super(x, y, defaultPath);
        defaultImagePath = defaultPath;
        modifiedImagePath = modifiedPath;
    }
    
    @Override
    public void interaction(MouseButtonEvent event, GraphicsGroup checkedGroup, boolean itemInteractionBool, Item interactable, GraphicsGroup cursor, 
        Cursor cursorObject, GraphicsGroup ui, Rectangle inventoryBar){  // listen, I'm sorry; this is stupid but I gotta make do with the garbage temp boxBool shit I made earlier
        if (defaultState) {
            setImagePath(modifiedImagePath);
            defaultState = false;
        }
        else if (!defaultState) {
            setImagePath(defaultImagePath);
            defaultState = true;
        }
    }
}
