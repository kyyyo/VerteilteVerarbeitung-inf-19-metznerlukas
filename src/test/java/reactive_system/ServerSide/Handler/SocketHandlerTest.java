package reactive_system.ServerSide.Handler;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import reactive_system.Enums.State;
import reactive_system.ServerSide.Employee.Employee;
import reactive_system.ServerSide.Employee.EmployeeManagement;
import reactive_system.ServerSide.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static org.junit.Assert.*;

public class SocketHandlerTest {

    @BeforeClass
    public static void startServer() {
            Server.startServer(new String[]{"1337", "32"});
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testData(){
        /**
         * Creating fake Sensor with Sockets
         */
        Socket tmpSocket = null;
        try {
            tmpSocket = new Socket("localhost", 1337);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(tmpSocket != null) {
            DataOutputStream dataOutputStream = null;
            try {
                dataOutputStream = new DataOutputStream(tmpSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }


            /**
             * Sending test json objects
             */
            if(dataOutputStream != null){
                JSONObject obj = new JSONObject();
                obj.put("input", "left");
                obj.put("id", "4444");
                try {
                    dataOutputStream.writeUTF(obj.toString());
                    Thread.sleep(1000);

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

                /**
                 * Employee management list should contain new employee
                 * with id 4444 that was send in the json object
                 * above
                 */
                List<Employee> testEmployees = EmployeeManagement.getInstance().getEmployees();
                for(Employee e : testEmployees){
                    if(e.getID().equalsIgnoreCase("4444")){
                        assertEquals(State.HALLWAY, e.getCurrentState());
                    }
                }

                obj = new JSONObject();
                obj.put("input", "right");
                obj.put("id", "4444");
                try {
                    dataOutputStream.writeUTF(obj.toString());
                    Thread.sleep(1000);

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

                for(Employee e : testEmployees){
                    if(e.getID().equalsIgnoreCase("4444")){
                        assertEquals(State.PRESENT, e.getCurrentState());
                    }
                }
            }
        }



    }
}