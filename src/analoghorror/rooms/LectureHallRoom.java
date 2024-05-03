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
        this.hallway = hallway;
        this.inventory = inventory;
        this.primarySound =  primarySound;
        primaryCursor =  hand;
        puzzleFirstSight = false;
        puzzleComplete= false;
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

        puzzle = new Puzzle(700, 125, this);
        puzzle.addValidInitCollectable(primaryCursor);
        puzzle.addValidSubCollectable(primaryCursor);
        roomInhabitants.add(puzzle);
        
        clunk = new Item(0, 0, "assets" + File.separator + "LectureHallRoom" + File.separator + "clunk.png", true, 2);
        clunk.setStatePaths(Arrays.asList("assets" + File.separator + "LectureHallRoom" + File.separator + "clunk.png",
        "assets" + File.separator + "LectureHallRoom" + File.separator + "clunk.png"));

        door.addValidInitCollectable(primaryCursor);
        door.addValidSubCollectable(primaryCursor);

        poison = new Collectable(745, 165, "assets" + File.separator + "poisonSmall.png", "lecturePoison");
        poison.setInventoryPath("assets" + File.separator + "poison.png");

        openLaptop = new Collectable(610, 290, "assets" + File.separator + "laptopOpen.png", "laptop");
        openLaptop.setInventoryPath("assets" + File.separator + "laptopClosed.png");

        closedLaptop = new Collectable(785, 317, "assets" + File.separator + "laptopClosed.png", "laptop");
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
        puzzleFirstSight();
        puzzleComplete();
        spawnPoison();
        clunkRemoval();
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

    /**
     * Add "Clunk! sound" Item and validate primaryCursor.
     */
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

    /**
     * Displays an overlay if you enter the room for the first time mentioning the puzzle.
     */
    private void puzzleFirstSight(){
        if (!puzzleFirstSight) {
            displayOverlay.add(new Image("assets" + File.separator + "overlays" + File.separator + "puzzleFirstSight.png"));
            puzzleFirstSight = true;
        }
    }

    /**
     * Displays an overlay once you collect the poison mentioning the puzzle's compartment.
     */
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

    private void spawnPoison(){
        if (puzzle.getSolved() && addedPoison == false) {
            this.roomInhabitants.add(poison);
           addedPoison = true;
        }
    }

    /**
     * Removes "Clunk! sound" Item.
     */
    private void clunkRemoval(){
        if (clunk.getState() == 1) {
            clunk.changeState(0);
            this.roomInhabitants.remove(clunk);
        }
    }
}
