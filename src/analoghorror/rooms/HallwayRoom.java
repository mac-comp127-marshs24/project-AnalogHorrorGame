package analoghorror.rooms;

import java.io.File;
import java.util.Arrays;
import analoghorror.inhabitants.*;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;

public class HallwayRoom extends Room{
    // private static boolean changeRoom = false;
    Collectable primaryCursor;
    GreenChairsRoom chairClassroom;
    LectureHallRoom lectureHallRoom;
    WindowedClassRoom windowedClassRoom;
    Item box;
    Collectable key;
    boolean jumpscarePresent;

    Item doorA;
    Item doorB;
    Item doorC;

    Collectable card;
    Item sonic;

    boolean hasTextBeenShown;
    

    public HallwayRoom(Collectable hand, String backgroundImage, GraphicsGroup displayOverlay){
        super(backgroundImage, displayOverlay);
        primaryCursor = hand;
        changeRoom = false;
        hasTextBeenShown = false;
        jumpscarePresent = false;
         //SOUND ADDED TO GAME INSANE!!!!
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
        ambientSound();
        doorInteraction();
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

    //TODO: work on stop sound method
    private void ambientSound(){
        if (jumpscarePresent ==  false) {
            playSound("res"+ File.separator +"assets"+ File.separator +"Audio"+ File.separator + "ambientBG.wav");
        }
        else{
            stopSound();
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
        jumpscarePresent = true;
        displayOverlay.add(new Image("assets" + File.separator + "nancy.jpg"));
        scareDelay();
    }

    public void finalScare(){
        System.out.println("FINAL MONSTER");
    }

}
