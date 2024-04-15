package analoghorror;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import analoghorror.item.Item;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;

public class Key extends Item {

    public Key(CanvasWindow window, GraphicsGroup game, GraphicsGroup cursor, Item item) {
        super(window, game, cursor, item);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void drawInLocalCoordinates(Graphics2D gc) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawInLocalCoordinates'");
    }

    @Override
    public boolean testHitInLocalCoordinates(double x, double y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'testHitInLocalCoordinates'");
    }

    @Override
    public Rectangle2D getBounds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBounds'");
    }

    @Override
    protected Object getEqualityAttributes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEqualityAttributes'");
    }

    
}
