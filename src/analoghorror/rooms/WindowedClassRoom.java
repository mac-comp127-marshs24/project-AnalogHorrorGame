package analoghorror.rooms;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.events.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import analoghorror.inhabitants.*;
import edu.macalester.graphics.*;

public class WindowedClassRoom extends Room{
    Collectable primaryCursor;
    HallwayRoom hallway;

    //TODO: Replace with actual items
    Item box;
    Collectable key;
    Item door;
    Collectable card;
    Item sonic;

    public WindowedClassRoom(HallwayRoom hallway, Collectable hand, String backgroundImage) {
        super(backgroundImage);
        this.hallway = hallway;
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
    public void updateRoom() {
        doorInteraction();
    }

    private void changeRoom(){
        if(changeRoom){ 
            hallway.resetActiveRoom();
            setActiveRoom(hallway.getActiveRoom());
            door.changeState(0);
        }
    }
    
}
