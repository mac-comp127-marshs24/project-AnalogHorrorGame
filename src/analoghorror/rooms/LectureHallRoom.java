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
    //TODO: Replace with actual items
    Item door;
    Puzzle puzzle;
    Collectable poison;


    public LectureHallRoom(HallwayRoom hallway, Collectable hand, String backgroundImage, Inventory inventory) {
        super(backgroundImage);
        this.hallway = hallway;
        this.inventory = inventory;
        primaryCursor =  hand;
        changeRoom = false;
        addedPoison = false;

        addRoomInhabitants();
    }

    @Override
    public void addRoomInhabitants() {
        door = new Item(385, 120, "assets" + File.separator + "doorClosed.png", false, 2);
        door.setStatePaths(Arrays.asList("assets" + File.separator + "doorClosed.png", "assets" + File.separator + "doorOpen.png"));
        roomInhabitants.add(door);
        poison = new Collectable(785, 300, "assets" + File.separator + "poison.png", "lecturePoison");


        //TODO: Continue implementing puzzle
        puzzle = new Puzzle(700, 125, false, 10);
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
    public void updateRoom(GraphicsGroup displayText) {
        doorInteraction();
        if (puzzle.getState() == 9 && addedPoison == false) {
            this.roomInhabitants.add(poison);
           addedPoison = true;
        }
        //puzzleMinigame(); //jumpscare (really cool)
    }

    private void changeRoom(){
        if(changeRoom){ //change changeRoom to a specific click event?
            //TODO: setActiveRoom should change the active room to the inputted new room, but ensure that is reflected on the canvas
            hallway.resetActiveRoom();
            setActiveRoom(hallway.getActiveRoom());
            door.changeState(0);
        }
    }

    private void puzzleMinigame(){
        long delay = 3000;
        Timer jumpscareTimer = new Timer();
        TimerTask jumpscareTask = new TimerTask() {
            @Override
            public void run() {
                if(puzzle.getState() != 10){
                    roomInhabitants.add(new Image("assets" + File.separator + "nancy.jpg"));
                }
            }
        };

        if(puzzle.getState() != 0){
            jumpscareTimer.schedule(jumpscareTask, delay);
        }
    }

}
