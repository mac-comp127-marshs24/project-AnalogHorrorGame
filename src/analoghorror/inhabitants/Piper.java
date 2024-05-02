package analoghorror.inhabitants;

import java.io.File;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import analoghorror.rooms.HallwayRoom;

public class Piper extends Item {
    HallwayRoom homeRoom;
    boolean startedFrameLoop;
    boolean piperDead;
    int numberOfFrameLoops;
    long frameDelay;
    Timer frameTimer;
    TimerTask changeFrame;

    public Piper(double x, double y, HallwayRoom hallway) {
        super(x, y, "assets" + File.separator + "piper" + File.separator + "frame0.jpg", true, 5);
        homeRoom = hallway;
        startedFrameLoop = false;
        piperDead = false;
        numberOfFrameLoops = 0;
        frameDelay = 1000;
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

    private void changeFrameBehavior(){
        if (currentState != itemStates && currentState != 0) {  // or 4
            currentState++;
            setImagePath(itemTextures.get(currentState));
            if (itemStates == 4 && !piperDead) {
                homeRoom.updateRoom();
            }
        }
    }

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
    public void piperEnd(){
        piperDead = true;
        // Stop all timers?
    }
}
