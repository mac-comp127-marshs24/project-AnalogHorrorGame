package analoghorror;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import analoghorror.item.Collectable;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Image;

/**
 * Inventory
 */
public class InventoryPlanning extends GraphicsGroup {
    private static final double PADDING = 20;
    private static final double SLOT_WIDTH = 50;
    private static final double SLOT_HEIGHT = 50;
    private static final int inventoryCapacity = 11;

    private static CanvasWindow temp;
    private static final int CANVAS_WIDTH = 854;
    private static final int CANVAS_HEIGHT = 480;

    private static GraphicsGroup slotGroup = new GraphicsGroup();
    private static List<GraphicsObject> slotList = new ArrayList<>();

    private static ArrayList<Collectable> inventorySet = new ArrayList<Collectable>(inventoryCapacity);  // TODO: Ask
                                                                                                         // Moyartu
    // about implementation
    // —W;

    public InventoryPlanning() {
         
    }

    public static void main(String[] args) {
        temp = new CanvasWindow("temp", CANVAS_WIDTH, CANVAS_HEIGHT);
        Collectable key = new Collectable(200, 180, "assets" + File.separator + "key.png", "key01");
        Collectable doorBell = new Collectable(600, 40, "assets" + File.separator + "studentCard.png", "doorbell01");
        Collectable sonic = new Collectable(70, 70, "assets" + File.separator + "sonicForward.png", "sonic");
        generator(CANVAS_HEIGHT);

        addToSlot(key);
        addToSlot(doorBell);
        addToSlot(sonic);

        inventorySet.remove(doorBell);
        temp.add(key);   
        temp.add(doorBell);
        temp.add(sonic);    
        System.out.println(key.getPosition());
    }

    /* PSUEDOCODE!!!!!!!!!! */
    /*
     * Add to inventory: - check if collectable is in set: inventorySet.contains(collectable)
     */
    // public static boolean isInInventory(Collectable collectable) {
    //     return inventorySet.contains(collectable);
    // }

    /*
     * - IF COLLECTABLE IS NOT IN SET:
     */

    public static void addToSlot(Collectable collectable) {
//   if (inventorySet.size() < inventoryCapacity && !isInInventory(collectable)) {
    inventorySet.add(collectable);
    int slotIndex = inventorySet.indexOf(collectable);  // Get the index of the added item
    slotPlacement(collectable, slotList.get(slotIndex));  // Pass both collectable and slot
//   }
}
    
    private static void slotPlacement(Collectable collectable, GraphicsObject slot) {
  collectable.setCenter(slot.getCenter());
}


    /* -set position to the box corresponding to the set index */


    /*
     * -KEEP SLOT GENERATOR & GENERATE 11 BOXES, EACH USING INDIVIDUAL INVENTORY BLOCK INSTEAD OF
     * RECTANGLES
     */

    /* Basic gist for adding 10 (i think) inventory boxes */
    private static void generator(double canvasHeight) {
        double x = 0, y = canvasHeight * 0.85;

        for (int i = 0; i < 11; i++) {
            Image slot = new Image(x, y, "assets" + File.separator + "slotbg.png");
            slotGroup.add(slot);
            slotList.add(slot);
            x += SLOT_WIDTH + PADDING;
        }
        temp.add(slotGroup);
    }


    /*
     * - ADD SLOTS TO A LIST OF SLOTS? - collectable.setPosition(slotList[3].getCenter())
     * 
     */

}
