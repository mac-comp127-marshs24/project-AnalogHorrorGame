package analoghorror;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.*;
import edu.macalester.graphics.events.MouseButtonEventHandler;

public class KeyObj /*extends GraphicsObject*/ {
    public KeyObj(){
    }

    //boolean attribute: is it picked up? 
    // if yes -> unable to pick up again

    // boolean attribute: is it being held? 
    // informs the interactable object about whether its the corresponding key object

    public void pickUp(){
        canvas.onClick(() -> { //listens for if its clicked on the canvas
            //checks for whether it is already in inventory
            //uses method in inventory class -> adds it the end of list of items in inventory
            //displays it in inventory in the next available empty slot
            //remove it from the current game canvas
        inventory.pickUp();
            //


    });
    }
}
