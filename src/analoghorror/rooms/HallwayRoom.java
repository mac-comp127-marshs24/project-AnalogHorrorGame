package analoghorror.rooms;

import java.io.File;
import java.util.Arrays;

import analoghorror.inhabitants.*;

public class HallwayRoom extends Room{
    // private static boolean changeRoom = false;
    Collectable primaryCursor;
    GreenChairsRoom classroom;
    Item box;
    Collectable key;
    Item door;
    Collectable card;
    Item sonic;
    

    public HallwayRoom(Collectable hand, String backgroundImage){
        super(backgroundImage);
        primaryCursor = hand;
        changeRoom = false;
        addRoomInhabitants();
    }

    public void addRoomInhabitants(){    
        box = new Item(255, 286, "assets" + File.separator + "chestClosed.png", false, 2);
        box.setStatePaths(Arrays.asList("assets" + File.separator + "chestClosed.png", "assets" + File.separator + "chestOpen.png"));
        this.roomInhabitants.add(box);  // Add to this.roomInhabitants

        key = new Collectable(60, 205, "assets" + File.separator + "silverKey.png", "key02");
        this.roomInhabitants.add(key);
        box.addValidInitCollectable(key);  // Add the Collectable to the internal validCollectable Sets for the Item

        door = new Item(385, 120, "assets" + File.separator + "doorClosed.png", false, 2);
        door.setStatePaths(Arrays.asList("assets" + File.separator + "doorClosed.png", "assets" + File.separator + "doorOpen.png"));
        this.roomInhabitants.add(door);

        sonic = new Item(778, 70, "assets" + File.separator + "sonicForward.png", true, 4);
        sonic.setStatePaths(Arrays.asList("assets" + File.separator + "sonicForward.png", "assets" + File.separator + "sonicDown.png",
        "assets" + File.separator + "sonicBack.png", "assets" + File.separator + "sonicUp.png"));
        this.roomInhabitants.add(sonic);
        sonic.addValidInitCollectable(primaryCursor);
        sonic.addValidSubCollectable(primaryCursor);

        card = new Collectable(528, 325, "assets" + File.separator + "studentCard.png", "card02");
        this.roomInhabitants.add(card);
        door.addValidInitCollectable(card);
        
        door.addValidInitCollectable(key);

        box.addValidSubCollectable(primaryCursor);
        door.addValidSubCollectable(primaryCursor);

        add(roomInhabitants);
    }

    public void doorInteraction(){
        System.out.println(door.getState() + " state");
        System.out.println(changeRoom + " before conditional");
        if (door.getState() == 1) {
            changeRoom = true;
            System.out.println(changeRoom + " after conditional");
            // System.out.println("room change");  // TESTING
            // setActiveRoom(classroom.getActiveRoom());  // Might be redundant structure
            changeRoom();
        }
    }
    
    @Override
    public void updateRoom() {
        doorInteraction();
    }

    private void changeRoom(){
        if(changeRoom){ //change changeRoom to a specific click event?
            //TODO: setActiveRoom should change the active room to the inputted new room, but ensure that is reflected on the canvas
            classroom.resetActiveRoom();
            setActiveRoom(classroom.getActiveRoom());
            door.changeState(0);
        }
    }
    
    public void addClassroom(GreenChairsRoom classroom){
        this.classroom = classroom;
    }

}
