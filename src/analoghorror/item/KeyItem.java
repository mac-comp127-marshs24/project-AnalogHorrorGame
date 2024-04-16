package analoghorror.item;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.events.MouseButtonEvent;

import edu.macalester.graphics.*;

public class KeyItem extends Ellipse{
    boolean inInventory;  // true if in inventory —W
    Point inventorySlot;  // inventory space —W; we should have an inventory class to help with slot management

    public KeyItem(double x, double y, double width, double height, Rectangle inventory) {
        super(x, y, width, height);
        inInventory = false;
        inventorySlot = new Point(108, inventory.getCenter().getY());

        //TODO Auto-generated constructor stub
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

    public boolean interaction(MouseButtonEvent event, GraphicsGroup checkedGroup, boolean itemInteractionBool, Rectangle interactable, GraphicsGroup cursor, 
        Cursor cursorObject, GraphicsGroup ui, Rectangle inventoryBar){  // listen, I'm sorry; this is stupid but I gotta make do with the garbage temp boxBool shit I made earlier
            // change box and reset key in inventory —W
            if (!itemInteractionBool) {
                interactable.setStrokeWidth(10);
                return true;
            }
            else if (itemInteractionBool){
                interactable.setStrokeWidth(1);
                return false;
            }
            return itemInteractionBool;
        }

    public void resetCursorAfterInteraction(MouseButtonEvent event, GraphicsGroup checkedGroup, GraphicsGroup cursor, 
    Cursor cursorObject, GraphicsGroup ui, Rectangle inventoryBar){
        cursor.remove(cursorObject);
        checkedGroup.add(cursorObject.getCursor());
        this.setCenter(inventorySlot);
        cursorObject.resetCursor();
        inInventory = true;
    }

    public void resetCursor(MouseButtonEvent event, GraphicsGroup checkedGroup, GraphicsGroup cursor, 
    Cursor cursorObject, GraphicsGroup ui, Rectangle inventoryBar){
        if (ui.getElementAt(event.getPosition()) != inventoryBar && inInventory == false) {
            cursor.remove(cursorObject);
            checkedGroup.add(this);
            this.setCenter(inventorySlot);
            inInventory = true;  // I wanted the key to reset upon a click even if you aren't using it over a box —W
        }
    }
}
