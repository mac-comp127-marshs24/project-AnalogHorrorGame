package analoghorror.item;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import analoghorror.HorrorGame;
import analoghorror.item.cursor.CursorManager;
import edu.macalester.graphics.*;
import edu.macalester.graphics.events.MouseButtonEvent;

public abstract class Item extends GraphicsObject{
    private CanvasWindow canvas;
    GraphicsGroup game;
    private CursorManager cursorManager;
    GraphicsGroup ui;

    /*Inventory */
    private Boolean itemInInventory;

    /*Item interaction */
    private Boolean interactable;

    public Item(CanvasWindow window, GraphicsGroup game, GraphicsGroup cursor, Item item){
        this.canvas = window;
        this.game = game;
        this.cursorManager = new CursorManager();
        this.ui = new GraphicsGroup();

        itemInInventory = false;
        interactable = true;

        itemLogic(game, item);
    }

    /**
     * Sets item to element clicked on within a given group.
     * @param event
     * @param group
     * @return Item at click
     */
    private GraphicsObject check(MouseButtonEvent event, GraphicsGroup group) {
        GraphicsObject item = group.getElementAt(event.getPosition());
        return item;
    }

    /**
     * Logic for game behavior.
     * @param game
     * @param item
     */
    public void itemLogic(GraphicsGroup game, Item item) {  
        //canvas animate
        canvas.animate(() -> {
            canvas.onMouseMove(event -> {
                // if the key isn't set as the cursor object, uses cursorDefault to prevent exception errors
                // (invisible, but could be hand l8r) —W
                cursorManager.getCursorObject().setCenter(event.getPosition());
            });
            canvas.draw();
        });
        canvas.onClick(event -> {
            objectUnderClick(event);

            if (interactable){
                itemInteraction(event, item, game, ui);
            }
            
        });
    }

   /**
    * Checks if Item is in inventory
    * @return True (yes) or false (no)
    */
    protected boolean isInInventory(){
        return this.itemInInventory;
    }

    /**
     * Checks if Item under click is current Item whether or not its in inventory.
     * @param event 
     */
    private void objectUnderClick(MouseButtonEvent event){
        /*Is in inventory */

        // if the object under the click is key and key isn't in the inventory —W
        if (check(event, game) == this && itemInInventory == false) {
            // put key in inventory
            game.getElementAt(event.getPosition()).setCenter(HorrorGame.getKeyHome()); // spot in inventory (use a Set or smthn) in Item —W
            cursorManager.returnCursorToDefault();
            itemInInventory = true;  // key is now in inventory —W
        }

        /*Is not in inventory */

        // if key is under click and key is in inventory —W
        if (check(event, game) == this && itemInInventory == true) {
            // cursor is now key and key is now part of the cursor group —W
            cursorManager.setCursorObject(check(event, game));
            cursorManager.addToCursorGroup(game);
        
            itemInInventory = false;  // key is out of inventory —W
        }

    }

    /**
     * Resets Item with a click if you aren't using it with its related interactable.
     * @param event
     * @param ui
     */
    private void resetUponClick(MouseButtonEvent event, GraphicsGroup ui){
        // I wanted the key to reset upon a click even if you aren't using it over a box —W
        if (check(event, ui) != HorrorGame.inventoryBar && isInInventory() == false) {
            cursorManager.addToCursorGroup(game);
            this.setCenter(HorrorGame.getKeyHome());

            cursorManager.returnCursorToDefault();
            this.itemInInventory = true;
        }
    }

    /**
     * Behavior for interaction: If the cursor is the current item and it is clicked over it's related interactable, change the subject of interaction and reset current item in inventory. Otherwise, reset item upon click.
     * @param event
     * @param subjectOfInteraction
     * @param game
     * @param ui
     */
    private void itemInteraction(MouseButtonEvent event, Item subjectOfInteraction, GraphicsGroup game, GraphicsGroup ui){
        //     /*Interactable */
                // if the cursor is key and clicked over box —W
                if (check(event, cursorManager.getCursorGroup()) == this) {
                    if (check(event, game) == subjectOfInteraction) {
                        Boolean subjectBool = subjectOfInteraction.itemInInventory; //change to .isInInventory -M
        
                        // change box and reset key in inventory —W
                        if (!subjectBool) {
                            //subjectOfInteraction.setStrokeWidth(10);
                        }
                        if (subjectBool){
                            //subjectOfInteraction.setStrokeWidth(1);
                        }
                        cursorManager.addToCursorGroup(game);
                        this.setCenter(HorrorGame.getKeyHome());
                        cursorManager.returnCursorToDefault();
                        subjectBool = true;
                        this.itemInInventory = true;
                    }
                    
                }
        
                resetUponClick(event, ui);
        }
}
