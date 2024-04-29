package analoghorror.rooms;
import java.io.File;
import java.util.Arrays;

import analoghorror.inhabitants.*;
import edu.macalester.graphics.GraphicsGroup;

public class GreenChairsRoom extends Room{
    Collectable primaryCursor;
    HallwayRoom hallway;

    //TODO: Replace with actual items
    Item ratCage;
    Collectable poisonedRat;
    Item door;
    boolean poisonedRatInteraction;
    // Collectable card;
    // Item sonic;

    public GreenChairsRoom(HallwayRoom hallway, Collectable hand, String backgroundImage) {
        super(backgroundImage);
        this.hallway = hallway;
        primaryCursor =  hand;
        changeRoom = false;
        poisonedRatInteraction = false;
        addRoomInhabitants();
    }

    //add items to roomInhabitants here
	@Override
	public void addRoomInhabitants() {
		ratCage = new Item(500, 201, "assets" + File.separator + "ratCageSmallRat.png", true, 4);
        ratCage.setStatePaths(Arrays.asList("assets" + File.separator + "ratCageSmallRat.png", "assets" + File.separator + "ratCageBigRat.png", "assets"
         + File.separator + "ratCageHugeRat.png", "assets" + File.separator + "ratCageEmpty.png"));
        this.roomInhabitants.add(ratCage);  // Add to "Room" (GraphicsGroup for now)

        poisonedRat = new Collectable(500, 201, "assets" + File.separator + "looseRat.png", "rat01");
        // this.roomInhabitants.add(key);
        door = new Item(385, 120, "assets" + File.separator + "doorClosed.png", false, 2);
        door.setStatePaths(Arrays.asList("assets" + File.separator + "doorClosed.png", "assets" + File.separator + "doorOpen.png"));
        this.roomInhabitants.add(door);

        // sonic = new Item(778, 70, "assets" + File.separator + "sonicForward.png", true, 4);
        // sonic.setStatePaths(Arrays.asList("assets" + File.separator + "sonicForward.png", "assets" + File.separator + "sonicDown.png",
        // "assets" + File.separator + "sonicBack.png", "assets" + File.separator + "sonicUp.png"));
        // this.roomInhabitants.add(sonic);
        // sonic.addValidInitCollectable(primaryCursor);
        // sonic.addValidSubCollectable(primaryCursor);

        // card = new Collectable(528, 325, "assets" + File.separator + "studentCard.png", "card01");
        // this.roomInhabitants.add(card);
        ratCage.addValidInitCollectable(primaryCursor);
        ratCage.addValidSubCollectable(primaryCursor);
        door.addValidInitCollectable(primaryCursor);
        door.addValidInitCollectable(primaryCursor);

        // box.addValidSubCollectable(primaryCursor);
        // door.addValidSubCollectable(primaryCursor);

        add(roomInhabitants);
	}

    public void doorInteraction(){
        if (door.getState() == 1) {
            changeRoom = true;
            // System.out.println("room change");  // TESTING
            // setActiveRoom(hallway.getActiveRoom());  // Might be redundant structure
            changeRoom();
        }
    }
   
    @Override
    public void updateRoom(GraphicsGroup displayText) {
        if (ratCage.getState() == 3 && poisonedRatInteraction == false) {
            this.roomInhabitants.add(poisonedRat);
            poisonedRatInteraction = true;
        }
        doorInteraction();
    }

    private void changeRoom(){
        if(changeRoom){ //change changeRoom to a specific click event?
            //TODO: setActiveRoom should change the active room to the inputted new room, but ensure that is reflected on the canvas
            hallway.resetActiveRoom();
            setActiveRoom(hallway.getActiveRoom());
            door.changeState(0);
        }
    }

    
}
