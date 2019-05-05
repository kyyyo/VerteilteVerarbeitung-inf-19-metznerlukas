package reactive_system;

import org.junit.Test;
import reactive_system.ServerSide.Employee;

import static org.junit.Assert.*;

public class EmployeeTest {


    /***
     * Testing switchState function and getCurrentState function
     */
    @Test
    public void switchStateTest() {
        Employee employee = new Employee("asdf1234");

        employee.switchState(Employee.Input.LINKS);
        assertEquals(Employee.State.IMGANG, employee.getCurrentState());

        employee.switchState(Employee.Input.RECHTS);
        assertEquals(Employee.State.ANWESEND, employee.getCurrentState());
    }
}