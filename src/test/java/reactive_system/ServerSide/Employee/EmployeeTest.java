package reactive_system.ServerSide.Employee;

import org.junit.Before;
import org.junit.Test;

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
        testEmployee.setCurrentState(Employee.State.AWAY);
        testEmployee.switchState(Employee.Input.LEFT);
        assertEquals(Employee.State.HALLWAY, testEmployee.getCurrentState());
    }

    @Test
    public void fromHallwayRight(){
        testEmployee.setCurrentState(Employee.State.HALLWAY);
        testEmployee.switchState(Employee.Input.RIGHT);
        assertEquals(Employee.State.PRESENT, testEmployee.getCurrentState());
    }

    @Test
    public void fromPresentRight(){
        testEmployee.setCurrentState(Employee.State.PRESENT);
        testEmployee.switchState(Employee.Input.RIGHT);
        assertEquals(Employee.State.HALLWAY, testEmployee.getCurrentState());
    }

    @Test
    public void fromHallwayLeft(){
        testEmployee.setCurrentState(Employee.State.HALLWAY);
        testEmployee.switchState(Employee.Input.LEFT);
        assertEquals(Employee.State.AWAY, testEmployee.getCurrentState());
    }

    @Test
    public void fromAwayRight(){
        testEmployee.setCurrentState(Employee.State.AWAY);
        testEmployee.switchState(Employee.Input.RIGHT);
        assertEquals(Employee.State.ERROR, testEmployee.getCurrentState());
    }

    @Test
    public void fromPresentLeft(){
        testEmployee.setCurrentState(Employee.State.PRESENT);
        testEmployee.switchState(Employee.Input.LEFT);
        assertEquals(Employee.State.ERROR, testEmployee.getCurrentState());
    }


}