package analoghorror.item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.macalester.graphics.*;

public class Item extends Image{
    boolean defaultState;
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
        defaultState = true;
        defaultImagePath = defaultPath;
        itemStates = numberOfItemStates - 1;
        currentState = 0;
        singleUse = isSingleUse;
        validInitialCollectables = new HashSet<String>();
        validSubCollectables = new HashSet<String>();
        itemTextures = new ArrayList<>(numberOfItemStates);
    }

    /**
     * Changes Item texture and state if interacted with using a Collectable in the Item's validCollectables
     * Set; singleUse boolean on construction determines number of times the Item can change its state.
     * 
     * @param collectable
     */
    public void interaction(Collectable collectable){
        // if (defaultState && collectableIsValid(collectable, validInitialCollectables)) {
        //     setImagePath(modifiedImagePath);
        //     defaultState = false;
        // }
        // else if (!defaultState && !singleUse && collectableIsValid(collectable, validSubCollectables)) {
        //     setImagePath(defaultImagePath);
        //     defaultState = true;
        // }
        System.out.println("It is interacting. Collectable is " + collectable.getIDString());
        System.out.println(validInitialCollectables + " are the valid initials");
        System.out.println(validSubCollectables + " are the valid subs");
        if (currentState == itemStates && singleUse == false) {
            currentState = 0;
            setImagePath(itemTextures.get(currentState));
            System.out.println("First condition");
        }
        else if (currentState == 0 && collectableIsValid(collectable, validInitialCollectables)) {
            currentState++;
            setImagePath(itemTextures.get(currentState));  // itemTextures.get(1)
            System.out.println("Second condition");
        }
        else if (currentState > 0 && currentState < itemStates
            && collectableIsValid(collectable, validSubCollectables)) {
            currentState++;
            System.out.println("Current state is " + currentState);
            setImagePath(itemTextures.get(currentState - 1));  // itemTextures.get(1)
            System.out.println("Third condition");
        }
    }

    /**
     * @return true if Item is unmodified or in its default state.
     * 
     * TODO: Adjust logic
     */
    public boolean getState(){
        return defaultState;
    }

    /**
     * Changes the state of the Item to the opposite of its current.
     */
    public void changeState(){
        // if (defaultState) {
        //     setImagePath(modifiedImagePath);
        //     defaultState = false;
        // } 
        // else if (!defaultState) {
        //     setImagePath(defaultImagePath);
        //     defaultState = true;
        // }

        if (currentState == itemStates && singleUse == false) {
            currentState = 0;
            if (currentState < itemStates) {
                currentState++;
                setImagePath(itemTextures.get(currentState));
            }
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

    public void setStatePaths(List<String> pathList){
        System.out.println(pathList + " paths in pathList");
        if (pathList.size() == itemStates + 1) {
            for (String path : pathList) {
                itemTextures.add(path);
            }
        }   
        else {
            // return exception
        }
        System.out.println(itemTextures + " itemTextures in setStatePaths()");  // testing
    }
}
