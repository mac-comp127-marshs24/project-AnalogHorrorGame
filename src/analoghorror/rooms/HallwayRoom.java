package analoghorror.rooms;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.events.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import analoghorror.inhabitants.*;
import edu.macalester.graphics.*;

public class HallwayRoom extends Room{
    // private static boolean changeRoom = false;
    Collectable primaryCursor;
    GreenChairsRoom classroom;
    Item box;
    Collectable key;
    Item door;
    Collectable card;
    Item sonic;
    

    public HallwayRoom(GreenChairsRoom classroom, Collectable hand, String backgroundImage){
        super(backgroundImage);
        this.classroom = classroom;
        primaryCursor = hand;
        changeRoom = false;
        // this.roomInhabitants = new GraphicsGroup();
        // this.add(background)/;

        addRoomInhabitants();
        doorInteraction();
        
    }

    public void addRoomInhabitants(){    
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

    //how it'd probably look in horror game?
    // public static void main(String[] args) {
    //     CanvasWindow temp = new CanvasWindow("temp", 854, 400);
    //     GreenChairsRoom classroom1 =  new GreenChairsRoom();
    //     HallwayRoom hallway = new HallwayRoom(classroom1);
    //     temp.add(hallway);
    //     temp.onClick((event)->
    //     {
    //         if(door == check(event, roomInhabitants)){
    //             changeRoom = true;
    //             System.out.println("new room!");
    //             hallway.setActiveRoom(classroom1);  //this would most likely be a call to hallway.updateRoom()? cant do here bc main static             
    //         }
        
    //     }
    //     );
    // }

    public void doorInteraction(){
        if (door.getState() == 1) {
            changeRoom = true;
            System.out.println("room change");
            updateRoom();
        }
    }
    
    @Override
    public void updateRoom() {
        if(changeRoom){ //change changeRoom to a specific click event?
            //TODO: setActiveRoom should change the active room to the inputted new room, but ensure that is reflected on the canvas
            setActiveRoom(classroom);
        }
    }

    

}
