package analoghorror.item;

import java.util.HashSet;
import java.util.Set;

// import analoghorror.Cursor;
import edu.macalester.graphics.*;

public class Item extends Image{
    boolean defaultState;
    boolean singleUse;  // True if item can only have its state changed once
    String defaultImagePath;
    String modifiedImagePath;
    Set<String> validInitialCollectables;
    Set<String> validSubCollectables;

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
        validInitialCollectables = new HashSet<String>();
        validSubCollectables = new HashSet<String>();
    }

    /**
     * Changes Item texture and state if interacted with using a Collectable in the Item's validCollectables
     * Set; singleUse boolean on construction determines number of times the Item can change its state.
     * 
     * @param collectable
     */
    public void interaction(Collectable collectable){
        if (defaultState && collectableIsValid(collectable, validInitialCollectables)) {
            setImagePath(modifiedImagePath);
            defaultState = false;
        }
        else if (!defaultState && !singleUse && collectableIsValid(collectable, validSubCollectables)) {
            setImagePath(defaultImagePath);
            defaultState = true;
        }
    }

    /**
     * @return true if Item is unmodified or in its default state.
     */
    public boolean getState(){
        return defaultState;
    }

    /**
     * Changes the state of the Item to the opposite of its current.
     */
    public void changeState(){
        if (defaultState) {
            setImagePath(modifiedImagePath);
            defaultState = false;
        } 
        else if (!defaultState) {
            setImagePath(defaultImagePath);
            defaultState = true;
        }
    }

    /**
     * Adds Collectable to the internal validInitialCollectable Set to be referenced upon interaction();
     * 
     * Initial Collectables can be used to change from a default state to a modified state.
     * 
     * @param collectable
     */
    public void addValidInitCollectable(Collectable collectable){
        validInitialCollectables.add(collectable.getIDString());
    }

    /**
     * Adds Collectable to the internal validSubCollectable Set to be referenced upon interaction();
     * 
     * Sub Collectables can be used to change from a modified state back to a default state.
     * 
     * @param collectable
     */
    public void addValidSubCollectable(Collectable collectable){
        validSubCollectables.add(collectable.getIDString());
    }

    private boolean collectableIsValid(Collectable collectable, Set<String> set){
        return set.contains(collectable.getIDString());
    }
}
