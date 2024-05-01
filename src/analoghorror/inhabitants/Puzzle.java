package analoghorror.inhabitants;

import java.io.File;
import java.util.Arrays;

public class Puzzle extends Item {
    boolean failState;
    int attemptedClears;

    public Puzzle(double x, double y, boolean isSingleUse, int numberOfItemStates) {
        super(x, y, "assets" + File.separator + "puzzle" + File.separator + "puzzleBoard.png", isSingleUse, numberOfItemStates);
        failState = false;
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

    public boolean getFailState(){
        return failState;
    }

    @Override
    public void interaction(Collectable collectable){
        if (currentState == 8 && collectableIsValid(collectable, validSubCollectables)) {
            currentState++;
            setImagePath(itemTextures.get(currentState));
            collectable.setUsedTrue();
            rightSquare();
        }
        else if (currentState == itemStates && singleUse == false && collectableIsValid(collectable, validSubCollectables)) {
            currentState = 0;
            setImagePath(itemTextures.get(currentState));
            collectable.setUsedTrue();
            wrongSquare();
        }
        else if (currentState == 0 && collectableIsValid(collectable, validInitialCollectables)) {
            currentState++;
            setImagePath(itemTextures.get(currentState));  // itemTextures.get(1)
            collectable.setUsedTrue();
            wrongSquare();
        }
        else if (currentState > 0 && currentState < itemStates
            && collectableIsValid(collectable, validSubCollectables)) {
            currentState++;
            setImagePath(itemTextures.get(currentState));
            collectable.setUsedTrue();
            wrongSquare();
        }
    }

    private void wrongSquare(){
        System.out.println("WRONG");
    }
    
    private void rightSquare(){
        System.out.println("RIGHT");
    }
}
