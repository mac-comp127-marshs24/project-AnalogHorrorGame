package analoghorror.item;

import java.util.HashSet;
import java.util.Set;

import edu.macalester.graphics.*;

public class Item extends Image{
    boolean defaultState;
    boolean singleUse;  // True if item can only have its state changed once
    String defaultImagePath;
    String modifiedImagePath;
    Set<String> validCollectables;

    public Item(double x, double y, String defaultPath, String modifiedPath, boolean singleUse) {
        super(x, y);
        setImagePath(defaultPath);
        defaultState = true;
        defaultImagePath = defaultPath;
        modifiedImagePath = modifiedPath;
        this.singleUse = singleUse;
        validCollectables = new HashSet<String>();
    }

    public void interaction(Collectable collectable){
        if (defaultState && collectableIsValid(collectable)) {
            setImagePath(modifiedImagePath);
            defaultState = false;
        }
        else if (!defaultState && !singleUse && collectableIsValid(collectable)) {
            setImagePath(defaultImagePath);
            defaultState = true;
        }
    }

    /**
     * @return true if Item is unmodified.
     */
    public boolean getState(){
        return defaultState;
    }

    public void addValidCollectable(Collectable collectable){
        validCollectables.add(collectable.getIDString());
    }

    private boolean collectableIsValid(Collectable collectable){
        return validCollectables.contains(collectable.getIDString());
    }
}
