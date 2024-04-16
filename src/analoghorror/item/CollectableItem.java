package analoghorror.item;

import edu.macalester.graphics.events.*;
import analoghorror.Cursor;
import edu.macalester.graphics.*;

public class CollectableItem extends Item {
    boolean inInventory;  // True if CollectableItem is in inventory
    Point inventorySlot;  // inventory space; we should have an inventory class to help with slot management —W

    public CollectableItem(double x, double y, Rectangle inventory, String path) {
        super(x, y, path);
        inInventory = false;
        inventorySlot = new Point(108, inventory.getCenter().getY());
    }
    
    /**
     * If CollectableItem is in the inventory, it will be removed and set as the Cursor. If it is not,
     * it is collected and put in the inventory.
     * 
     * @param event
     * @param checkedGroup
     * @param cursor
     * @param cursorObject
     */
    public void inventoryLogic(MouseButtonEvent event, GraphicsGroup checkedGroup, GraphicsGroup cursor, Cursor cursorObject){
        if (inInventory) {
            // Cursor is now CollectableItem and CollectableItem is now part of the cursor group
            cursorObject.setCursor(this);
            checkedGroup.remove(cursorObject.getCursor());
            cursor.add(cursorObject);
            inInventory = false;  // CollectableItem is out of inventory
        }
        else if (!inInventory) {
            // Put CollectableItem in inventory
            this.setCenter(inventorySlot); // Reference variable
            cursorObject.resetCursor();
            inInventory = true;  // CollectableItem is now in inventory
        }
    }    

    /**
     * CollectableItem is no longer used as Cursor and is sent back to its inventory slot.
     * 
     * @param checkedGroup
     * @param cursor
     * @param cursorObject
     */
    public void resetCursorAfterInteraction(GraphicsGroup checkedGroup, GraphicsGroup cursor, Cursor cursorObject){
        // cursor.remove(cursorObject);  // don't think I need this, but keeping it for future troubleshooting just in case
        checkedGroup.add(cursorObject.getCursor());
        this.setCenter(inventorySlot);
        cursorObject.resetCursor();
        inInventory = true;
    }

    /**
     * TODO: Improve interaction with a future Inventory/UI class
     * 
     * CollectableItem is no longer used as Cursor and is sent back to its inventory slot if the inventory UI isn't under it.
     * 
     * @param event
     * @param checkedGroup
     * @param cursor
     * @param cursorObject
     * @param ui
     * @param inventoryBar
     */
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
