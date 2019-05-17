package reactive_system.ClientSide;

import org.json.JSONObject;
import reactive_system.ServerSide.Employee.Employee;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Client class represents one client that
 * sends randomly generated data to the server. Can be initialized
 * with left or right
 *
 * @author Name: Lukas Metzner ; Matrikel: 884220 ; <Lukas.Metzner@stud.fh-rosenheim.de>
 */
public class Sensor {

    private static InetAddress ip;
    private static int port;
    private static Employee.Input sensorType;
    private static Logger clientLog;

    public static void main(String[] args) {


        try {
            setupLogger();
        } catch (IOException e) {
            System.out.println("Failed to setup logger. Exiting!");
            System.exit(1);
        }
        setupFromArgs(args);

        String[] randomIDs = {"1234", "2341", "3412", "4123", "2143", "3321", "4444"};
        Random randomGen = new Random();
        Boolean exit = false;


        while (!exit) {
            try {
                /**
                 * Setup connection to server
                 */
                Socket socket = new Socket(ip, port);

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                /**
                 * Send random data all 5 seconds
                 */
                while (!exit) {
                    JSONObject testObj = new JSONObject();
                    testObj.put("id", randomIDs[randomGen.nextInt(7)]);
                    testObj.put("input", sensorType);
                    outputStream.writeUTF(testObj.toString());
                    outputStream.flush();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                clientLog.severe(e.getMessage());
            }
        }

    }

    /**
     * Method to setup logger
     * @throws IOException
     */
    private static void setupLogger() throws IOException {
        clientLog = Logger.getLogger("clientLog");
        FileHandler serverLogFH = new FileHandler("ClientLog.log");
        serverLogFH.setFormatter(new SimpleFormatter());
        clientLog.addHandler(serverLogFH);
        clientLog.setLevel(Level.ALL);
    }

    /**
     * Prases arguments:
     * First Arg: SensorType either links or right
     * Second Arg: IpAddress parsed to InetAddress
     * Third Arg: Port parsed to Integer
     * @param args
     */
    private static void setupFromArgs(String[] args) {
        if(args == null)
            System.out.println("Arguments are null: SensorType IPAddress Port");
        if(args.length < 2)
            System.out.println("Not enough arguments: SensorType IPAddress Port");

        try {
            if (args[0].equalsIgnoreCase("links")) {
                sensorType = Employee.Input.LINKS;
            } else if (args[0].equalsIgnoreCase("rechts")) {
                sensorType = Employee.Input.RECHTS;
            } else {
                System.out.println("Wrong type of sensor. Exiting!");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("No argument specified");
            System.exit(-1);
        }

        try {
            ip = InetAddress.getByName(args[1]);
        } catch (Exception e) {
        }
        try {
            port = Integer.parseInt(args[2]);
        } catch (Exception e) {
        }

    }
}
