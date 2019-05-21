package reactive_system.ServerSide.Employee;

import org.junit.Before;
import org.junit.Test;
import reactive_system.Enums.Input;
import reactive_system.Enums.State;

import static org.junit.Assert.assertEquals;

public class EmployeeTest {

    private Employee testEmployee;

    @Before
    public void setupTest() {
        testEmployee = new Employee("1234");
    }

    /**
     * Testing all switch case scenarios
     * setCurrentState is used because JUnit does not
     * guaranteed the correct order of execution
     */
    @Test
    public void fromAwayLeft() {
        testEmployee.setCurrentState(State.AWAY);
        testEmployee.switchState(Input.LEFT);
        assertEquals(State.HALLWAY, testEmployee.getCurrentState());
    }

    @Test
    public void fromHallwayRight(){
        testEmployee.setCurrentState(State.HALLWAY);
        testEmployee.switchState(Input.RIGHT);
        assertEquals(State.PRESENT, testEmployee.getCurrentState());
    }

    @Test
    public void fromPresentRight(){
        testEmployee.setCurrentState(State.PRESENT);
        testEmployee.switchState(Input.RIGHT);
        assertEquals(State.HALLWAY, testEmployee.getCurrentState());
    }

    @Test
    public void fromHallwayLeft(){
        testEmployee.setCurrentState(State.HALLWAY);
        testEmployee.switchState(Input.LEFT);
        assertEquals(State.AWAY, testEmployee.getCurrentState());
    }

    @Test
    public void fromAwayRight(){
        testEmployee.setCurrentState(State.AWAY);
        testEmployee.switchState(Input.RIGHT);
        assertEquals(State.ERROR, testEmployee.getCurrentState());
    }

    @Test
    public void fromPresentLeft(){
        testEmployee.setCurrentState(State.PRESENT);
        testEmployee.switchState(Input.LEFT);
        assertEquals(State.ERROR, testEmployee.getCurrentState());
    }


}