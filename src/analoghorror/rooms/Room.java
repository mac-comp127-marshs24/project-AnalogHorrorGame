package analoghorror.rooms;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import analoghorror.Sound;
import edu.macalester.graphics.*;

//room is a graphics group
public abstract class Room extends GraphicsGroup{
    protected GraphicsGroup roomInhabitants; 
    protected Image backgroundImage;
    protected Room activeRoom;
    protected boolean changeRoom;
    protected boolean jumpscarePresent;
    protected GraphicsGroup displayOverlay;

    protected Sound ambientSound;
    protected Sound jumpscareSound;

    

    public Room(String backgroundImage, GraphicsGroup overlayGroup) {
        super();
        roomInhabitants = new GraphicsGroup();
        this.backgroundImage = new Image(backgroundImage);
        add(this.backgroundImage);
        this.activeRoom = this;
        this.displayOverlay = overlayGroup;
        this.jumpscarePresent = false;
    }

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
    public abstract void updateRoom();

    public Image getBackgroundImage(){
        return backgroundImage;
    }

    public void setBackgroundImage(String path){
        backgroundImage.setImagePath(path);
    }

    public abstract void jumpscare();

    protected void scareDelay(){
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

}
