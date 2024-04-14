package analoghorror;

import java.util.ArrayList;
import java.util.List;

import analoghorror.item.Item;
import edu.macalester.graphics.*;

/**
 * Inventory
 */
public class Inventory extends GraphicsGroup{
    private static final double SLOT_WIDTH = 50;
    private static final double SLOT_HEIGHT = 50;

    //i feel like we can utilize List.ofCopy here? i made it protected so other classes can add or remove to itemsList but safety seems sus
    // *reminder for me, check registrar lab? i think it was that utilized list.copyOf for defensive copying?
    protected List<Item> itemsList = new ArrayList<Item>();

    // use similar logic for forecast box to add each item object to an individual inventory slot



    public Inventory(CanvasWindow canvas){

        /*constructor for inventory obj */
        slotGenerator(canvas);

    }

    private static void slotInteraction(CanvasWindow canvas){
        /*canvas.OnMouseCLick
         * if slotGroup.getElement at event.getPosition is null do nothing
         * if there is an element maybe mark true? do some item interaction? or change visuals of inventory slot like border?
         */
    }

    /*Basic gist for adding 10 (i think) inventory boxes*/
    private void slotGenerator(CanvasWindow canvas){
        GraphicsGroup slotGroup = new GraphicsGroup();
        double padding = 0;
        double x = 0, y = canvas.getHeight()*0.85;

        for(int i = 0; i < 10; i++){
            Rectangle slot = new Rectangle(x, y, SLOT_WIDTH, SLOT_HEIGHT);
            slotGroup.add(slot);
            x += SLOT_WIDTH + padding;
        }


        
    }


    
}