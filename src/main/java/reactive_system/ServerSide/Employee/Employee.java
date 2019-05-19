package reactive_system.ServerSide.Employee;

/**
 * The employee class contains a Finite-state machine
 * which decides the current state of the employee.
 * The class also contains the ID for the employee.
 *
 * @author Name: Lukas Metzner ; Matrikel: 884220 ; <Lukas.Metzner@stud.fh-rosenheim.de>
 * @version 1.0
 */
public class Employee {

    /**
     * A 2 dimensional array which contains a state table
     */
    private static State[][] stateTable
            = {{State.HALLWAY, State.AWAY, State.ERROR},
            {State.ERROR, State.PRESENT, State.HALLWAY}};

    /**
     * Current state of a employee
     */
    private State currentState;

    /**
     * The ID of a employee
     */
    private String ID;


    /**
     * Sets ID of a employee
     *
     * @param ID
     */
    public Employee(String ID) {
        this.currentState = State.AWAY;
        this.ID = ID;
    }

    /**
     * changes currentState when employee takes left or right path
     *
     * @param input
     */
    public void switchState(Input input) {
        this.currentState = stateTable[input.ordinal()][this.currentState.ordinal()];
    }

    /**
     * Returns current state of a employee
     *
     * @return
     */
    public State getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(State newState) {
        this.currentState = newState;
    }

    /**
     * Returns the ID of a employee
     *
     * @return
     */
    public String getID() {
        return this.ID;
    }

    /**
     * A Enum containing all possible states
     */
    public enum State {AWAY, HALLWAY, PRESENT, ERROR}

    /**
     * A Enum containing all possible inputs
     */
    public enum Input {LEFT, RIGHT}
}
