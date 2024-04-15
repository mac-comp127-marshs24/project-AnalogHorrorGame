package analoghorror.item;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import edu.macalester.graphics.*;

public class GeneralItem extends GraphicsObject {
    private boolean inInventory;
    private Image texture;
    private GraphicsGroup room;
    private Point spawnPoint;
    private Rectangle hitBox;
    /**
     * Inventory location; eventually change to be a "slot" in Inventory object?;
     * 
     * on initial click { Image.setInventorySlot... }
     *
     * that way the item can be returned to the same position
     */
    private Point inventorySlot;

    public GeneralItem(Image itemTexture, GraphicsGroup room, Point roomPosition, double width, double height) {
        inInventory = false;
        texture = itemTexture;
        roomPosition = spawnPoint;
        hitBox = new Rectangle(0, 0, width, height);
        hitBox.setCenter(roomPosition);
    }

    public void updateLocation(Point point){
        setCenter(point);
        texture.setCenter(point);
    }

    public Image getTexture(){
        return texture;
    }

    public void setInInventory(boolean status){
        inInventory = status;
    }

    public boolean getInInventory(){
        return inInventory;
    }

    public void setInventorySlot(Point point){
        inventorySlot = point;
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
