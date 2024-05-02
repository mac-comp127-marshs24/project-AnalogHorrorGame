package analoghorror.inhabitants;

import java.io.File;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import analoghorror.rooms.HallwayRoom;

public class Piper extends Item {
    HallwayRoom homeRoom;
    Boolean startedFrameLoop;
    int numberOfFrameLoops;
    Timer frameTimer;
    TimerTask changeFrame;
    long frameDelay;

    public Piper(double x, double y, HallwayRoom hallway) {
        super(x, y, "assets" + File.separator + "piper" + File.separator + "frame0.jpg", true, 5);
        //TODO Auto-generated constructor stub
        frameDelay = 1000;
        homeRoom = hallway;
        startedFrameLoop = false;
        numberOfFrameLoops = 0;
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
            currentState = 0;  // death state; initialize at 1
            setImagePath(itemTextures.get(currentState)); // monster's return path
            homeRoom.updateRoom();
        }
    }

    private void changeFrameBehavior(){
        if (currentState != itemStates && currentState != 0) {  // or 4
            // System.out.println("Current state " + currentState);
            currentState++;
            // System.out.println("State after ++ " + currentState);
            setImagePath(itemTextures.get(currentState));
            if (itemStates == 4) {
                homeRoom.updateRoom();
            }
        }
    }

    public void piperStart(){
        for(int i = 1; i <= itemStates; i++){
            System.out.println("Loop " + i);
            if (startedFrameLoop == false) {
                startedFrameLoop = true;
                // frameTimer.cancel();
                frameTimer = new Timer(false);
                frameTimer.schedule(new TimerTask() {
                    @Override
                    public void run(){
                        // i--;  ??
                        changeFrameBehavior();
                    }
                }, 0, i * 3000);
            }
        }
    }
    public void piperEnd(){
        // stop all timers?
    }
}
