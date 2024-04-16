package analoghorror.item;

import edu.macalester.graphics.*;

public class KeyItem extends CollectableItem {

    public KeyItem(double x, double y, Rectangle inventory, String path) {
        super(x, y, inventory, path);
        setImagePath(path);
        inInventory = false;
        inventorySlot = new Point(108, inventory.getCenter().getY());
    }

    @Override
    public void interaction(Item interactable){
            interactable.interaction(interactable);
        }
   
    
}
