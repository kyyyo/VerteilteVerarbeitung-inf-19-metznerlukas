package reactive_system.ClientSide;

import reactive_system.Enums.Input;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
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
    private static Input sensorType;
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
        Boolean exit = false;


        while (!exit) {
            try {
                /**
                 * Setup connection to server
                 */
                Socket socket = new Socket(ip, port);
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                RandomDataGenerator dataGenerator = new RandomDataGenerator(outputStream, sensorType, randomIDs);

                /**
                 * Send random data all 5 seconds
                 */
                while (!exit) {
                    dataGenerator.sendNewData();
                }
            } catch (Exception e) {
                clientLog.severe(e.getMessage());
            }
        }

    }

    /**
     * Method to setup logger
     *
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
     *
     * @param args
     */
    private static void setupFromArgs(String[] args) {
        if (args == null)
            System.out.println("Arguments are null: SensorType IPAddress Port");
        if (args.length < 2)
            System.out.println("Not enough arguments: SensorType IPAddress Port");

        try {
            if (args[0].equalsIgnoreCase("left")) {
                sensorType = Input.LEFT;
            } else if (args[0].equalsIgnoreCase("right")) {
                sensorType = Input.RIGHT;
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
