package reactive_system.ServerSide.Employee;

import reactive_system.ServerSide.Event;
import reactive_system.Enums.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Singleton class that holds a list of all employees and
 * class switch state function on one of them if the notify function is called
 *
 * @author Name: Lukas Metzner ; Matrikel: 884220 ; <Lukas.Metzner@stud.fh-rosenheim.de>
 * @version 1.0
 */
public final class EmployeeManagement {

    /**
     * Singleton instance
     */
    private static final EmployeeManagement instance = new EmployeeManagement();

    /**
     * List of all employees
     */
    private static List<Employee> Employees;

    /**
     * Logger variables
     */
    private static Logger eventLog;
    private static Logger employeeManagementLog;


    /**
     * Setup Logger
     * employeeManagementLog writes in file EmployeeManagementLog.log
     * eventLog writes in file EventLog.log
     */
    private EmployeeManagement() {
        Employees = new ArrayList<>();

        employeeManagementLog = Logger.getLogger("employeeManagementLog");
        eventLog = Logger.getLogger("eventLog");

        FileHandler employeeManagementLogFH = null;
        FileHandler eventLogFH = null;

        try {
            employeeManagementLogFH = new FileHandler("EmployeeManagementLog.log");
        } catch (IOException e) {
            employeeManagementLog.severe(e.getMessage());
        }

        try {
            eventLogFH = new FileHandler("EventLog.log");
        } catch (IOException e) {
            eventLog.severe(e.getMessage());
        }

        employeeManagementLogFH.setFormatter(new SimpleFormatter());
        employeeManagementLog.addHandler(employeeManagementLogFH);
        employeeManagementLog.setLevel(Level.ALL);

        eventLogFH.setFormatter(new SimpleFormatter());
        eventLog.addHandler(eventLogFH);
        eventLog.setLevel(Level.ALL);
    }

    /**
     * Function which makes class a singleton object
     *
     * @return
     */
    public static EmployeeManagement getInstance() {
        return EmployeeManagement.instance;
    }

    /**
     * If employee with event.ID is found, the
     * switch state function is called. If the
     * employee does not exists, a new one is created
     * and the switch state will be called. If the employee
     * is in the error state he will be reseted.
     *
     * @param e
     */
    public void notify(Event e) {
        Boolean found = false;
        for (Employee emp : Employees) {
            if (emp.getID().equals(e.getID())) {
                found = true;
                if (emp.getCurrentState() == State.ERROR) {
                    emp.setCurrentState(State.AWAY);
                    employeeManagementLog.severe("Employee with ID " + e.getID() + " was in a error state and got reseted");
                    break;
                }
                emp.switchState(e.getInput());
                eventLog.info("Event from Employee " + e.getID() + " dispatched");
                break;
            }
        }

        if (!found) {
            Employee tmp = new Employee((e.getID()));
            Employees.add(tmp);
            employeeManagementLog.info("Employee with ID: " + tmp.getID() + " has been added to database");
            tmp.switchState(e.getInput());
            eventLog.info("Event from Employee " + e.getID() + " dispatched");
        }
    }

    public List<Employee> getEmployees() {
        return Employees;
    }

}
