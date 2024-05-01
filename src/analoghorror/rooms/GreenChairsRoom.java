package analoghorror.rooms;
import java.io.File;
import java.util.Arrays;

import analoghorror.Inventory;
import analoghorror.inhabitants.*;
import edu.macalester.graphics.GraphicsGroup;

public class GreenChairsRoom extends Room{
    Collectable primaryCursor;
    HallwayRoom hallway;
    Inventory inventory;

    Item door;
    Item ratCage;
    Item leaveAnnouncement;
    Collectable poisonedRat;
    Collectable windowBoxKey;
    Collectable poison;
    boolean poisonedRatInteraction;
    boolean roomScare;

    public GreenChairsRoom(HallwayRoom hallway, Collectable hand, String backgroundImage, Inventory inventory) {
        super(backgroundImage);
        this.hallway = hallway;
        this.inventory = inventory;
        primaryCursor =  hand;
        changeRoom = false;
        poisonedRatInteraction = false;
        roomScare = false;
        addRoomInhabitants();
    }

    //add items to roomInhabitants here
	@Override
	public void addRoomInhabitants() {
		door = new Item(716, 60,
            "assets" + File.separator + "GreenChairsRoom" + File.separator + "greenChairRoomDoorClosed.png", false, 2);
        door.setStatePaths(Arrays.asList(
            "assets" + File.separator + "GreenChairsRoom" + File.separator + "greenChairRoomDoorClosed.png",
            "assets" + File.separator + "GreenChairsRoom" + File.separator + "greenChairRoomDoorOpen.png"));
        this.roomInhabitants.add(door);
        ratCage = new Item(500, 201,
            "assets" + File.separator + "GreenChairsRoom" + File.separator + "ratCageSmallRat.png", true, 4);
        ratCage.setStatePaths(
            Arrays.asList("assets" + File.separator + "GreenChairsRoom" + File.separator + "ratCageSmallRat.png",
                "assets" + File.separator + "GreenChairsRoom" + File.separator + "ratCageBigRat.png",
                "assets" + File.separator + "GreenChairsRoom" + File.separator + "ratCageHugeRat.png",
                "assets" + File.separator + "GreenChairsRoom" + File.separator + "ratCageEmpty.png"));
        this.roomInhabitants.add(ratCage);  // Add to "Room" (GraphicsGroup for now)
        poison = new Collectable(141, 286, "assets" + File.separator + "poison.png", "tutorialPoison");
        ratCage.addValidInitCollectable(poison);
        ratCage.addValidSubCollectable(poison);

        leaveAnnouncement = new Item(0, 0,
            "assets" + File.separator + "GreenChairsRoom" + File.separator + "getOut.png", true, 2);
        leaveAnnouncement
            .setStatePaths(Arrays.asList("assets" + File.separator + "GreenChairsRoom" + File.separator + "getOut.png",
                "assets" + File.separator + "GreenChairsRoom" + File.separator + "getOut.png"));

        this.roomInhabitants.add(poison);

        poisonedRat = new Collectable(500, 201,
            "assets" + File.separator + "GreenChairsRoom" + File.separator + "looseRat.png", "rat01");
        poisonedRat
            .setInventoryPath("assets" + File.separator + "GreenChairsRoom" + File.separator + "collectedRat.png");

        windowBoxKey = new Collectable(100, 352,
            "assets" + File.separator + "GreenChairsRoom" + File.separator + "keyOnFloor.png", "windowBoxKey");
        windowBoxKey.setInventoryPath("assets" + File.separator + "GreenChairsRoom" + File.separator + "brassKey.png");
        this.roomInhabitants.add(windowBoxKey);

        // ratCage.addValidInitCollectable(primaryCursor);
        // ratCage.addValidSubCollectable(primaryCursor);
        door.addValidInitCollectable(primaryCursor);
        door.addValidInitCollectable(primaryCursor);

        add(roomInhabitants);
    }

    public void doorInteraction(){
        if (door.getState() == 1) {
            if (poisonedRatInteraction && roomScare == false) {
                super.setBackgroundImage("assets" + File.separator + "GreenChairsRoom" + File.separator + "shadowGreenChairsRoomBG.png");
                roomScare = true;
            }
            else if (poisonedRatInteraction && roomScare) {
                super.setBackgroundImage("assets" + File.separator + "GreenChairsRoom" + File.separator + "slimeGreenChairsRoomBG.png");
            }
            changeRoom = true;
            // System.out.println("room change");  // TESTING
            // setActiveRoom(hallway.getActiveRoom());  // Might be redundant structure
            changeRoom();
        }
    }
   
    @Override
    public void updateRoom(GraphicsGroup displayText) {
        if (ratCage.getState() == 3 && poisonedRatInteraction == false) {
            this.roomInhabitants.add(poisonedRat);
            poisonedRatInteraction = true;
            this.roomInhabitants.add(leaveAnnouncement);
            leaveAnnouncement.addValidInitCollectable(primaryCursor);
            leaveAnnouncement.addValidSubCollectable(primaryCursor);
        }
        if (inventory.getCollectableWithID("windowPoison") != null) {
            ratCage.addValidInitCollectable(inventory.getCollectableWithID("windowPoison"));
            ratCage.addValidSubCollectable(inventory.getCollectableWithID("windowPoison"));
        }
        if (inventory.getCollectableWithID("lecturePoison") != null) {
            ratCage.addValidInitCollectable(inventory.getCollectableWithID("lecturePoison"));
            ratCage.addValidSubCollectable(inventory.getCollectableWithID("lecturePoison"));
        }
        if (poisonedRatInteraction) {
            if (leaveAnnouncement.getState() == 1) {
                leaveAnnouncement.changeState(0);
                this.roomInhabitants.remove(leaveAnnouncement);
            }
        }
        if (inventory.getCollectableWithID("windowPoison") != null
            && (inventory.getCollectableWithID("windowPoison").getUsed())) {
            inventory.disposeOfCollectable(inventory.getCollectableWithID("windowPoison"));
        }
        if (inventory.getCollectableWithID("tutorialPoison") != null
            && (inventory.getCollectableWithID("tutorialPoison").getUsed())) {
            inventory.disposeOfCollectable(inventory.getCollectableWithID("tutorialPoison"));
        }
        if (inventory.getCollectableWithID("lecturePoison") != null
            && (inventory.getCollectableWithID("lecturePoison").getUsed())) {
            inventory.disposeOfCollectable(inventory.getCollectableWithID("lecturePoison"));
        }
        doorInteraction();
    }

    private void changeRoom(){
        if(changeRoom){ //change changeRoom to a specific click event?
            //TODO: setActiveRoom should change the active room to the inputted new room, but ensure that is reflected on the canvas
            hallway.resetActiveRoom();
            setActiveRoom(hallway.getActiveRoom());
            door.changeState(0);
        }
    }

    
}
