package analoghorror.item;

import edu.macalester.graphics.events.*;
import edu.macalester.graphics.*;

public class CollectableItem extends Item {
    boolean inInventory;  // true if in inventory —W
    Point inventorySlot;  // inventory space —W; we should have an inventory class to help with slot management

    public CollectableItem(double x, double y, Rectangle inventory, String path) {
        super(x, y, path);
        inInventory = false;
        inventorySlot = new Point(108, inventory.getCenter().getY());
    }
    
    public void inventoryLogic(MouseButtonEvent event, GraphicsGroup checkedGroup, GraphicsGroup cursor, Cursor cursorObject){
        if (inInventory) {
            // cursor is now key and key is now part of the cursor group —W
            cursorObject.setCursor(this);
            checkedGroup.remove(cursorObject.getCursor());
            cursor.add(cursorObject);
            inInventory = false;  // key is out of inventory —W
        }
        else if (!inInventory) {
            // put key in inventory
            this.setCenter(inventorySlot); // spot in inventory (use a Set or smthn) in Item —W
            cursorObject.resetCursor();
            inInventory = true;  // key is now in inventory —W
        }
    }    

    public void resetCursorAfterInteraction(MouseButtonEvent event, GraphicsGroup checkedGroup, GraphicsGroup cursor, 
    Cursor cursorObject, GraphicsGroup ui, Rectangle inventoryBar){
        // cursor.remove(cursorObject);  // don't think I need this, but keeping it for future troubleshooting just in case
        checkedGroup.add(cursorObject.getCursor());
        this.setCenter(inventorySlot);
        cursorObject.resetCursor();
        inInventory = true;
    }

    public void resetCursor(MouseButtonEvent event, GraphicsGroup checkedGroup, GraphicsGroup cursor, 
    Cursor cursorObject, GraphicsGroup ui, Rectangle inventoryBar){
        if (ui.getElementAt(event.getPosition()) != inventoryBar && inInventory == false) {
            // cursor.remove(cursorObject);  // don't think I need this, but keeping it for future troubleshooting just in case
            checkedGroup.add(this);
            this.setCenter(inventorySlot);
            inInventory = true;  // I wanted the key to reset upon a click even if you aren't using it over a box —W
            cursorObject.resetCursor();
        }
    }
}
