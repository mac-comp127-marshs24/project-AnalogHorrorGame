package analoghorror.rooms;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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

    public void setBackgroundImage(String path){
        backgroundImage.setImagePath(path);
    }

    //CREDIT: https://stackoverflow.com/questions/21369365/how-to-stop-a-sound-while-its-playing-from-another-method
    public void playSound(String filePath) {
        try {
          // loads sound 
          AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
          Clip clip = AudioSystem.getClip();
          clip.open(audioInputStream);
      
          // plays sound
          clip.start();
        } 
        
        catch (Exception e) {
          e.printStackTrace();
          // handles potential exceptions (e.g., file not found, audio format unsupported)
          System.err.println("Error playing sound: " + e.getMessage());
        }
      }
}
