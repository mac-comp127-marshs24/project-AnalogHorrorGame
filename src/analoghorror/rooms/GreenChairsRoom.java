package analoghorror.rooms;
import analoghorror.inhabitants.Collectable;
import edu.macalester.graphics.GraphicsGroup;

public class GreenChairsRoom extends Room{
    Collectable primaryCursor;
    HallwayRoom hallway;

    public GreenChairsRoom(HallwayRoom hallway, Collectable hand, String backgroundImage) {
        super(backgroundImage);
        this.hallway = hallway;
        primaryCursor =  hand;
        changeRoom = false;
    }

    //add items to roomInhabitants here
	@Override
	public void addRoomInhabitants() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addRoomInhabitants'");
	}
   
    @Override
    public void updateRoom() {
        if(changeRoom){ //change changeRoom to a specific click event?
            //TODO: setActiveRoom should change the active room to the inputted new room, but ensure that is reflected on the canvas
            setActiveRoom(hallway);
        }
    }

    
}
