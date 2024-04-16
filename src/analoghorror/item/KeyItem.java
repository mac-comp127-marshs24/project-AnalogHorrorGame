package analoghorror.item;

import edu.macalester.graphics.events.*;
import analoghorror.Cursor;
import edu.macalester.graphics.*;

public class KeyItem extends CollectableItem {

    public KeyItem(double x, double y, Rectangle inventory, String path) {
        super(x, y, inventory, path);
        setImagePath(path);
        inInventory = false;
        inventorySlot = new Point(108, inventory.getCenter().getY());
    }

    @Override
    public void interaction(MouseButtonEvent event, GraphicsGroup checkedGroup, boolean itemInteractionBool, Item interactable, GraphicsGroup cursor, 
        Cursor cursorObject, GraphicsGroup ui, Rectangle inventoryBar){  // listen, I'm sorry; this is stupid but I gotta make do with the garbage temp boxBool shit I made earlier
            interactable.interaction(event, checkedGroup, itemInteractionBool, interactable, cursor, cursorObject, ui, inventoryBar);
        }
   
    
}
