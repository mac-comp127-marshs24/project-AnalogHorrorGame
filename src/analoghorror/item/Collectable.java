package analoghorror.item;

import edu.macalester.graphics.events.*;
import analoghorror.Cursor;
import analoghorror.Inventory;
import edu.macalester.graphics.*;

public class Collectable extends Image {
    boolean inInventory;  // True if CollectableItem is in inventory
    Point inventorySlot;  // Inventory space; TODO: Have an inventory class to help with slot management
    String collectableID;

    /**
     * A game item that can be collected into the Inventory when clicked. Arguments determine location in Inventory, texture,
     * and a unique collectableID.
     * 
     * @param x
     * @param y
     * @param path Texture
     * @param collectableID Referenced when an Item checks to see if it can change its state with the Collectable object
     */
    public Collectable(double x, double y, String path, String collectableID) {
        super(x, y, path);
        inInventory = false;
        this.collectableID = collectableID;
    }

    public void setInventorySlot(Inventory inventory, double x){  // TODO: Eventually make private
        inventorySlot = new Point(x, inventory.getCenter().getY());
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
    public void inventoryLogic(MouseButtonEvent event, GraphicsGroup checkedGroup, GraphicsGroup cursor, Cursor cursorObject){  // TODO: Add Inventory as arg
        if (inInventory) {
            // Cursor is now CollectableItem and CollectableItem is now part of the cursor group
            cursorObject.setCursor(this);
            checkedGroup.remove(cursorObject.getCursor());
            cursor.add(cursorObject);
            inInventory = false;  // CollectableItem is out of inventory
        }
        else if (!inInventory) {
            // Put CollectableItem in inventory
            // TODO: Call an inventory.getAvailableSlot() and use it to setInventorySlot
            this.setCenter(inventorySlot);  
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
    public void resetCursor(GraphicsGroup checkedGroup, Cursor cursorObject){
        checkedGroup.add(this);
        this.setCenter(inventorySlot);
        cursorObject.resetCursor();
        inInventory = true;
    }

    /** 
     * CollectableItem is no longer used as Cursor and is sent back to its inventory slot if the inventory UI isn't under it.
     * 
     * @param event
     * @param checkedGroup
     * @param cursor
     * @param cursorObject
     * @param ui
     * @param inventoryBar  
     */
    public void resetCursorIfOverRoom(MouseButtonEvent event, GraphicsGroup checkedGroup,
    Cursor cursorObject, Inventory inventory){
        if (inventory.pointInSlot(event.getPosition()) == false && inInventory == false) {
            resetCursor(checkedGroup, cursorObject);
        }
    }

    public String getIDString(){
        return collectableID;
    }
}
