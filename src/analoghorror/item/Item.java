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

    /**
     * An interactable game Item that extends the Image GraphicsObject. Arguments determine textures and if
     * it can alternate states once or twice. Item will not change states unless it s interacted with using
     * a Collectable that has been added to its validCollectables Set with addValidCollectable();
     * 
     * @param x
     * @param y
     * @param defaultPath Default texture
     * @param modifiedPath Changed state texture
     * @param singleUse Set to true if Item can only have its state changed once
     */
    public Item(double x, double y, String defaultPath, String modifiedPath, boolean singleUse) {
        super(x, y);
        setImagePath(defaultPath);
        defaultState = true;
        defaultImagePath = defaultPath;
        modifiedImagePath = modifiedPath;
        this.singleUse = singleUse;
        validCollectables = new HashSet<String>();
    }

    /**
     * Changes Item texture and state if interacted with using a Collectable in the Item's validCollectables
     * Set; singleUse boolean on construction determines number of times the Item can change its state.
     * 
     * @param collectable
     */
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

    /**
     * Adds Collectable to the internal validCollectable Set to be references upon interaction();
     * 
     * @param collectable
     */
    public void addValidCollectable(Collectable collectable){
        validCollectables.add(collectable.getIDString());
    }

    private boolean collectableIsValid(Collectable collectable){
        return validCollectables.contains(collectable.getIDString());
    }
}
