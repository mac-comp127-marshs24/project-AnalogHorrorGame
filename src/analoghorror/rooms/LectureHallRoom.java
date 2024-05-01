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

    //TODO: Replace with actual items
    Item box;
    Collectable key;
    Item door;
    Collectable card;
    Item sonic;
    Item puzzle;

    public LectureHallRoom(HallwayRoom hallway, Collectable hand, String backgroundImage, Inventory inventory) {
        super(backgroundImage);
        this.hallway = hallway;
        this.inventory = inventory;
        primaryCursor =  hand;
        changeRoom = false;

        addRoomInhabitants();
    }

    @Override
    public void addRoomInhabitants() {
        box = new Item(255, 286, "assets" + File.separator + "chestClosed.png", false, 2);
        box.setStatePaths(Arrays.asList("assets" + File.separator + "chestClosed.png", "assets" + File.separator + "chestOpen.png"));
        roomInhabitants.add(box);  // Add to "Room" (GraphicsGroup for now)

        key = new Collectable(60, 205, "assets" + File.separator + "silverKey.png", "key01");
        roomInhabitants.add(key);
        box.addValidInitCollectable(key);  // Add the Collectable to the internal validCollectable Sets for the Item

        door = new Item(385, 120, "assets" + File.separator + "doorClosed.png", false, 2);
        door.setStatePaths(Arrays.asList("assets" + File.separator + "doorClosed.png", "assets" + File.separator + "doorOpen.png"));
        roomInhabitants.add(door);

        sonic = new Item(778, 70, "assets" + File.separator + "sonicForward.png", true, 4);
        sonic.setStatePaths(Arrays.asList("assets" + File.separator + "sonicForward.png", "assets" + File.separator + "sonicDown.png",
        "assets" + File.separator + "sonicBack.png", "assets" + File.separator + "sonicUp.png"));
        roomInhabitants.add(sonic);
        sonic.addValidInitCollectable(primaryCursor);
        sonic.addValidSubCollectable(primaryCursor);

        //TODO: Continue implementing puzzle
        puzzle = new Item(700, 125, "assets" + File.separator + "puzzle" + File.separator + "puzzleBoard.png", false, 10);
        puzzle.setStatePaths(Arrays.asList(
        "assets" + File.separator + "puzzle" + File.separator + "puzzleBoard.png",

        "assets" + File.separator + "puzzle" + File.separator + "botRightPuzzleBoard.png",
        "assets" + File.separator + "puzzle" + File.separator + "botMidPuzzleBoard.png",
        "assets" + File.separator + "puzzle" + File.separator + "botLeftPuzzleBoard.png", 
        

        "assets" + File.separator + "puzzle" + File.separator + "midRightPuzzleBoard.png",
        "assets" + File.separator + "puzzle" + File.separator + "midMidPuzzleBoard.png",
        "assets" + File.separator + "puzzle" + File.separator + "midLeftPuzzleBoard.png",
        

        "assets" + File.separator + "puzzle" + File.separator + "topRightPuzzleBoard.png",
        "assets" + File.separator + "puzzle" + File.separator + "topMidPuzzleBoard.png",
        "assets" + File.separator + "puzzle" + File.separator + "topLeftPuzzleBoard.png"));
        puzzle.addValidInitCollectable(primaryCursor);
        puzzle.addValidSubCollectable(primaryCursor);
        roomInhabitants.add(puzzle);

        card = new Collectable(528, 325, "assets" + File.separator + "studentCard.png", "card01");
        roomInhabitants.add(card);
        door.addValidInitCollectable(card);
        
        door.addValidInitCollectable(key);

        box.addValidSubCollectable(primaryCursor);
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
        puzzleMinigame();
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
