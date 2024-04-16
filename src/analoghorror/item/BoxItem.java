package analoghorror.item;

public class BoxItem extends Item{  
    // TODO: Make a new RoomItem class that includes two paths for the constructor bc KeyItems only need one, probably? Maybe a "glowing" sprite?
    String defaultImagePath;
    String modifiedImagePath;

    public BoxItem(double x, double y, String defaultPath, String modifiedPath) {
        super(x, y, defaultPath);
        defaultImagePath = defaultPath;
        modifiedImagePath = modifiedPath;
    }
    
    @Override
    public void interaction(Item interactable){
        if (defaultState) {
            setImagePath(modifiedImagePath);
            defaultState = false;
        }
        else if (!defaultState) {
            setImagePath(defaultImagePath);
            defaultState = true;
        }
    }
}
