package analoghorror.inhabitants;

import java.io.File;
import java.util.Arrays;

public class Puzzle extends Item {

    public Puzzle(double x, double y, boolean isSingleUse, int numberOfItemStates) {
        super(x, y, "assets" + File.separator + "puzzle" + File.separator + "puzzleBoard.png", isSingleUse, numberOfItemStates);
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

    
    
}
