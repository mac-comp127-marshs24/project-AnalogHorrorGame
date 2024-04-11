package analoghorror;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.*;

/**
 * Inventory
 */
public class Inventory extends GraphicsGroup{
    private static final double SLOT_WIDTH = 50;
    private static final double SLOT_HEIGHT = 50;

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