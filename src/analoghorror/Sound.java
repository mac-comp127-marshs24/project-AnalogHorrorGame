package analoghorror;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    
    public Sound(){
    }

    /**
     * Loads sound at filePath and plays it. If the file isn't found or other errors are encountered, an error is printed.
     * 
     * @param filePath
     */
    public void playSound(String filePath) {
        try {
          AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
          clip = AudioSystem.getClip();
          clip.open(audioInputStream);
      
          clip.start();
        } 
        catch (Exception e) {
          e.printStackTrace();
          // Handles potential exceptions (e.g., file not found, audio format unsupported)
          System.err.println("Error playing sound: " + e.getMessage());
        }
      }

    /**
     * Loops sound number of times specified.
     * 
     * @param numOfLoops
     */
    public void loopSound(int numOfLoops){
        clip.loop(numOfLoops);
    }

    /**
     * Stops and closes sound.
     */
    public void stopSound(){
        clip.stop();
        clip.close();
      }
}
