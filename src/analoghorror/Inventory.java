package analoghorror;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import analoghorror.item.Collectable;
import edu.macalester.graphics.*;
import edu.macalester.graphics.events.MouseButtonEvent;

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
        inventoryList = new ArrayList<Collectable>(Collections.nCopies(INVENTORY_CAPACITY, null));
        generator(inventoryWidth, inventoryHeight, imagePath); // 742 x 68
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
            slot.setFillColor(Color.RED); // Change later
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

    public void acquireCollectable(Collectable collectable, Cursor cursor){
        // Put CollectableItem in inventory
        inventoryList.set(getAvailableSlotIndex(), collectable);  // Set first empty index to Collectable
        
        int collectableSlot = inventoryList.indexOf(collectable);  // Set collectable "slot" to center of available slotBox
        collectable.setInventorySlot(slotBoxList.get(collectableSlot).getCenter()); 
        
        collectableLayer.add(collectable);  // Add collectable to GraphicsGroup
        collectable.resetCenter();  // Send to home slotBox

        cursor.resetCursor();
        collectable.setInInventory(true);  // CollectableItem is now in inventory
    }

    public void assignCollectable(Collectable collectable, GraphicsGroup cursorGroup, Cursor cursor, MouseButtonEvent event){
        // Cursor is now CollectableItem and CollectableItem is now part of the cursor group
        cursor.setCursor(collectable);
        collectableLayer.remove(cursor.getCursor());
        cursorGroup.add(cursor);
        cursor.setCenter(event.getPosition());
        collectable.setInInventory(false);  // CollectableItem is out of inventory
    }

    public int getAvailableSlotIndex(){
        int firstAvailable = inventoryList.indexOf(null);  // Returns first instance of null
        return firstAvailable;
    }

    public void returnCollectableToLayer(Collectable collectable){
        collectableLayer.add(collectable);  // Add collectable to GraphicsGroup
        collectable.resetCenter();  // Send to home slotBox
    }
    
}