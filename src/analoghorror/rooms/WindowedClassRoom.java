package analoghorror.rooms;
import edu.macalester.graphics.GraphicsGroup;

import java.io.File;
import java.util.Arrays;

import analoghorror.Inventory;
import analoghorror.inhabitants.*;

public class WindowedClassRoom extends Room{
    Collectable primaryCursor;
    HallwayRoom hallway;
    Inventory inventory;

    //TODO: Replace with actual items
    Item box;
    Item door;
    Collectable poison;
    // Collectable card;
    // Item sonic;

    boolean addedPoison;
    

    public WindowedClassRoom(HallwayRoom hallway, Collectable hand, String backgroundImage, Inventory inventory, GraphicsGroup displayOverlay) {
        super(backgroundImage, displayOverlay);
        this.hallway = hallway;
        this.inventory = inventory;
        primaryCursor = hand;
        changeRoom = false;
        addRoomInhabitants();
        addedPoison = false;
    }

    @Override
    public void addRoomInhabitants() {
        box = new Item(400, 280, "assets" + File.separator + "boxClosed.png", true, 2);
        box.setStatePaths(Arrays.asList("assets" + File.separator + "boxClosed.png", "assets" + File.separator + "boxOpen.png"));
        roomInhabitants.add(box);  // Add to "Room" (GraphicsGroup for now)

        poison = new Collectable(530, 365, "assets" + File.separator + "poison.png", "windowPoison");

        door = new Item(385, 120, "assets" + File.separator + "doorClosed.png", false, 2);
        door.setStatePaths(Arrays.asList("assets" + File.separator + "doorClosed.png", "assets" + File.separator + "doorOpen.png"));
        roomInhabitants.add(door);

        // card = new Collectable(528, 325, "assets" + File.separator + "studentCard.png", "card01");
        // roomInhabitants.add(card);
        // door.addValidInitCollectable(card);
        //box.addValidInitCollectable(inventory.getCollectableWithID("windowBoxKey"));

        box.addValidSubCollectable(primaryCursor);
        door.addValidSubCollectable(primaryCursor);
        door.addValidInitCollectable(primaryCursor);

        this.add(roomInhabitants);
    }

    public void doorInteraction(){
        if (door.getState() == 1) {
            changeRoom = true;
            changeRoom();
        }

    }

    @Override
    public void updateRoom() {
        if (inventory.getCollectableWithID("windowBoxKey") != null && (inventory.getCollectableWithID("windowBoxKey").getUsed())) {
           this.roomInhabitants.add(poison);
           addedPoison = true;
           inventory.disposeOfCollectable(inventory.getCollectableWithID("windowBoxKey")); 
        }
        if (inventory.getCollectableWithID("windowBoxKey") != null){ //tried adding outside of update room, still crashes game
            box.addValidInitCollectable(inventory.getCollectableWithID("windowBoxKey"));
        }
        
        doorInteraction();
    }

    private void changeRoom(){
        if(changeRoom){ 
            hallway.resetActiveRoom();
            setActiveRoom(hallway.getActiveRoom());
            door.changeState(0);
        }
    }
    
}
