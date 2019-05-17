package reactive_system;

import org.junit.Test;
import reactive_system.ServerSide.Employee.Employee;

import static org.junit.Assert.*;

public class EmployeeTest {


    /***
     * Testing switchState function and getCurrentState function
     * One "round" ABWESEND -> IMGANG -> ANWESEND -> IMGANG -> ABWESEND -> FEHLER
     */
    @Test
    public void switchStateTest() {
        Employee employee = new Employee("asdf1234");

        assertEquals(Employee.State.ABWESEND, employee.getCurrentState());

        employee.switchState(Employee.Input.LINKS);
        assertEquals(Employee.State.IMGANG, employee.getCurrentState());

        employee.switchState(Employee.Input.RECHTS);
        assertEquals(Employee.State.ANWESEND, employee.getCurrentState());

        employee.switchState(Employee.Input.RECHTS);
        assertEquals(Employee.State.IMGANG, employee.getCurrentState());

        employee.switchState(Employee.Input.LINKS);
        assertEquals(Employee.State.ABWESEND, employee.getCurrentState());

        employee.switchState(Employee.Input.RECHTS);
        assertEquals(Employee.State.FEHLER, employee.getCurrentState());

    }
}