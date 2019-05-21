package reactive_system.ServerSide;

import reactive_system.ServerSide.Handler.EventHandler;
import reactive_system.ServerSide.Handler.SocketHandler;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Server class which contains the main function for the server.
 * The main function starts two threads, one of them takes events from the blocking queue
 * and notifies the employee management system while the other one takes new client connections
 * and starts seperate threads for each new client.
 *
 * @author Name: Lukas Metzner ; Matrikel: 884220 ; <Lukas.Metzner@stud.fh-rosenheim.de>
 */
public class Server {

    private static Logger serverLog;
    private static BlockingQueue<Event> blockingQueue;
    private static int port;
    private static int fixedThreadPoolNum;

    public static void main(String[] args) {

        startServer(args);
    }

    public static void startServer(String[] args){
        blockingQueue = new LinkedBlockingQueue<>();
        setupFromArgs(args);
        try {
            setupLogger();
        } catch (IOException e) {
            System.out.println("Failed to setup logger. Exiting!");
            System.exit(1);
        }

        Thread EventHandling = new Thread(new EventHandler(blockingQueue, serverLog));
        Thread SocketHandling = new Thread(new SocketHandler(blockingQueue, serverLog, port, fixedThreadPoolNum));

        EventHandling.start();
        SocketHandling.start();
    }

    /**
     * Creating logging for the server.
     * Logs stored in ServerLog.log
     */
    private static void setupLogger() throws IOException {
        serverLog = Logger.getLogger("serverLog");
        FileHandler serverLogFH = new FileHandler("ServerLog.log");
        serverLogFH.setFormatter(new SimpleFormatter());
        serverLog.addHandler(serverLogFH);
        serverLog.setLevel(Level.ALL);
    }

    /**
     * Prases arguments:
     * First Arg: Port number
     * Second Arg: Number that determents the max size of thread pool
     *
     * @param args
     */
    private static void setupFromArgs(String[] args) {
        if (args == null)
            System.out.println("Arguments are null: Port fixedThreadPoolNumber");
        if (args.length < 1)
            System.out.println("Not enough arguments: Port fixedThreadPoolNumber");

        try {
            port = Integer.parseInt(args[0]);
            fixedThreadPoolNum = Integer.parseInt(args[1]);
        } catch (Exception e) {
            serverLog.severe(e.getMessage());
            System.exit(1);
        }

    }
}
