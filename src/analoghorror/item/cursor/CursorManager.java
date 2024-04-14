package analoghorror.item.cursor;

import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.*;

public class CursorManager{
    GraphicsObject cursorObject;
    GraphicsObject cursorDefault;
    GraphicsGroup cursor;

    public CursorManager(){
        cursor = new GraphicsGroup();
        cursorDefault = new Ellipse(0, 0, 10, 10);
        cursorObject = cursorDefault;
    }

    /*Setters & Getters*/

    /**
     * @return Item being used as cursor object
     */
    public GraphicsObject getCursorObject(){
        return this.cursorObject;
    }

    /**
     * @return Current cursor
     */
    public GraphicsGroup getCursorGroup(){
        return this.cursor;
    }

   /**
    * Sets current cursor
    * @param newCursor 
    */
    public void setCursorObject(GraphicsObject newCursor){
        cursorObject = newCursor;
        cursor.add(cursorObject);
    }

    /*Behavior */

    /*Returns cursor to default ellipse */
    public void returnCursorToDefault(){
        cursorObject = cursorDefault;
    }

    /**
     * Removes current cursor object from game and makes it into a cursor
     * @param game
     */
    public void addToCursorGroup(GraphicsGroup game){
        // cursor is now key and key is now part of the cursor group —W
        game.remove(getCursorObject());
        cursor.add(getCursorObject());
    }

}