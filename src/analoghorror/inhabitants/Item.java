package analoghorror.inhabitants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.macalester.graphics.*;

public class Item extends Image{
    boolean singleUse;  // True if item can only have its state changed once
    String defaultImagePath;
    int itemStates;
    int currentState;
    Set<String> validInitialCollectables;
    Set<String> validSubCollectables;
    List<String> itemTextures;

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
    public Item(double x, double y, String defaultPath, boolean isSingleUse, int numberOfItemStates) {
        super(x, y);
        setImagePath(defaultPath);
        defaultImagePath = defaultPath;
        itemStates = numberOfItemStates - 1;
        currentState = 0;
        singleUse = isSingleUse;
        validInitialCollectables = new HashSet<String>();
        validSubCollectables = new HashSet<String>();
        itemTextures = new ArrayList<>(numberOfItemStates);
    }

    /**
     * Call before interaction();
     * 
     * Pass the List of images you want to use as frames. Be sure not to pass
     * more than the number of item states specified in the constructor.
     * 
     * @param pathList List of Strings to be used as texture Paths
     */
    public void setStatePaths(List<String> pathList){
        if (pathList.size() == itemStates + 1) {
            for (String path : pathList) {
                itemTextures.add(path);
            }
        }   
        else {
            throw new IllegalArgumentException("pathList size exceeds itemStates");        
        }
    }

    /**
     * Changes Item texture and state if interacted with using a Collectable in the Item's validCollectables
     * Set; singleUse boolean on construction determines number of times the Item can change its state.
     * 
     * @param collectable
     */
    public void interaction(Collectable collectable){
        if (currentState == itemStates && singleUse == false && collectableIsValid(collectable, validSubCollectables)) {
            currentState = 0;
            setImagePath(itemTextures.get(currentState));
            collectable.setUsedTrue();
        }
        else if (currentState == 0 && collectableIsValid(collectable, validInitialCollectables)) {
            currentState++;
            setImagePath(itemTextures.get(currentState));
            collectable.setUsedTrue();
        }
        else if (currentState > 0 && currentState < itemStates
            && collectableIsValid(collectable, validSubCollectables)) {
            currentState++;
            setImagePath(itemTextures.get(currentState));
            collectable.setUsedTrue();
        }
    }

    /**
     * @return currentState number.
     */
    public int getState(){
        return currentState;
    }

    /**
     * Changes the state of the Item to the stateNumber specified.
     * 
     * If you want to increment by one, call changeState(getState() + 1);
     */
    public void changeState(int stateNumber){
        currentState = stateNumber;
        if (currentState > itemStates) {
            currentState = 0;
            throw new IllegalArgumentException("stateNumber exceeds itemStates");
        }
        setImagePath(itemTextures.get(currentState));
    }

    /**
     * @return whether the Item can have its state set to 0 once again.
     * 
     * Couple with getState() to see if an Item has been modified.
     */
    public boolean isSingleUse(){
        return singleUse;
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

    /**
     * @param collectable
     * @param set
     * @return true if the specified Collectable is found in the internal Set specified
     */
    protected boolean collectableIsValid(Collectable collectable, Set<String> set){
        return set.contains(collectable.getIDString());
    }

    
}
