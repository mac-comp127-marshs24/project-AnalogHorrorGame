package analoghorror.rooms;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import analoghorror.Sound;
import edu.macalester.graphics.*;

public abstract class Room extends GraphicsGroup{
    protected GraphicsGroup roomInhabitants; 
    protected GraphicsGroup displayOverlay;
    protected Image backgroundImage;
    protected Room activeRoom;
    protected boolean changeRoom;
    protected Sound primarySound;

    /**
     * An extension of GraphicsGroup, each room has a background and roomInhbitants layer.
     */
    public Room(String backgroundImage, GraphicsGroup overlayGroup) {
        super();
        roomInhabitants = new GraphicsGroup();
        this.backgroundImage = new Image(backgroundImage);
        add(this.backgroundImage);
        this.activeRoom = this;
        this.displayOverlay = overlayGroup;
    }

    /**
     * @return roomInhabitants layer of Room.
     */
    public GraphicsGroup getRoomInhabitants() {
        return roomInhabitants;
    }

    /**
     * ActiveRoom is used during the room change process.
     * The current room is always the ActiveRoom of the current room.
     * When not changing rooms, ActiveRoom is the current room.
     * When changing rooms, ActiveRoom is set to the room that is being entered.
     * The current room is the ActiveRoom of the current room.
     * Then the former room's active room can be reset with resetActiveRoom() to enable
     * future travel.
     */
    public void resetActiveRoom(){
        activeRoom = this;
        changeRoom = false;
    }

    /**
     * ActiveRoom is used during the room change process.
     * The current room is always the ActiveRoom of the current room.
     * When not changing rooms, ActiveRoom is the current room.
     * When changing rooms, ActiveRoom is set to the room that is being entered.
     * The current room is the ActiveRoom of the current room.
     * Then the former room's active room can be reset with resetActiveRoom() to enable
     * future travel.
     * 
     * @return Room
     */
    public Room getActiveRoom(){
        return activeRoom;
    }

    /**
     * ActiveRoom is used during the room change process.
     * The current room is always the ActiveRoom of the current room.
     * When not changing rooms, ActiveRoom is the current room.
     * When changing rooms, ActiveRoom is set to the room that is being entered.
     * The current room is the ActiveRoom of the current room.
     * Then the former room's active room can be reset with resetActiveRoom() to enable
     * future travel.
     * 
     * @param activeRoom
     */
    public void setActiveRoom(Room activeRoom) {
        this.activeRoom = activeRoom;
    }

    /**
     * Add all of the Items, Collectables, etc. to the Room.
     */
    public abstract void addRoomInhabitants();

    /**
     * Called to check interaction conditions within the Room.
     */
    public abstract void updateRoom();

    public void setBackgroundImage(String path){
        backgroundImage.setImagePath(path);
    }

    /**
     * Hands jumpscare plays audio, displays the image asset, and exits the game after a timer.
     */
    public void jumpscare(){
        primarySound.stopSound();
        primarySound.playSound("res" + File.separator + "assets" + File.separator + "audio" + File.separator + "jumpscareBagpipe.wav");
        displayOverlay.add(new Image("assets" + File.separator + "piper" + File.separator + "hands.png"));
        scareDelay();
    }
    
    /**
     * Closes the game after a 1.5 sec. delay.
     */
    protected void scareDelay() {
        long delay = 1500;
        Timer jumpscareTimer = new Timer();
        TimerTask jumpscareTask = new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        };
        jumpscareTimer.schedule(jumpscareTask, delay);
    }

    /**
     * Closes the game after a 10 sec. delay.
     */
    protected void winDelay(){
        long delay = 10000;
        Timer jumpscareTimer = new Timer();
        TimerTask jumpscareTask = new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        };
        jumpscareTimer.schedule(jumpscareTask, delay);
    }

}
