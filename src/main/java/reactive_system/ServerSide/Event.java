package reactive_system.ServerSide;

import reactive_system.ServerSide.Employee.Employee;

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
    private Employee.Input Input;

    public Event(String ID, Employee.Input Input) {
        this.ID = ID;
        this.Input = Input;
    }

    public String getID() {
        return ID;
    }


    public Employee.Input getInput() {
        return Input;
    }


}
