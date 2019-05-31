package reactive_system.ServerSide;

import reactive_system.Enums.Input;

/**
 * Event class which contains the ID of the employee
 * that caused the event and the input which means
 * left or right
 *
 * @author Name: Lukas Metzner ; Matrikel: 884220 ; <Lukas.Metzner@stud.fh-rosenheim.de>
 * @version 1.0
 */
public class Event {

    private String ID;
    private Input Input;

    public Event(String ID, Input Input) {
        this.ID = ID;
        this.Input = Input;
    }

    public String getID() {
        return ID;
    }


    public Input getInput() {
        return Input;
    }


}
