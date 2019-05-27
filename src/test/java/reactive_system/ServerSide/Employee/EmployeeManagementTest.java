package reactive_system.ServerSide.Employee;

import org.junit.Before;
import org.junit.Test;
import reactive_system.Enums.Input;
import reactive_system.Enums.State;
import reactive_system.ServerSide.Event;

import static org.junit.Assert.*;

public class EmployeeManagementTest {

    private EmployeeManagement employeeManagement;

    @Before
    public void setupTest(){
        employeeManagement = EmployeeManagement.getInstance();
    }

    @Test
    public void notifyTest() {
        Event e = new Event("asdf1234", Input.LEFT);
        employeeManagement.notify(e);
        Boolean found = false;

        for(Employee emp : employeeManagement.getEmployees()){
            if(emp.getID().equals(e.getID())){
                found = true;
                break;
            }
        }

        assertEquals(true, found);
        State state = employeeManagement.getEmployees().get(0).getCurrentState();
        assertEquals(State.HALLWAY, state);
    }
}