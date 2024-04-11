package analoghorror;
/*We can have an interface for rooms/settings to kinda normalize behaviors if that makes sense?*/
/*Like what behavior do we want all rooms to have: probably like a way of naviating out of said room, normalize behaviors of interacting w said room, etc kinda? */
/*we can maybe do the same w items */
public interface Room {
    public void moveBetweenRooms(Room room);
    //arrow button labelled with destination room
    //button.onclick
    //clears canvas of previous room
    //moves player screen to desination room

    
}
