package analoghorror.rooms;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.events.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import analoghorror.inhabitants.*;
import edu.macalester.graphics.*;

public class HallwayRoom extends Room{
    private static boolean changeRoom = false;
    private GreenChairsRoom classroom;
    static GraphicsGroup roomInhabitants;
    
    Image background;

    Item box;
    Collectable key;
    static Item door;
    

    public HallwayRoom(GreenChairsRoom classroom){
        this.classroom = classroom;
        this.roomInhabitants = new GraphicsGroup();

        background = new Image("assets" + File.separator + "hall.png");
        this.add(background);

        addRoomInhabitants();
        
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

        this.add(roomInhabitants);

    }

    //TODO: delete once functional
    //couldn't call this in here from horror game for some reason, will delete later
    public static GraphicsObject check(MouseButtonEvent event, GraphicsGroup group) {
        GraphicsObject item = group.getElementAt(event.getPosition());
        return item;
    }

    //how it'd probably look in horror game?
    public static void main(String[] args) {
        CanvasWindow temp = new CanvasWindow("temp", 854, 400);
        GreenChairsRoom classroom1 =  new GreenChairsRoom();
        HallwayRoom hallway = new HallwayRoom(classroom1);
        temp.add(hallway);
        temp.onClick((event)->
        {
            if(door == check(event, roomInhabitants)){
                changeRoom = true;
                System.out.println("new room!");
                hallway.setActiveRoom(classroom1);  //this would most likely be a call to hallway.updateRoom()? cant do here bc main static             
            }
        
        }
        );
    }
    
    @Override
    public void updateRoom() {
        if(changeRoom){ //change changeRoom to a specific click event?
            //TODO: setActiveRoom should change the active room to the inputted new room, but ensure that is reflected on the canvas
            setActiveRoom(classroom);
        }
    }

    

}
