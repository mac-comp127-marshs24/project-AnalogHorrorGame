package analoghorror;

import java.util.ArrayList;
import java.util.List;

import analoghorror.item.Collectable;
import edu.macalester.graphics.*;

/**
 * Inventory
 */
public class Inventory extends GraphicsGroup{
    private static final double PADDING = 10;
    private static final double SLOT_WIDTH = 50;
    private static final double SLOT_HEIGHT = 50;

    List<Collectable> inventoryList;
    GraphicsGroup base;
    GraphicsGroup collectableLayer;

    //i feel like we can utilize List.ofCopy here? i made it protected so other classes can add or remove to itemsList but safety seems sus
    // *reminder for me, check registrar lab? i think it was that utilized list.copyOf for defensive copying?
    // protected List<LegacyItem> itemsList = new ArrayList<LegacyItem>();

    // use similar logic for forecast box to add each item object to an individual inventory slot

    public Inventory(double x, double y, double canvasWidth, double canvasHeight, String imagePath){
        super(x, y);
        generator(canvasWidth, canvasHeight,imagePath);
        inventoryList = new ArrayList<Collectable>();  // TODO: Ask Moyartu about implementation —W
    }

    private static void slotInteraction(CanvasWindow canvas){
        /*canvas.OnMouseCLick
         * if slotGroup.getElement at event.getPosition is null do nothing
         * if there is an element maybe mark true? do some item interaction? or change visuals of inventory slot like border?
         */
    }

    /*Basic gist for adding 10 (i think) inventory boxes*/
    private void generator(double canvasWidth, double canvasHeight, String imagePath){
        Image texture = new Image(0, 0, imagePath);
        Rectangle inventoryBar = new Rectangle(0, 0, 742, 68);
        inventoryBar.setStroked(false);
        inventoryBar.setFilled(false);
        base.add(texture);
        base.add(inventoryBar);
        add(base);
        add(collectableLayer);
        // GraphicsGroup slotGroup = new GraphicsGroup();
        // double padding = 0;
        // double x = 0, y = canvasHeight*0.85;

        // for(int i = 0; i < 10; i++){
        //     Rectangle slot = new Rectangle(x, y, SLOT_WIDTH, SLOT_HEIGHT);
        //     slotGroup.add(slot);
        //     x += SLOT_WIDTH + padding;
        // }
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