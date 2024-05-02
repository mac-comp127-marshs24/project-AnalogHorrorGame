package analoghorror.rooms;

import java.io.File;
import java.util.Arrays;

import analoghorror.Inventory;
import analoghorror.inhabitants.*;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;

public class HallwayRoom extends Room{
    // private static boolean changeRoom = false;
    boolean piperDeath;
    Collectable primaryCursor;
    GreenChairsRoom greenChairsRoom;
    LectureHallRoom lectureHallRoom;
    WindowedClassRoom windowedClassRoom;
    Item box;
    Collectable key;
    boolean jumpscarePresent;
    Inventory inventory;

    Item greenChairsRoomDoor;
    Item lectureHallRoomDoor;
    Item windowedClassRoomDoor;

    Piper piper;

    Collectable card;
    Item sonic;

    boolean hasTextBeenShown;
    

    public HallwayRoom(Collectable hand, String backgroundImage, Inventory inventory, GraphicsGroup displayOverlay){
        super(backgroundImage, displayOverlay);
        piperDeath = false;
        primaryCursor = hand;
        changeRoom = false;
        hasTextBeenShown = false;
        jumpscarePresent = false;
        this.inventory = inventory;
         //SOUND ADDED TO GAME INSANE!!!!
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
        ambientSound();
        doorInteraction();
        if (inventory.getCollectableWithID("rat01") != null){ //tried adding outside of update room, still crashes game
            piper.addValidInitCollectable(inventory.getCollectableWithID("rat01"));
            piper.addValidSubCollectable(inventory.getCollectableWithID("rat01"));

        }
        if (piper.getState() == 0 && piperDeath == false) {
            System.out.println("They are dead");
            piperDeath = true;
            piper.piperEnd();
            // killed
        }
        if (piper.getState() == 4) {  // or 5
            System.out.println("You are dead");
            // monster wins
            scareDelay();
        }
    }

    private void changeRoom(){
        if(changeRoom && greenChairsRoomDoor.getState() == 1){ 
            greenChairsRoom.resetActiveRoom();
            setActiveRoom(greenChairsRoom.getActiveRoom());
            greenChairsRoomDoor.changeState(0);
        }

        else if(changeRoom && lectureHallRoomDoor.getState() == 1){
            lectureHallRoom.resetActiveRoom();
            setActiveRoom(lectureHallRoom.getActiveRoom());
            lectureHallRoomDoor.changeState(0);
        }

        else if(changeRoom && windowedClassRoomDoor.getState() == 1){
            windowedClassRoom.resetActiveRoom();
            setActiveRoom(windowedClassRoom.getActiveRoom());
            windowedClassRoomDoor.changeState(0);
        }
    }

    private void ambientSound(){
        if (jumpscarePresent ==  false) {
            playSound("res"+ File.separator +"assets"+ File.separator +"Audio"+ File.separator + "ambientBG.wav");
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

    public void jumpscare(){
        displayOverlay.add(new Image("assets" + File.separator + "piper" + File.separator + "hands.png"));
        scareDelay();
    }

    public void finalScare(){
        this.roomInhabitants.add(piper);
        piper.piperStart();
        System.out.println("FINAL MONSTER");
    }

}
