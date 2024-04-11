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

        slotFormation(canvas);

    }

    private static void slotInteraction(CanvasWindow canvas){

    }

    private void slotFormation(CanvasWindow canvas){
        double padding = 0;
        double x = 0, y = canvas.getHeight()*0.85;

        for(int i = 0; i < 10; i++){
            Rectangle slot = new Rectangle(x, y, SLOT_WIDTH, SLOT_HEIGHT);
            canvas.add(slot);
            x += SLOT_WIDTH + padding;
        }
        
    }


    
}