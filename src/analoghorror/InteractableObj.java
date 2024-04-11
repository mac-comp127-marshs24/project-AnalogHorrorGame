package analoghorror;

public class InteractableObj {

    public InteractableObj(KeyObj keyObject){

    }
    public void interact(Item heldObject){
    //object.onclick
    //checks for item currently being used in inventory
    //item held != objective item --> nothing happens
    //item held == objective item --> event trigger
        if (heldObject == this.keyObject){
            //triggers event associated with object?
            

        }

    }
    
}
