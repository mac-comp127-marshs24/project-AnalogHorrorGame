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

    Item doorA;
    Item doorB;
    Item doorC;

    Collectable card;
    Item sonic;

    boolean hasTextBeenShown;
    

    public HallwayRoom(Collectable hand, String backgroundImage){
        super(backgroundImage);
        primaryCursor = hand;
        changeRoom = false;
        hasTextBeenShown = false;
        addRoomInhabitants();
    }

    public void addRoomInhabitants(){    
        // box = new Item(255, 286, "assets" + File.separator + "chestClosed.png", false, 2);
        // box.setStatePaths(Arrays.asList("assets" + File.separator + "chestClosed.png", "assets" + File.separator + "chestOpen.png"));
        // this.roomInhabitants.add(box);  // Add to this.roomInhabitants

        // key = new Collectable(60, 205, "assets" + File.separator + "silverKey.png", "key02");
        // this.roomInhabitants.add(key);
        // box.addValidInitCollectable(key);  // Add the Collectable to the internal validCollectable Sets for the Item

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

        // sonic = new Item(778, 70, "assets" + File.separator + "sonicForward.png", true, 4);
        // sonic.setStatePaths(Arrays.asList("assets" + File.separator + "sonicForward.png", "assets" + File.separator + "sonicDown.png",
        // "assets" + File.separator + "sonicBack.png", "assets" + File.separator + "sonicUp.png"));
        // this.roomInhabitants.add(sonic);
        // sonic.addValidInitCollectable(primaryCursor);
        // sonic.addValidSubCollectable(primaryCursor);

        card = new Collectable(528, 325, "assets" + File.separator + "studentCard.png", "card02");
        this.roomInhabitants.add(card);

        // box.addValidSubCollectable(primaryCursor);

        /*Door key interaction */
        doorA.addValidInitCollectable(card);
        // doorA.addValidInitCollectable(key);

        doorB.addValidInitCollectable(card);
        // doorB.addValidInitCollectable(key);

        doorC.addValidInitCollectable(card);
        // doorC.addValidInitCollectable(key);

        doorA.addValidSubCollectable(primaryCursor);
        doorB.addValidSubCollectable(primaryCursor);
        doorC.addValidSubCollectable(primaryCursor);

        add(roomInhabitants);
    }

    public void doorInteraction(){
        // System.out.println(doorA.getState() + " state");
        // System.out.println(changeRoom + " before conditional");
        if (doorA.getState() == 1 || doorB.getState() == 1 || doorC.getState() == 1) {
            changeRoom = true;
            // System.out.println(changeRoom + " after conditional");
            // System.out.println("room change");  // TESTING
            // setActiveRoom(chairClassroom.getActiveRoom());  // Might be redundant structure
            changeRoom();
        }
    }
    
    @Override
    public void updateRoom(GraphicsGroup displayText) {
        // System.out.println(displayText + " dt");
        // System.out.println(box.getState() +" bs");
        // if (displayText.getWidth() != 0) {
        //     displayText.removeAll();
        // }
        // else if (box.getState() == 1 && hasTextBeenShown == false) {
        //     displayText.add(new Image("assets" + File.separator + "sampleText.png"));
        //     hasTextBeenShown = true;
        // }
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
    
    public void addChairClassroom(GreenChairsRoom chairClassroom){
        this.chairClassroom = chairClassroom;
    }

    public void addWindowedClassroom(WindowedClassRoom windowedClassRoom){
        this.windowedClassRoom = windowedClassRoom;
    }

    public void addLectureHall(LectureHallRoom lectureHallRoom){
        this.lectureHallRoom = lectureHallRoom;
    }
}
