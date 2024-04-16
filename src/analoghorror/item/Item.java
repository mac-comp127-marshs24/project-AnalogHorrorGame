package analoghorror.item;

import edu.macalester.graphics.*;

public abstract class Item extends Image{
    boolean defaultState;

    public Item(double x, double y, String path) {
        super(x, y);
        setImagePath(path);
        defaultState = true;
    }

    public void interaction(Item interactable){
    }

    /**
     * @return true if Item is unmodified.
     */
    public boolean getState(){
        return defaultState;
    }
}
