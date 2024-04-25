package analoghorror.rooms;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;

import java.util.List;

import edu.macalester.graphics.*;
import analoghorror.inhabitants.*;

//room is a graphics group
public abstract class Room extends GraphicsGroup{
    private GraphicsGroup roomInhabitants; 
    private Room activeRoom;
    private boolean changeRoom;

    public Room() {
        this.roomInhabitants = new GraphicsGroup();
        this.activeRoom = this;
        this.changeRoom = false;
    }

    public GraphicsGroup getRoomInhabitants() {
        return roomInhabitants;
    }

    //add items to item graphics group in room so not on same "layer" as bg
    //idk it doesnt have to be on different "layers" aka graphics groups i just feel like its better to have that just in case? idk

    public void addMultipleToRoom(List <Item> items){
        items.stream().forEach(roomInhabitants::add);
    }

    public void updateRoom(Room differentRoom) {
        if(changeRoom){
            activeRoom = differentRoom;
        }
    }

}
