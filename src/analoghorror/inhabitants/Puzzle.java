package analoghorror.inhabitants;

import java.io.File;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import analoghorror.rooms.LectureHallRoom;

public class Puzzle extends Item {
    boolean failState;
    boolean solved;
    boolean scheduled;
    int attemptedClears;
    Timer puzzleTimer;
    Timer jumpscareTimer;
    TimerTask clunkTask;
    TimerTask scareTask;
    long puzzleDelay;
    long scareDelay;
    LectureHallRoom homeRoom;

    public Puzzle(double x, double y, boolean isSingleUse, int numberOfItemStates, LectureHallRoom lectureHallRoom) {
        super(x, y, "assets" + File.separator + "puzzle" + File.separator + "puzzleBoard.png", isSingleUse,
            numberOfItemStates);
        failState = false;
        solved = false;
        scheduled = false;
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
        puzzleDelay = 5000;
        scareDelay = 500;
        attemptedClears = 0;
        homeRoom = lectureHallRoom;
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
            collectable.setUsedTrue();
            puzzleTimer.cancel();
            puzzleTimer = new Timer();
            clunkTask = new TimerTask() {
                @Override
                public void run() {
                    clunkTaskBehavior();
                }
            };
            activateSquare();
        } else if (currentState == 0 && collectableIsValid(collectable, validInitialCollectables) && solved == false) {
            currentState++;
            setImagePath(itemTextures.get(currentState));
            collectable.setUsedTrue();
            puzzleTimer.cancel();
            puzzleTimer = new Timer();
            clunkTask = new TimerTask() {
                @Override
                public void run() {
                    clunkTaskBehavior();
                }
            };
            activateSquare();
        } else if (currentState > 0 && currentState < itemStates
            && collectableIsValid(collectable, validSubCollectables) && solved == false) {
            currentState++;
            setImagePath(itemTextures.get(currentState));
            collectable.setUsedTrue();
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

    private void activateSquare() {
        scheduled = true;
        puzzleTimer.schedule(clunkTask, puzzleDelay);
        // System.out.println("WRONG");
    }

    private void clunkTaskBehavior() {
        System.out.println("Clunk!");
        attemptedClears++;
        scheduled = false;
        if (currentState == 9) {
            solved = true;
            System.out.println("Solved!");
            setImagePath("assets" + File.separator + "puzzle" + File.separator + "openPuzzleBoard.png");
            homeRoom.updateRoom();
        } else if (attemptedClears == 3) {
            jumpscareTimer.schedule(scareTask, scareDelay);
        }
    }

    private void scareTaskBehavior() {
        failState = true;
        System.out.println("Yeah you die");
        homeRoom.updateRoom();
    }

    public boolean getSolved() {
        return solved;
    }
}
