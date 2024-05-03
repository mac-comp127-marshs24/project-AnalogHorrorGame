package analoghorror;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    
    public Sound(){
    }

    public void playSound(String filePath) {
        try {
          // loads sound 
          AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
          clip = AudioSystem.getClip();
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

      public void loopSound(int numOfLoops){
        clip.loop(numOfLoops);
    }

    public void stopSound(){
        clip.stop();
        clip.close();
      }
}
