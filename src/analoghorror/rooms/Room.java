package analoghorror.rooms;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.*;

//room is a graphics group
public abstract class Room extends GraphicsGroup{
    protected GraphicsGroup roomInhabitants; 
    protected Image backgroundImage;
    protected Room activeRoom;
    protected boolean changeRoom;

    public Room(String backgroundImage) {
        super();
        roomInhabitants = new GraphicsGroup();
        this.backgroundImage = new Image(backgroundImage);
        add(this.backgroundImage);
        this.activeRoom = this;
    }

    //add items to item graphics group in room so not on same "layer" as bg
    //idk it doesnt have to be on different "layers" aka graphics groups i just feel like its better to have that just in case? idk

    public GraphicsGroup getRoomInhabitants() {
        return roomInhabitants;
    }

    public void resetActiveRoom(){
        activeRoom = this;
        changeRoom = false;
    }

    public Room getActiveRoom(){
        return activeRoom;
    }

    public void setActiveRoom(Room activeRoom) {
        this.activeRoom = activeRoom;
    }

    public abstract void addRoomInhabitants();
    public abstract void updateRoom(GraphicsGroup displayText);

    public Image getBackgroundImage(){
        return backgroundImage;
    }
}
