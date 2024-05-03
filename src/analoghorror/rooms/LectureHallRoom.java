package analoghorror.rooms;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;

import java.io.File;
import java.util.Arrays;

import analoghorror.Inventory;
import analoghorror.Sound;
import analoghorror.inhabitants.*;

public class LectureHallRoom extends Room {
    boolean addedPoison;
    boolean puzzleFirstSight;
    boolean puzzleComplete;
    
    Collectable primaryCursor;
    HallwayRoom hallway;
    Inventory inventory;

    Item door;
    Item clunk;

    Puzzle puzzle;
    Collectable poison;
    Collectable openLaptop;
    Collectable closedLaptop;

    public LectureHallRoom(HallwayRoom hallway, Collectable hand, String backgroundImage, Inventory inventory, GraphicsGroup displayOverlay, Sound primarySound) {
        super(backgroundImage, displayOverlay);
        puzzleFirstSight = false;
        puzzleComplete= false;
        this.hallway = hallway;
        this.inventory = inventory;
        this.primarySound =  primarySound;
        primaryCursor =  hand;
        changeRoom = false;
        addedPoison = false;

        addRoomInhabitants();
    }

    @Override
    public void addRoomInhabitants() {
        door = new Item(45, 105, "assets" + File.separator + "LectureHallRoom" + File.separator + "lectureHallRoomClosed.png", false, 2);
        door.setStatePaths(Arrays.asList("assets" + File.separator + "LectureHallRoom" + File.separator + "lectureHallRoomClosed.png", 
        "assets" + File.separator + "LectureHallRoom" + File.separator + "lectureHallRoomOpen.png"));
        roomInhabitants.add(door);
        poison = new Collectable(745, 165, "assets" + File.separator + "poisonSmall.png", "lecturePoison");
        poison.setInventoryPath("assets" + File.separator + "poison.png");


        puzzle = new Puzzle(700, 125, this);
        puzzle.addValidInitCollectable(primaryCursor);
        puzzle.addValidSubCollectable(primaryCursor);
        roomInhabitants.add(puzzle);

        clunk = new Item(0, 0,
            "assets" + File.separator + "LectureHallRoom" + File.separator + "clunk.png", true, 2);
        clunk
            .setStatePaths(Arrays.asList("assets" + File.separator + "LectureHallRoom" + File.separator + "clunk.png",
                "assets" + File.separator + "LectureHallRoom" + File.separator + "clunk.png"));


        door.addValidInitCollectable(primaryCursor);
        door.addValidSubCollectable(primaryCursor);

        openLaptop = new Collectable(610, 290, "assets" + File.separator + "laptopOpen.png", "laptop");
        openLaptop.setInventoryPath("assets" + File.separator + "laptopClosed.png");

        closedLaptop = new Collectable(785, 317, "assets" + File.separator + "laptopClosed.png", "laptop");
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
        clearDisplayOverlay();
        puzzleFirstSight();
        puzzleComplete();
        if (puzzle.getSolved() && addedPoison == false) {
            this.roomInhabitants.add(poison);
           addedPoison = true;
        }
        if (clunk.getState() == 1) {
            clunk.changeState(0);
            this.roomInhabitants.remove(clunk);
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

    public void clunk(){
        this.roomInhabitants.add(clunk);
        clunk.addValidInitCollectable(primaryCursor);
        clunk.addValidSubCollectable(primaryCursor);
    }
    
    public void spawnOpenLaptop(){
        this.roomInhabitants.add(openLaptop);
    }

    public void spawnClosedLaptop(){
        this.roomInhabitants.add(closedLaptop);
    }

    private void puzzleFirstSight(){
        if (!puzzleFirstSight) {
            displayOverlay.add(new Image("assets" + File.separator + "overlays" + File.separator + "puzzleFirstSight.png"));
            puzzleFirstSight = true;
        }
    }

    private void puzzleComplete(){
        if (puzzle.getSolved() && !puzzleComplete && poison.getInInventory()) {
            displayOverlay.add(new Image("assets" + File.separator + "overlays" + File.separator + "puzzleComplete.png"));
            puzzleComplete = true;
        }
    }

    @Override
    protected void clearDisplayOverlay() {
        if (displayOverlay.getWidth() != 0) {
            displayOverlay.removeAll();
        }
        else if (puzzle.getFailState()) {  // Maybe make all jumpscare()s more like ↓
            jumpscare();
        }
    }
}
