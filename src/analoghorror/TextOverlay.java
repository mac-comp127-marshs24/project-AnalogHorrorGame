package analoghorror;
import java.util.Timer;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;

public class TextOverlay extends GraphicsGroup{
    Image text;
    Timer duration;
    public TextOverlay (Timer duration, Image text){
        this.duration = duration;
        this.text = text;
    }

    private void textPopUp(Image text){

    }
}
