package reactive_system.ServerSide.Handler;

import org.json.JSONObject;
import reactive_system.Enums.Input;
import reactive_system.ServerSide.Event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * Instances of the ClientHandler class are created on every new client connection.
 * The incoming events are formatted as json strings. The run method parses these
 * json strings to event objects and puts them on the blocking queue.
 *
 * @author Name: Lukas Metzner ; Matrikel: 884220 ; <Lukas.Metzner@stud.fh-rosenheim.de>
 */
public class ClientHandler implements Runnable {

    private BlockingQueue blockingQueue;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Logger serverLog = Logger.getLogger("serverLog");

    /**
     * The constructor takes the global blocking queue as parameter
     * and all import variables used in the socket connection
     *
     * @param blockingQueue
     * @param socket
     * @param inputStream
     * @param outputStream
     */
    public ClientHandler(BlockingQueue blockingQueue, Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        this.blockingQueue = blockingQueue;
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                JSONObject jsonEvent = new JSONObject(this.inputStream.readUTF());
                /**
                 * Left or right string to enum conversion
                 */
                Event event = null;
                if (jsonEvent.getString("input").equalsIgnoreCase("left"))
                    event = new Event(jsonEvent.getString("id"), Input.LEFT);
                else if (jsonEvent.getString("input").equalsIgnoreCase("right"))
                    event = new Event(jsonEvent.getString("id"), Input.RIGHT);

                /**
                 * If the event was created successfully it will be put on the blocking queue, logged
                 * and a response will be send to the client.
                 * Else the error is logged and a response will be send to the client.
                 */
                if (event != null) {
                    blockingQueue.put(event);
                    serverLog.info("Event from Employee with id " + event.getID() + " was put on the blocking queue");
                    this.outputStream.writeUTF("EventHandler Successfull");
                    this.outputStream.flush();
                } else {
                    serverLog.info("Incoming message had wrong formatting");
                    this.outputStream.writeUTF("Message had wrong formatting");
                    this.outputStream.flush();
                }

            } catch (Exception e) {
                serverLog.info(e.getMessage());
            }
        }

    }
}
