package reactive_system.ServerSide;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class that holds a list of all employees and
 * class switch state function on one of them if the notify function is called
 * @author Name: Lukas Metzner ; Matrikel: 884220 ; <Lukas.Metzner@stud.fh-rosenheim.de>
 * @version 1.0
 */
public class EmployeeManagement {

    /**
     * Singleton instance
     */
    private static EmployeeManagement instance;

    /**
     * List of all employees
     */
    private static List<Employee> Employees;


    private EmployeeManagement(){}

    /**
     * Function which makes class a singleton object
     * @return
     */
    public static EmployeeManagement getInstance(){
        if(EmployeeManagement.instance == null){
            EmployeeManagement.instance = new EmployeeManagement();
            Employees = new ArrayList<>();
        }

        return EmployeeManagement.instance;
    }

    /**
     * If employee with event.ID is found, the
     * switch state function is called. If the
     * employee does not exists, a new one is created
     * and the switch state will be called.
     * @param e
     */
    public void notify(Event e){
        Boolean found = false;
        for (Employee emp: Employees ) {
            if(emp.getID().equals(e.getID())){
                emp.switchState(e.getInput());
                found = true;
                break;
            }
        }

        if(!found){
            Employee tmp = new Employee((e.getID()));
            tmp.switchState(e.getInput());
            Employees.add(tmp);
        }
    }

    public List<Employee> getEmployees(){
        return this.Employees;
    }

}
