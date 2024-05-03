package analoghorror.rooms;

import java.io.File;
import java.util.Arrays;

import analoghorror.Inventory;
import analoghorror.Sound;
import analoghorror.inhabitants.*;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;

public class HallwayRoom extends Room{
    boolean piperDeath;
    boolean playerDeath;
    boolean startDisplay;
    boolean introDisplay;
    boolean youWin;
    boolean hallucination;
    boolean hasTextBeenShown;

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

    public HallwayRoom(Collectable hand, String backgroundImage, Inventory inventory, GraphicsGroup displayOverlay, Sound primarySound){
        super(backgroundImage, displayOverlay);
        this.inventory = inventory;
        this.primarySound = primarySound;
        primaryCursor = hand;
        startDisplay = false;
        introDisplay = false;
        piperDeath = false;
        playerDeath = false;
        youWin = false;
        hallucination = false;
        changeRoom = false;
        hasTextBeenShown = false;

        addRoomInhabitants();
    }

    public void addRoomInhabitants(){
        greenChairsRoomDoor = new Item(581, -5, "assets" + File.separator + "HallwayRoom" + File.separator + "greenChairsRoomDoorClosed.png", false, 2);
        greenChairsRoomDoor.setStatePaths(Arrays.asList("assets" + File.separator + "HallwayRoom" + File.separator + "greenChairsRoomDoorClosed.png", 
        "assets" + File.separator + "HallwayRoom" + File.separator + "greenChairsRoomDoorOpen.png"));
        this.roomInhabitants.add(greenChairsRoomDoor);

        lectureHallRoomDoor = new Item(450, 140, "assets" + File.separator + "HallwayRoom" + File.separator + "lectureHallRoomDoorClosed.png", false, 2);
        lectureHallRoomDoor.setStatePaths(Arrays.asList("assets" + File.separator + "HallwayRoom" + File.separator + "lectureHallRoomDoorClosed.png", 
        "assets" + File.separator + "HallwayRoom" + File.separator + "lectureHallRoomDoorOpen.png"));
        roomInhabitants.add(lectureHallRoomDoor);

        windowedClassRoomDoor = new Item(275, 92, "assets" + File.separator + "HallwayRoom" + File.separator + "windowedClassRoomDoorClosed.png", false, 2);
        windowedClassRoomDoor.setStatePaths(Arrays.asList("assets" + File.separator + "HallwayRoom" + File.separator + "windowedClassRoomDoorClosed.png", 
        "assets" + File.separator + "HallwayRoom" + File.separator + "windowedClassRoomDoorOpen.png"));
        roomInhabitants.add(windowedClassRoomDoor);

        
        greenChairsRoomDoor.addValidSubCollectable(primaryCursor);
        lectureHallRoomDoor.addValidSubCollectable(primaryCursor);
        windowedClassRoomDoor.addValidSubCollectable(primaryCursor);

        piper = new Piper(0, 0, this);

        card = new Collectable(458, 325, "assets" + File.separator + "cardOnFloor.png", "card02");
        card.setInventoryPath("assets" + File.separator + "studentCard.png");
        roomInhabitants.add(card);      

        greenChairsRoomDoor.addValidInitCollectable(card);
        lectureHallRoomDoor.addValidInitCollectable(card);
        windowedClassRoomDoor.addValidInitCollectable(card);

        add(roomInhabitants);
    }

    /**
     * Change room depending on what door opens.
     */
    public void doorInteraction(){
        if (greenChairsRoomDoor.getState() == 1 || lectureHallRoomDoor.getState() == 1 || windowedClassRoomDoor.getState() == 1) {
            changeRoom = true;
            changeRoom();
        }
    }
    
    @Override
    public void updateRoom() {
        clearDisplayOverlay();
        introDisplay();
        startDisplay();
        validateRat();
        piperDeath();
        playerDeath();
        doorInteraction();
    }

    /**
     * Set ActiveRoom depending on what door you click and resets said door.
     */
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

    /**
     * Adds and starts Piper.
     */
    public void finalScare(){
        this.roomInhabitants.add(piper);
        piper.piperStart();
    }

    /**
     * Displays the starting overlay at the right time.
     */
    private void startDisplay(){
        if (!startDisplay) {
            displayOverlay.add(new Image("assets" + File.separator + "overlays" + File.separator + "start.png"));
            startDisplay = true;
        }
    }

    /**
     * Displays the intro overlay at the right time.
     */
    private void introDisplay(){
        if (startDisplay && !introDisplay) {
            displayOverlay.add(new Image("assets" + File.separator + "overlays" + File.separator + "intro.png"));
            introDisplay = true;
        }
    }

    /**
     * Displays the victory overlay at the right time.
     */
    private void youWin(){
        if (!youWin) {
            displayOverlay.add(new Image("assets" + File.separator + "overlays" + File.separator + "youWin.png"));
            youWin = true;
        }
    }

    /**
     * Displays a hallucinated figure at the end of the hallway after you collect the poison in the lecture room.
     */
    public void hallucination(){
        if (!hallucination) {
            displayOverlay.add(new Image("assets" + File.separator + "overlays" + File.separator + "hallucination.png"));
            hallucination = true;
        }
    }

    @Override
    protected void clearDisplayOverlay() {
        if (displayOverlay.getWidth() != 0 && !youWin) {
            displayOverlay.removeAll();
        }
        if (displayOverlay.getWidth() != 0 && youWin) {
            winDelay();
        }
    }

    /**
     * Lets Piper die from rat homework.
     */
    private void validateRat(){
        if (inventory.getCollectableWithID("rat01") != null){
            piper.addValidInitCollectable(inventory.getCollectableWithID("rat01"));
            piper.addValidSubCollectable(inventory.getCollectableWithID("rat01"));
        }
    }

    /**
     * If player hasn't died and Piper was fed rat, you win!
     */
    private void piperDeath(){
        if (piper.getState() == 0 && piperDeath == false && playerDeath == false) {
            piperDeath = true;
            piper.piperEnd();
            youWin();
        }
    }

    /**
     * If Piper reaches player, game ends.
     */
    private void playerDeath(){
        if (piper.getState() == 4) {
            playerDeath = true;
            scareDelay(); 
        }
    }
}
