package analoghorror.inhabitants;

import edu.macalester.graphics.events.*;
import edu.macalester.graphics.*;
import analoghorror.Cursor;
import analoghorror.Inventory;

public class Collectable extends Image {
    boolean inInventory;  // True if CollectableItem is in inventory
    boolean used;  // True if Collectable successfully interacted with an Item at least once
    Point inventorySlot;  // Inventory space; 
    String collectableID;
    String inventoryPath;

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
        this.inventoryPath = path;
        used = false;
    }

    public void setInventorySlot(Point point){  // TODO: Eventually make private
        inventorySlot = point;
    }

    public void resetCenter(){
        setCenter(inventorySlot);
    }   

    /**
     * CollectableItem is no longer used as Cursor and is sent back to its inventory slot.
     * 
     * @param checkedGroup
     * @param cursor
     * @param cursorObject
     */
    public void resetCursor(Inventory inventory, Cursor cursorObject){
        inventory.returnCollectableToLayer(this);;
        resetCenter();
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
    public void resetCursorIfOverRoom(MouseButtonEvent event, Cursor cursorObject, Inventory inventory){
        if (inventory.pointInSlot(event.getPosition()) == false && inInventory == false) {
            resetCursor(inventory, cursorObject);
        }
    }

    public String getIDString(){
        return collectableID;
    }

    public boolean getInInventory(){
        return inInventory;
    }

    public void setInInventory(boolean inInventory){
        this.inInventory = inInventory;
    }

    //idk psuedo code
    public void changePathOnCollection(){
            this.setImagePath(inventoryPath);
    }

    public void setInventoryPath(String inventoryPath) {
        this.inventoryPath = inventoryPath;
    }

    public void setUsedTrue() {
        if(used == false) {
            used = true;
        }
    }

    public boolean getUsed(){
        return used;
    }
}
