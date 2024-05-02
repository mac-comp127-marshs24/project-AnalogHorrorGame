package analoghorror.rooms;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;

import java.io.File;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import analoghorror.Inventory;
import analoghorror.inhabitants.*;

public class LectureHallRoom extends Room {
    Collectable primaryCursor;
    HallwayRoom hallway;
    Inventory inventory;
    boolean addedPoison;
    Item door;
    Puzzle puzzle;
    Collectable poison;


    public LectureHallRoom(HallwayRoom hallway, Collectable hand, String backgroundImage, Inventory inventory, GraphicsGroup displayOverlay) {
        super(backgroundImage, displayOverlay);
        this.hallway = hallway;
        this.inventory = inventory;
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


        puzzle = new Puzzle(700, 125, false, 10, this);
        puzzle.addValidInitCollectable(primaryCursor);
        puzzle.addValidSubCollectable(primaryCursor);
        roomInhabitants.add(puzzle);

        door.addValidInitCollectable(primaryCursor);
        door.addValidSubCollectable(primaryCursor);

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
        else if (puzzle.getFailState()) {
            jumpscare();
        }
        if (puzzle.getSolved() && addedPoison == false) {
            this.roomInhabitants.add(poison);
           addedPoison = true;
        }
    }

    private void changeRoom(){
        if(changeRoom){
            hallway.resetActiveRoom();
            setActiveRoom(hallway.getActiveRoom());
            door.changeState(0);
        }
    }

    public void jumpscare(){
        displayOverlay.add(new Image("assets" + File.separator + "nancy.jpg"));
        scareDelay();
    }
}
