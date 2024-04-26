package analoghorror.rooms;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.events.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import analoghorror.inhabitants.*;
import edu.macalester.graphics.*;

public class LectureHallRoom extends Room {
    Collectable primaryCursor;
    HallwayRoom hallway;

    public LectureHallRoom(HallwayRoom hallway, Collectable hand, String backgroundImage) {
        super(backgroundImage);
        this.hallway = hallway;
        primaryCursor =  hand;
        changeRoom = false;
    }

    @Override
    public void addRoomInhabitants() {
       // TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addRoomInhabitants'");
    }

    @Override
    public void updateRoom() {
        if(changeRoom){ //change changeRoom to a specific click event?
            //TODO: setActiveRoom should change the active room to the inputted new room, but ensure that is reflected on the canvas
            setActiveRoom(hallway);
        }
    }

}
