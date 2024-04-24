package analoghorror.rooms;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.*;
import edu.macalester.graphics.Rectangle;

/* We can have an interface for rooms/settings to kinda normalize behaviors if that makes sense? */
/*
 * Like what behavior do we want all rooms to have: probably like a way of naviating out of said
 * room, normalize behaviors of interacting w said room, etc kinda?
 */
/* we can maybe do the same w items */
public abstract class Room {
    Rectangle moveForward = new Rectangle(20, 20, 20, 20);
    Rectangle moveBackwards = new Rectangle(320, 20, 20, 20);
    GraphicsGroup room;
    // temp rectangle, can change named to left/right

    public Room(CanvasWindow window) {
        window.add(roomContents()); //add stuff from current room
        changeRooms(window);
        
    }

    /**
     * Add specific contents to room
     * @return room graphics group
     */
    protected GraphicsGroup roomContents(){
        room = new GraphicsGroup();

        //add background/items to graphics group
        //add movement arrows
        room.add(moveForward);
        room.add(moveBackwards);

        return room;
    }
    
    /**
     * Changes from one room object to another
     * @param window
     */
    private void changeRooms(CanvasWindow window){
        window.onClick((event) -> {
            if (window.getElementAt(event.getPosition()) == moveForward){
                window.removeAll();
                window.add(roomContents()); //each room has its own roomContents, so then we can maybe do like hallway.RoomContents and get that?
            }

            else if (window.getElementAt(event.getPosition()) == moveBackwards){
                window.removeAll();
                window.add(roomContents()); //specific room content
            }
        });
    }

}
