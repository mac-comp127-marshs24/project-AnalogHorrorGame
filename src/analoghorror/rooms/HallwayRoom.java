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
    Collectable primaryCursor;
    GreenChairsRoom chairClassroom;
    LectureHallRoom lectureHallRoom;
    WindowedClassRoom windowedClassRoom;
    Item box;
    Collectable key;
    Inventory inventory;

    Item doorA;
    Item doorB;
    Item doorC;

    Piper piper;

    Collectable card;
    Item sonic;
    
    Sound jumpscareSound;

    boolean hasTextBeenShown;
    

    public HallwayRoom(Collectable hand, String backgroundImage, Inventory inventory, GraphicsGroup displayOverlay){
        super(backgroundImage, displayOverlay);
        piperDeath = false;
        primaryCursor = hand;
        changeRoom = false;
        hasTextBeenShown = false;
        this.inventory = inventory;

        /*Sound */
        ambientSound = new Sound();
        jumpscareSound = new Sound();

        addRoomInhabitants();
    }

    public void addRoomInhabitants(){
        /*Doors */
        doorA = new Item(660, -30, "assets" + File.separator + "Door1Closed.png", false, 2);
        doorA.setStatePaths(Arrays.asList("assets" + File.separator + "Door1Closed.png", "assets" + File.separator + "Door1.png"));
        this.roomInhabitants.add(doorA);

        doorB = new Item(528, 130, "assets" + File.separator + "Door2Closed.png", false, 2);
        doorB.setStatePaths(Arrays.asList("assets" + File.separator + "Door2Closed.png", "assets" + File.separator + "Door2.png"));
        this.roomInhabitants.add(doorB);

        doorC = new Item(288, 85, "assets" + File.separator + "Door3Closed.png", false, 2);
        doorC.setStatePaths(Arrays.asList("assets" + File.separator + "Door3Closed.png", "assets" + File.separator + "Door3.png"));
        this.roomInhabitants.add(doorC);

        card = new Collectable(458, 325, "assets" + File.separator + "cardOnFloor.png", "card02");
        card.setInventoryPath("assets" + File.separator + "studentCard.png");
        this.roomInhabitants.add(card);

        piper = new Piper(20, 20, this);

        /*Door key interaction */
        doorA.addValidInitCollectable(card);

        doorB.addValidInitCollectable(card);

        doorC.addValidInitCollectable(card);

        doorA.addValidSubCollectable(primaryCursor);
        doorB.addValidSubCollectable(primaryCursor);
        doorC.addValidSubCollectable(primaryCursor);

        add(roomInhabitants);
    }

    public void doorInteraction(){
        if (doorA.getState() == 1 || doorB.getState() == 1 || doorC.getState() == 1) {
            changeRoom = true;
            changeRoom();
        }
    }
    
    @Override
    public void updateRoom() {
        playAmbientSound();
        doorInteraction();
        if (inventory.getCollectableWithID("rat01") != null){ //tried adding outside of update room, still crashes game
            piper.addValidInitCollectable(inventory.getCollectableWithID("rat01"));
            piper.addValidSubCollectable(inventory.getCollectableWithID("rat01"));

        }
        if (piper.getState() == 0 && piperDeath == false) {
            ambientSound.stopSound();
            System.out.println("They are dead");
            piperDeath = true;
            piper.piperEnd();
            // killed
        }
        if (piper.getState() == 4) {  // or 5
            ambientSound.stopSound();
            System.out.println("You are dead");
            // monster wins
            scareDelay();
        }
    }

    private void changeRoom(){
        if(changeRoom && doorA.getState() == 1){ 
            chairClassroom.resetActiveRoom();
            setActiveRoom(chairClassroom.getActiveRoom());
            doorA.changeState(0);
        }

        else if(changeRoom && doorB.getState() == 1){
            lectureHallRoom.resetActiveRoom();
            setActiveRoom(lectureHallRoom.getActiveRoom());
            doorB.changeState(0);
        }

        else if(changeRoom && doorC.getState() == 1){
            windowedClassRoom.resetActiveRoom();
            setActiveRoom(windowedClassRoom.getActiveRoom());
            doorC.changeState(0);
        }
    }
    
    public void addChairClassroom(GreenChairsRoom chairClassroom){
        this.chairClassroom = chairClassroom;
    }

    public void addWindowedClassroom(WindowedClassRoom windowedClassRoom){
        this.windowedClassRoom = windowedClassRoom;
    }

    public void addLectureHall(LectureHallRoom lectureHallRoom){
        this.lectureHallRoom = lectureHallRoom;
    }

    public void jumpscare(){   
        this.jumpscarePresent = true;
        jumpscareSound.playSound("res"+ File.separator +"assets"+ File.separator +"Audio"+ File.separator + "jumpscareBagpipe.wav");
        displayOverlay.add(new Image("assets" + File.separator + "nancy.jpg"));
        scareDelay();
    }

    public void finalScare(){
        jumpscareSound.playSound("res"+ File.separator +"assets"+ File.separator +"Audio"+ File.separator + "franticHallway.wav");
        this.roomInhabitants.add(piper);
        piper.piperStart();
        System.out.println("FINAL MONSTER");
    }

}
