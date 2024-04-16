package analoghorror.item;

import edu.macalester.graphics.events.*;
import edu.macalester.graphics.*;

public class KeyItem extends CollectableItem {

    public KeyItem(double x, double y, Rectangle inventory, String path) {
        super(x, y, inventory, path);
        setImagePath(path);
        inInventory = false;
        inventorySlot = new Point(108, inventory.getCenter().getY());
    }

    @Override
    public boolean interaction(MouseButtonEvent event, GraphicsGroup checkedGroup, boolean itemInteractionBool, Rectangle interactable, GraphicsGroup cursor, 
        Cursor cursorObject, GraphicsGroup ui, Rectangle inventoryBar){  // listen, I'm sorry; this is stupid but I gotta make do with the garbage temp boxBool shit I made earlier
            // change box and reset key in inventory —W
            if (!itemInteractionBool) {
                interactable.setStrokeWidth(10);
                return true;
            }
            else if (itemInteractionBool){
                interactable.setStrokeWidth(1);
                return false;
            }
            return itemInteractionBool;
        }
   
    
}
