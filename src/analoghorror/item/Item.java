package analoghorror.item;

import edu.macalester.graphics.events.*;
import edu.macalester.graphics.*;

public abstract class Item extends Image{
    
    public Item(double x, double y, String path) {
        super(x, y);
        setImagePath(path);
    }

    public boolean interaction(MouseButtonEvent event, GraphicsGroup checkedGroup, boolean itemInteractionBool, Rectangle interactable, GraphicsGroup cursor, 
        Cursor cursorObject, GraphicsGroup ui, Rectangle inventoryBar){  // listen, I'm sorry; this is stupid but I gotta make do with the garbage temp boxBool shit I made earlier
            return itemInteractionBool;
        }
    }
