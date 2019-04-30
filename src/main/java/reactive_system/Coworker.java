package reactive_system;

/**
 * The coworker class contains a Finite-state machine
 * which decides the current state of the coworker.
 * The class also contains the ID for the employee.
 *
 * @author Name: Lukas Metzner ; Matrikel: 884220 ; <Lukas.Metzner@stud.fh-rosenheim.de>
 * @version 1.0
 */
public class Coworker {

    /**
     * A Enum containing all possible states
     */
    public enum State {ABWESEND, IMGANG, ANWESEND, FEHLER};

    /**
     * A Enum containing all possible inputs
     */
    public enum Input {LINKS, RECHTS};

    /**
     * A 2 dimensional array which contains a state table
     */
    private static State stateTable [][]
            = { {State.IMGANG, State.ABWESEND, State.FEHLER},
            {State.FEHLER, State.ANWESEND, State.IMGANG}};


    /**
     * Current state of a coworker
     */
    private State currentState;

    /**
     * The ID of a coworker
     */
    private String ID;



    /**
     * Sets ID of a coworker
     *
     * @param ID
     */
    public Coworker(String ID){
        this.currentState = State.ABWESEND;
        this.ID = ID;
    }

    /**
     * changes currentState when coworker takes left or right path
     * @param input
     */
    public void switchState(Input input){
        this.currentState = stateTable[input.ordinal()][this.currentState.ordinal()];
    }

    /**
     * Returns current state of a coworker
     * @return
     */
    public State getCurrentState(){
        return this.currentState;
    }

    /**
     * Returns the ID of a coworker
     * @return
     */
    public String getID() {
        return this.ID;
    }
}
