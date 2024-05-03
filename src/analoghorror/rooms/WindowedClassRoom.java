package analoghorror.rooms;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;

import java.io.File;
import java.util.Arrays;

import analoghorror.Inventory;
import analoghorror.inhabitants.*;

public class WindowedClassRoom extends Room{
    Collectable primaryCursor;
    HallwayRoom hallway;
    Inventory inventory;

    Item box;
    Item door;
    Collectable poison;
    Collectable openLaptop;
    Collectable closedLaptop;


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
        box = new Item(400, 280, "assets" + File.separator + "WindowedClassRoom" + File.separator + "boxClosed.png", true, 2);
        box.setStatePaths(Arrays.asList("assets" + File.separator + "WindowedClassRoom" + File.separator + "boxClosed.png", 
        "assets" + File.separator + "WindowedClassRoom" + File.separator + "boxOpen.png"));
        roomInhabitants.add(box);  // Add to "Room" (GraphicsGroup for now)

        poison = new Collectable(530, 365, "assets" + File.separator + "poison.png", "windowPoison");

        door = new Item(710, 200, "assets" + File.separator + "WindowedClassRoom" + File.separator + "windowedClassRoomArrow.png", false, 2);
        door.setStatePaths(Arrays.asList("assets" + File.separator + "WindowedClassRoom" + File.separator + "windowedClassRoomArrow.png", 
        "assets" + File.separator + "WindowedClassRoom" + File.separator + "windowedClassRoomArrow.png"));
        roomInhabitants.add(door);

        box.addValidSubCollectable(primaryCursor);
        door.addValidSubCollectable(primaryCursor);
        door.addValidInitCollectable(primaryCursor);

        openLaptop = new Collectable(111, 356, "assets" + File.separator + "laptopOpen.png", "laptop");
        openLaptop.setInventoryPath("assets" + File.separator + "laptopClosed.png");

        closedLaptop = new Collectable(231, 302, "assets" + File.separator + "laptopClosed.png", "laptop");
        closedLaptop.setInventoryPath("assets" + File.separator + "laptopClosed.png");

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
        doorInteraction();
        if (displayOverlay.getWidth() != 0) {
            displayOverlay.removeAll();
        }
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
    
    public void jumpscare(){
        displayOverlay.add(new Image("assets" + File.separator + "piper" + File.separator + "hands.png"));
        scareDelay();
    }

    public void spawnOpenLaptop(){
        this.roomInhabitants.add(openLaptop);
    }

    public void spawnClosedLaptop(){
        this.roomInhabitants.add(closedLaptop);
    }
}
