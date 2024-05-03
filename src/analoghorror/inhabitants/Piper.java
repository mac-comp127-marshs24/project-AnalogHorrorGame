package analoghorror.inhabitants;

import java.io.File;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import analoghorror.rooms.HallwayRoom;

public class Piper extends Item {
    HallwayRoom homeRoom; // Room for animation
    boolean startedFrameLoop; // Returns whether initial frame has started
    boolean piperDead; // Returns whether piper monster is dead
    int numberOfFrameLoops; 
    long frameDelay;
    Timer frameTimer;
    TimerTask changeFrame;

    /**
     * An interactable animation for the piper monster that extends the Item class. Arugments determine animation position and the room in which it occurs.
     * @param x 
     * @param y
     * @param hallway Animation occurs in Hallway.
     */
    public Piper(double x, double y, HallwayRoom hallway) {
        super(x, y, "assets" + File.separator + "piper" + File.separator + "frame0.jpg", true, 5);
        homeRoom = hallway; //sets hallway object from HorrorGame to homeroom
        startedFrameLoop = false;
        piperDead = false;
        numberOfFrameLoops = 0;
        frameDelay = 1000;

        //Frames for monster animation
        setStatePaths(Arrays.asList(
            "assets" + File.separator + "piper" + File.separator + "frame0.png",
            "assets" + File.separator + "piper" + File.separator + "frame1.png",
            "assets" + File.separator + "piper" + File.separator + "frame2.png",
            "assets" + File.separator + "piper" + File.separator + "frame3.png",
            "assets" + File.separator + "piper" + File.separator + "frame4.png"
            ));
        changeState(1);
    }

    @Override
    public void interaction(Collectable collectable) {
        if (collectableIsValid(collectable, validSubCollectables)) {
            currentState = 0;  // Monster's death image path
            setImagePath(itemTextures.get(currentState)); // Monster's death image path
            homeRoom.updateRoom();
        }
    }

    /**
     * Changes frames if the current animation frame is not equal to the max number of frames and if current frame is not equal to 0.
     */
    private void changeFrameBehavior(){
        if (currentState != itemStates && currentState != 0) {  // or 4
            currentState++;
            setImagePath(itemTextures.get(currentState));
            if (itemStates == 4 && !piperDead) { // if the animation reaches the fourth frame and the piper isn't dead, show death screen in update room.
                homeRoom.updateRoom();
            }
        }
    }

    /**
     * Starts piper animation and runs frame behavior.
     */
    public void piperStart(){
        for(int i = 1; i <= itemStates; i++){
            if (startedFrameLoop == false) {
                startedFrameLoop = true;
                frameTimer = new Timer(false);
                frameTimer.schedule(new TimerTask() {
                    @Override
                    public void run(){
                        changeFrameBehavior();
                    }
                }, 0, i * 3000);
            }
        }
    }

    /**
     * Stops piper animation by setting piperDead to true.
     */
    public void piperEnd(){
        piperDead = true;
    }
}
