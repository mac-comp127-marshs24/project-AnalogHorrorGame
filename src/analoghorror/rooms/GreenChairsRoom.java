package analoghorror.rooms;
import java.io.File;
import java.util.Arrays;

import analoghorror.Inventory;
import analoghorror.inhabitants.*;
import edu.macalester.graphics.GraphicsGroup;

public class GreenChairsRoom extends Room{
    Collectable primaryCursor;
    HallwayRoom hallway;
    Inventory inventory;

    //TODO: Replace with actual items
    Item ratCage;
    Collectable poisonedRat;
    Collectable windowBoxKey;
    Item door;
    boolean poisonedRatInteraction;
    boolean roomScare;
    // Collectable card;
    // Item sonic;

    public GreenChairsRoom(HallwayRoom hallway, Collectable hand, String backgroundImage, Inventory inventory) {
        super(backgroundImage);
        this.hallway = hallway;
        this.inventory = inventory;
        primaryCursor =  hand;
        changeRoom = false;
        poisonedRatInteraction = false;
        roomScare = false;
        addRoomInhabitants();
    }

    //add items to roomInhabitants here
	@Override
	public void addRoomInhabitants() {
		door = new Item(716, 60, "assets" + File.separator + "greenChairRoomDoorClosed.png", false, 2);
        door.setStatePaths(Arrays.asList("assets" + File.separator + "greenChairRoomDoorClosed.png", "assets" + File.separator + "greenChairRoomDoorOpen.png"));
        this.roomInhabitants.add(door);
        ratCage = new Item(500, 201, "assets" + File.separator + "ratCageSmallRat.png", true, 4);
        ratCage.setStatePaths(Arrays.asList("assets" + File.separator + "ratCageSmallRat.png", "assets" + File.separator + "ratCageBigRat.png", "assets"
         + File.separator + "ratCageHugeRat.png", "assets" + File.separator + "ratCageEmpty.png"));
        this.roomInhabitants.add(ratCage);  // Add to "Room" (GraphicsGroup for now)

        poisonedRat = new Collectable(500, 201, "assets" + File.separator + "looseRat.png", "rat01");
        poisonedRat.setInventoryPath("assets" + File.separator + "key.png");

        windowBoxKey = new Collectable(100, 352, "assets" + File.separator + "keyOnFloor.png", "windowBoxKey");
        windowBoxKey.setInventoryPath("assets" + File.separator + "brassKey.png");
        this.roomInhabitants.add(windowBoxKey);

        ratCage.addValidInitCollectable(primaryCursor);
        ratCage.addValidSubCollectable(primaryCursor);
        door.addValidInitCollectable(primaryCursor);
        door.addValidInitCollectable(primaryCursor);

        add(roomInhabitants);
	}

    public void doorInteraction(){
        if (door.getState() == 1) {
            if (poisonedRatInteraction) {
                super.setBackgroundImage("assets" + File.separator + "roombaseAlternate.png");
                roomScare = true;
            }
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
