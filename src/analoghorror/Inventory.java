package analoghorror;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import analoghorror.item.Collectable;
import edu.macalester.graphics.*;

/**
 * Inventory
 */
public class Inventory extends GraphicsGroup{
    private static final double HORIZONTAL_PADDING = 8;
    private static final double HORIZONTAL_OFFSET = 27;
    private static final double VERTICAL_OFFSET = 9;
    private static final double SLOT_WIDTH = 55;
    private static final int INVENTORY_CAPACITY = 11;

    GraphicsGroup base;
    GraphicsGroup collectableLayer;
    List<Rectangle> slotBoxList;
    List<Collectable> inventoryList;

    public Inventory(double x, double y, double inventoryWidth, double inventoryHeight, String imagePath){
        super(x, y);
        base = new GraphicsGroup();
        collectableLayer = new GraphicsGroup();
        slotBoxList = new ArrayList<Rectangle>();
        inventoryList = new ArrayList<Collectable>(Collections.nCopies(INVENTORY_CAPACITY, null));  // TODO: Ask Moyartu about implementation —W
        generator(inventoryWidth, inventoryHeight, imagePath); // 742 x 68
    }

    private static void slotInteraction(CanvasWindow canvas){
        /*canvas.OnMouseCLick
         * if slotGroup.getElement at event.getPosition is null do nothing
         * if there is an element maybe mark true? do some item interaction? or change visuals of inventory slot like border?
         */
    }

    private void generator(double inventoryWidth, double inventoryHeight, String imagePath){
        Image texture = new Image(0, 0, imagePath);
        Rectangle inventoryBar = new Rectangle(0, 0, inventoryWidth, inventoryHeight);
        inventoryBar.setStroked(false);
        inventoryBar.setFilled(false);
        base.add(texture);
        base.add(inventoryBar);
        double slotRowStartX = 0 + HORIZONTAL_OFFSET, slotY = texture.getY() + VERTICAL_OFFSET;  // just center later? Look at y
        for (int i = 0; i < INVENTORY_CAPACITY; i++) {
            Rectangle slot = new Rectangle(slotRowStartX, slotY, SLOT_WIDTH, SLOT_WIDTH);
            slot.setFillColor(Color.RED);
            slotBoxList.add(slot);
            base.add(slot);
            slotRowStartX += slot.getWidth() + HORIZONTAL_PADDING;
        }
        add(base);
        add(collectableLayer);
    }

    /**
     * Primarily for use in Cursor restCursorIfOverRoom();
     * 
     * If the Point is over an Rectangle (slot) within Inventory, returns true.
     * 
     * @param point
     * @return
     */
    public boolean pointInSlot(Point point){
        if (getElementAt(point) instanceof Rectangle) {
            return true;
        }
        else {
            return false;
        }
    }

    public GraphicsGroup getCollectableLayer(){
        return collectableLayer;
    }

    public void testCollectable(Collectable collectable, GraphicsGroup cursorGroup, Cursor cursor){
        if (collectable.getInInventory()) {
            assignCollectable(collectable, cursorGroup, cursor);
        }
        else if (!collectable.getInInventory()) {
           acquireCollectable(collectable, cursor);
        }
    }

    public void acquireCollectable(Collectable collectable, Cursor cursor){
         // Put CollectableItem in inventory
        // TODO: Call an inventory.getAvailableSlot() and use it to setInventorySlot
        collectable.setCenter(inventorySlot);  
        cursor.resetCursor();
        collectable.setInInventory(true);  // CollectableItem is now in inventory
    }

    public void assignCollectable(Collectable collectable, GraphicsGroup cursorGroup, Cursor cursor){
        // Cursor is now CollectableItem and CollectableItem is now part of the cursor group
        cursor.setCursor(collectable);
        collectableLayer.remove(cursor.getCursor());  // or game.remove
        cursorGroup.add(cursor);
        collectable.setInInventory(false);  // CollectableItem is out of inventory
    }

    // public Point getAvailableSlot(){
    //     loop over list of slots
    //         if slot is occupied
    //             keep looping
    //         if slot is empty
    //             return slot.getCenter()
    //     else return exception 
    // }
    
}