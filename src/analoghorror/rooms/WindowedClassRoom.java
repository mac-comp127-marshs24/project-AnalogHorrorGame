package analoghorror.rooms;
import edu.macalester.graphics.GraphicsGroup;

import java.io.File;
import java.util.Arrays;

import analoghorror.Inventory;
import analoghorror.Sound;
import analoghorror.inhabitants.*;

public class WindowedClassRoom extends Room{
    boolean addedPoison;

    Collectable primaryCursor;
    HallwayRoom hallway;
    Inventory inventory;

    Item door;
    Item box;

    Collectable poison;
    Collectable openLaptop;
    Collectable closedLaptop;

    public WindowedClassRoom(HallwayRoom hallway, Collectable hand, String backgroundImage, Inventory inventory, GraphicsGroup displayOverlay, Sound primarySound) {
        super(backgroundImage, displayOverlay);
        this.hallway = hallway;
        this.inventory = inventory;
        this.primarySound = primarySound;
        primaryCursor = hand;
        changeRoom = false;
        addedPoison = false;
        
        addRoomInhabitants();
    }

    @Override
    public void addRoomInhabitants() {
        box = new Item(400, 280, "assets" + File.separator + "WindowedClassRoom" + File.separator + "boxClosed.png", true, 2);
        box.setStatePaths(Arrays.asList("assets" + File.separator + "WindowedClassRoom" + File.separator + "boxClosed.png", 
        "assets" + File.separator + "WindowedClassRoom" + File.separator + "boxOpen.png"));
        roomInhabitants.add(box);

        door = new Item(710, 200, "assets" + File.separator + "WindowedClassRoom" + File.separator + "windowedClassRoomArrow.png", false, 2);
        door.setStatePaths(Arrays.asList("assets" + File.separator + "WindowedClassRoom" + File.separator + "windowedClassRoomArrow.png", 
        "assets" + File.separator + "WindowedClassRoom" + File.separator + "windowedClassRoomArrow.png"));
        roomInhabitants.add(door);

        box.addValidSubCollectable(primaryCursor);
        door.addValidSubCollectable(primaryCursor);
        door.addValidInitCollectable(primaryCursor);

        poison = new Collectable(530, 365, "assets" + File.separator + "poison.png", "windowPoison");

        openLaptop = new Collectable(111, 356, "assets" + File.separator + "laptopOpen.png", "laptop");
        openLaptop.setInventoryPath("assets" + File.separator + "laptopClosed.png");

        closedLaptop = new Collectable(231, 302, "assets" + File.separator + "laptopClosed.png", "laptop");
        closedLaptop.setInventoryPath("assets" + File.separator + "laptopClosed.png");

        this.add(roomInhabitants);
    }

    /**
     * Change room if door opens.
     */
    public void doorInteraction(){
        if (door.getState() == 1) {
            changeRoom = true;
            changeRoom();
        }
    }

    @Override
    public void updateRoom() {
        clearDisplayOverlay();
        spawnPoison();
        validateKey();
        doorInteraction();
    }

    /**
     * Set ActiveRoom to hallway and reset door.
     */
    private void changeRoom(){
        if(changeRoom){ 
            hallway.resetActiveRoom();
            setActiveRoom(hallway.getActiveRoom());
            door.changeState(0);
        }
    }

    public void spawnOpenLaptop(){
        this.roomInhabitants.add(openLaptop);
    }

    public void spawnClosedLaptop(){
        this.roomInhabitants.add(closedLaptop);
    }

    private void spawnPoison() {
        if (inventory.getCollectableWithID("windowBoxKey") != null
            && (inventory.getCollectableWithID("windowBoxKey").getUsed())) {
            this.roomInhabitants.add(poison);
            addedPoison = true;
            inventory.disposeOfCollectable(inventory.getCollectableWithID("windowBoxKey"));
        }
    }

    private void validateKey(){
        if (inventory.getCollectableWithID("windowBoxKey") != null){
            box.addValidInitCollectable(inventory.getCollectableWithID("windowBoxKey"));
        }
    }
}
