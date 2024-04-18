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

    private static ArrayList<Collectable> inventoryList = new ArrayList<Collectable>(inventoryCapacity); 

    public InventoryPlanning() {
        //maybe add slotList, invList and slotGroup to constructor
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

        inventoryList.remove(doorBell);
        temp.add(key);
        temp.add(doorBell);
        temp.add(sonic);
        System.out.println(key.getPosition());
    }

    /* PSUEDOCODE!!!!!!!!!! */
    /*
     * Add to inventory: - check if collectable is in set: inventoryList.contains(collectable)
     */
    // public static boolean isInInventory(Collectable collectable) {
    // return inventoryList.contains(collectable);
    // }

    public static void addToSlot(Collectable collectable) {
        inventoryList.add(collectable);
        /* Set position to the box corresponding to the set index */
        int slotIndex = inventoryList.indexOf(collectable);  // Get the index of the added item
        slotPlacement(collectable, slotList.get(slotIndex));  // Pass both collectable and slot
    }
    
    private static void slotPlacement(Collectable collectable, GraphicsObject slot) {
        collectable.setCenter(slot.getCenter()); //Sets center of collectable to slot center
    }

    /*
     * Generates 11 slots, each using individual inventory block instead of rectangles
     */
    private static void generator(double canvasHeight) {
        double x = 0, y = canvasHeight * 0.85;
        for (int i = 0; i < 11; i++) {
            Image slot = new Image(x, y, "assets" + File.separator + "slotbg.png");
            slotGroup.add(slot); //Adds slots to an overall group (easier for movement?)
            slotList.add(slot); //Add slots to a list of slots
            x += SLOT_WIDTH + PADDING;
        }
        temp.add(slotGroup);
    }

}
