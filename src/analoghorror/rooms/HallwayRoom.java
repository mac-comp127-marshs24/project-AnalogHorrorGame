package analoghorror.rooms;

import java.io.File;
import java.util.Arrays;

import analoghorror.Inventory;
import analoghorror.Sound;
import analoghorror.inhabitants.*;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;

public class HallwayRoom extends Room{
    // private static boolean changeRoom = false;
    boolean piperDeath;
    boolean playerDeath;
    boolean startDisplay;
    boolean introDisplay;
    boolean youWin;
    boolean hallucination;

    Collectable primaryCursor;
    GreenChairsRoom greenChairsRoom;
    LectureHallRoom lectureHallRoom;
    WindowedClassRoom windowedClassRoom;
    boolean jumpscarePresent;
    Inventory inventory;

    Item greenChairsRoomDoor;
    Item lectureHallRoomDoor;
    Item windowedClassRoomDoor;

    Piper piper;
    Collectable card;

    boolean hasTextBeenShown;

    
    public HallwayRoom(Collectable hand, String backgroundImage, Inventory inventory, GraphicsGroup displayOverlay, Sound primarySound){
        super(backgroundImage, displayOverlay);
        startDisplay = false;
        introDisplay = false;
        piperDeath = false;
        playerDeath = false;
        youWin = false;
        hallucination = false;
        primaryCursor = hand;
        changeRoom = false;
        hasTextBeenShown = false;
        this.inventory = inventory;
        this.primarySound = primarySound;

        addRoomInhabitants();
    }

    public void addRoomInhabitants(){
        /*Doors */
        greenChairsRoomDoor = new Item(581, -5, "assets" + File.separator + "HallwayRoom" + File.separator + "greenChairsRoomDoorClosed.png", false, 2);
        greenChairsRoomDoor.setStatePaths(Arrays.asList("assets" + File.separator + "HallwayRoom" + File.separator + "greenChairsRoomDoorClosed.png", 
        "assets" + File.separator + "HallwayRoom" + File.separator + "greenChairsRoomDoorOpen.png"));
        this.roomInhabitants.add(greenChairsRoomDoor);

        lectureHallRoomDoor = new Item(450, 140, "assets" + File.separator + "HallwayRoom" + File.separator + "lectureHallRoomDoorClosed.png", false, 2);
        lectureHallRoomDoor.setStatePaths(Arrays.asList("assets" + File.separator + "HallwayRoom" + File.separator + "lectureHallRoomDoorClosed.png", 
        "assets" + File.separator + "HallwayRoom" + File.separator + "lectureHallRoomDoorOpen.png"));
        this.roomInhabitants.add(lectureHallRoomDoor);

        windowedClassRoomDoor = new Item(275, 92, "assets" + File.separator + "HallwayRoom" + File.separator + "windowedClassRoomDoorClosed.png", false, 2);
        windowedClassRoomDoor.setStatePaths(Arrays.asList("assets" + File.separator + "HallwayRoom" + File.separator + "windowedClassRoomDoorClosed.png", 
        "assets" + File.separator + "HallwayRoom" + File.separator + "windowedClassRoomDoorOpen.png"));
        this.roomInhabitants.add(windowedClassRoomDoor);

        card = new Collectable(458, 325, "assets" + File.separator + "cardOnFloor.png", "card02");
        card.setInventoryPath("assets" + File.separator + "studentCard.png");
        this.roomInhabitants.add(card);

        piper = new Piper(0, 0, this);

        /*Door key interaction */
        greenChairsRoomDoor.addValidInitCollectable(card);

        lectureHallRoomDoor.addValidInitCollectable(card);

        windowedClassRoomDoor.addValidInitCollectable(card);

        greenChairsRoomDoor.addValidSubCollectable(primaryCursor);
        lectureHallRoomDoor.addValidSubCollectable(primaryCursor);
        windowedClassRoomDoor.addValidSubCollectable(primaryCursor);

        add(roomInhabitants);
    }

    public void doorInteraction(){
        if (greenChairsRoomDoor.getState() == 1 || lectureHallRoomDoor.getState() == 1 || windowedClassRoomDoor.getState() == 1) {
            changeRoom = true;
            changeRoom();
        }
    }
    
    @Override
    public void updateRoom() {
        // ambientSound();
        if (displayOverlay.getWidth() != 0 && !youWin) {
            displayOverlay.removeAll();
        }
        if (displayOverlay.getWidth() != 0 && youWin) {
            winDelay();
        }
        introDisplay();
        startDisplay();
        doorInteraction();
        if (inventory.getCollectableWithID("rat01") != null){ //tried adding outside of update room, still crashes game
            piper.addValidInitCollectable(inventory.getCollectableWithID("rat01"));
            piper.addValidSubCollectable(inventory.getCollectableWithID("rat01"));
        }
        if (piper.getState() == 0 && piperDeath == false && playerDeath == false) {
            piperDeath = true;
            piper.piperEnd();
            youWin();
            // killed
        }
        if (piper.getState() == 4) {  // or 5
            playerDeath = true;
            // monster wins
            scareDelay();  // closes game
        }
    }

    private void changeRoom(){
        if(changeRoom && greenChairsRoomDoor.getState() == 1){ 
            greenChairsRoom.resetActiveRoom();
            setActiveRoom(greenChairsRoom.getActiveRoom());
            greenChairsRoomDoor.changeState(0);
            greenChairsRoom.updateRoom();
        }

        else if(changeRoom && lectureHallRoomDoor.getState() == 1){
            lectureHallRoom.resetActiveRoom();
            setActiveRoom(lectureHallRoom.getActiveRoom());
            lectureHallRoomDoor.changeState(0);
            lectureHallRoom.updateRoom();
        }

        else if(changeRoom && windowedClassRoomDoor.getState() == 1){
            windowedClassRoom.resetActiveRoom();
            setActiveRoom(windowedClassRoom.getActiveRoom());
            windowedClassRoomDoor.changeState(0);
            windowedClassRoom.updateRoom();
        }
    }
    
    public void addChairClassroom(GreenChairsRoom chairClassroom){
        this.greenChairsRoom = chairClassroom;
    }

    public void addWindowedClassroom(WindowedClassRoom windowedClassRoom){
        this.windowedClassRoom = windowedClassRoom;
    }

    public void addLectureHall(LectureHallRoom lectureHallRoom){
        this.lectureHallRoom = lectureHallRoom;
    }

    public void finalScare(){
        // jumpscareSound.playSound("res" + File.separator + "assets"+ File.separator +"Audio"+ File.separator + "franticHallway.wav");
        this.roomInhabitants.add(piper);
        piper.piperStart();
    }

    private void startDisplay(){
        if (!startDisplay) {
            displayOverlay.add(new Image("assets" + File.separator + "overlays" + File.separator + "start.png"));
            startDisplay = true;
        }
    }

    private void introDisplay(){
        if (startDisplay && !introDisplay) {
            displayOverlay.add(new Image("assets" + File.separator + "overlays" + File.separator + "intro.png"));
            introDisplay = true;
        }
    }

    private void youWin(){
        if (!youWin) {
            displayOverlay.add(new Image("assets" + File.separator + "overlays" + File.separator + "youWin.png"));
            youWin = true;
        }
    }

    public void hallucination(){
        if (!hallucination) {
            displayOverlay.add(new Image("assets" + File.separator + "overlays" + File.separator + "hallucination.png"));
            hallucination = true;
        }
    }
}
