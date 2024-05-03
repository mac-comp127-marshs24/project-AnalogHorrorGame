package analoghorror.inhabitants;

import java.io.File;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import analoghorror.rooms.LectureHallRoom;

public class Puzzle extends Item {
    LectureHallRoom homeRoom;

    boolean failState;
    boolean solved;

    int attemptedClears;
    long puzzleDelay;
    long scareDelay;

    Timer puzzleTimer;
    Timer jumpscareTimer;
    TimerTask clunkTask;
    TimerTask scareTask;

    /**
     * An Item that gives you 3 tries to succeed in opening it. If you fail,
     * calls a jumpscare().
     * 
     * @param x
     * @param y
     * @param lectureHallRoom
     */
    public Puzzle(double x, double y, LectureHallRoom lectureHallRoom) {
        super(x, y, "assets" + File.separator + "puzzle" + File.separator + "puzzleBoard.png", false, 10);
        homeRoom = lectureHallRoom;
        failState = false;
        solved = false;
        attemptedClears = 0;
        puzzleDelay = 5000;
        scareDelay = 500;
        puzzleTimer = new Timer();
        jumpscareTimer = new Timer();

        clunkTask = new TimerTask() {
            @Override
            public void run() {
                clunkTaskBehavior();
            }
        };

        scareTask = new TimerTask() {
            @Override
            public void run() {
                scareTaskBehavior();
            }
        };

        setStatePaths(Arrays.asList(
            "assets" + File.separator + "puzzle" + File.separator + "puzzleBoard.png",

            "assets" + File.separator + "puzzle" + File.separator + "botRightPuzzleBoard.png",
            "assets" + File.separator + "puzzle" + File.separator + "botMidPuzzleBoard.png",
            "assets" + File.separator + "puzzle" + File.separator + "botLeftPuzzleBoard.png",

            "assets" + File.separator + "puzzle" + File.separator + "midRightPuzzleBoard.png",
            "assets" + File.separator + "puzzle" + File.separator + "midMidPuzzleBoard.png",
            "assets" + File.separator + "puzzle" + File.separator + "midLeftPuzzleBoard.png",

            "assets" + File.separator + "puzzle" + File.separator + "topRightPuzzleBoard.png",
            "assets" + File.separator + "puzzle" + File.separator + "topMidPuzzleBoard.png",
            "assets" + File.separator + "puzzle" + File.separator + "topLeftPuzzleBoard.png"));
    }

    public boolean getFailState() {
        return failState;
    }

    @Override
    public void interaction(Collectable collectable) {
        if (currentState == itemStates && singleUse == false && collectableIsValid(collectable, validSubCollectables)
            && solved == false) {
            currentState = 1;
            setImagePath(itemTextures.get(currentState));
            cancelStartClunk();
        } else if (currentState == 0 && collectableIsValid(collectable, validInitialCollectables) && solved == false) {
            currentState++;
            setImagePath(itemTextures.get(currentState));
            cancelStartClunk();
        } else if (currentState > 0 && currentState < itemStates
            && collectableIsValid(collectable, validSubCollectables) && solved == false) {
            currentState++;
            setImagePath(itemTextures.get(currentState));
            cancelStartClunk();
        }
    }

    /**
     * Schedules a timer to check for a win or fail state.
     */
    private void activateSquare() {
        puzzleTimer.schedule(clunkTask, puzzleDelay);
    }

    /**
     * Checks for a win or fail state and prompts the homeRoom to spawn a "Clunk! sound" Item.
     */
    private void clunkTaskBehavior() {
        homeRoom.clunk();
        attemptedClears++;
        if (currentState == 9) {
            solved = true;
            setImagePath("assets" + File.separator + "puzzle" + File.separator + "openPuzzleBoard.png");
            homeRoom.updateRoom();
        } else if (attemptedClears == 3) {
            jumpscareTimer.schedule(scareTask, scareDelay);
        }
    }

    /**
     * Calls homeRoom.updateRoom() to activate jumpscare().
     */
    private void scareTaskBehavior() {
        failState = true;
        homeRoom.updateRoom();
    }

    /**
     * @return true if puzzle is solved
     */
    public boolean getSolved() {
        return solved;
    }

    /**
     * Cancels any current puzzleTimer and starts a new one so you can't just click through the puzzle and get the answer.
     */
    private void cancelStartClunk(){
        puzzleTimer.cancel();
        puzzleTimer = new Timer();
        clunkTask = new TimerTask() {
            @Override
            public void run() {
                clunkTaskBehavior();
            }
        };
        activateSquare();
    }
}
