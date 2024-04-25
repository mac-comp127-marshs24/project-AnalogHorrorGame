package analoghorror.rooms;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;

import java.util.List;

import analoghorror.Inventory;
import edu.macalester.graphics.*;
import edu.macalester.graphics.Rectangle;
import analoghorror.inhabitants.*;

//room is a graphics group
public abstract class Room extends GraphicsGroup{
    private GraphicsGroup roomInhabitants; 
    private Room activeRoom;
    private boolean changeRoom;

    public Room() {
        //have inventory in room
        this.roomInhabitants = new GraphicsGroup();
        this.activeRoom = this;
        this.changeRoom = false;
    }

    //add items to item graphics group in room so not on same "layer" as bg
    //idk it doesnt have to be on different "layers" aka graphics groups i just feel like its better to have that just in case? idk

    public GraphicsGroup getRoomInhabitants() {
        return roomInhabitants;
    }

    public Room getActiveRoom(){
        return activeRoom;
    }

    public void setActiveRoom(Room activeRoom) {
        this.activeRoom = activeRoom;
    }

    public abstract void updateRoom();

    //temp from horrorgame, remove
}
