package analoghorror.rooms;

import java.io.File;
import java.util.Arrays;

import analoghorror.Inventory;
import analoghorror.inhabitants.*;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;

public class GreenChairsRoom extends Room {
    Collectable primaryCursor;
    HallwayRoom hallway;
    Inventory inventory;

    Item door;
    Item ratCage;
    Item leaveAnnouncement;
    Collectable openLaptop;
    Collectable poisonedRat;
    Collectable windowBoxKey;
    Collectable poison;
    boolean poisonedRatInteraction;
    boolean roomScare;
    boolean leaveAfterAnnouncement;

    public GreenChairsRoom(HallwayRoom hallway, Collectable hand, String backgroundImage, Inventory inventory, GraphicsGroup displayOverlay) {
        super(backgroundImage, displayOverlay);
        this.hallway = hallway;
        this.inventory = inventory;
        primaryCursor = hand;
        changeRoom = false;
        poisonedRatInteraction = false;
        roomScare = false;
        addRoomInhabitants();
    }

    // add items to roomInhabitants here
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

        openLaptop = new Collectable(84, 254, "assets" + File.separator + "laptopOpen.png", "laptop");
        openLaptop.setInventoryPath("assets" + File.separator + "laptopClosed.png");

        door.addValidInitCollectable(primaryCursor);
        door.addValidInitCollectable(primaryCursor);

        add(roomInhabitants);
    }

    public void doorInteraction() {
        if (door.getState() == 1) {
            if (poisonedRatInteraction && roomScare == false) {
                super.setBackgroundImage(
                    "assets" + File.separator + "GreenChairsRoom" + File.separator + "shadowGreenChairsRoomBG.png");
                roomScare = true;
            } else if (poisonedRatInteraction && roomScare) {
                super.setBackgroundImage(
                    "assets" + File.separator + "GreenChairsRoom" + File.separator + "slimeGreenChairsRoomBG.png");
            }
            changeRoom = true;
            changeRoom();
        }
    }

    @Override
    public void updateRoom() {
        if (displayOverlay.getWidth() != 0) {
                displayOverlay.removeAll();
        }
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
                leaveAfterAnnouncement();
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

    private void changeRoom() {
        if (changeRoom) {
            hallway.resetActiveRoom();
            setActiveRoom(hallway.getActiveRoom());
            door.changeState(0);
        }
    }

    public void jumpscare(){
        displayOverlay.add(new Image("assets" + File.separator + "piper" + File.separator + "hands.png"));
        scareDelay();
    }

    public void spawnOpenLaptop() {
        this.roomInhabitants.add(openLaptop);
    }

    private void leaveAfterAnnouncement(){
        if (!leaveAfterAnnouncement) {
            displayOverlay.add(new Image("assets" + File.separator + "overlays" + File.separator + "leaveAfterAnnouncement.png"));
            leaveAfterAnnouncement = true;
        }
    }
}
