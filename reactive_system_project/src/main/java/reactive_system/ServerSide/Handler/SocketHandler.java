package reactive_system.ServerSide.Handler;

import reactive_system.ServerSide.Event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * SocketHandler makes new ClientHandler thread
 * for each client that connects to the server using
 * the executor framework.
 *
 * @author Name: Lukas Metzner ; Matrikel: 884220 ; <Lukas.Metzner@stud.fh-rosenheim.de>
 */
public class SocketHandler implements Runnable {

    private final BlockingQueue<Event> blockingQueue;
    private final Logger serverLog;
    private final int port;
    private final int fixedThreadPoolNum;

    public SocketHandler(BlockingQueue<Event> blockingQueue, Logger serverLog, int port, int fixedThreadPoolNum) {
        this.blockingQueue = blockingQueue;
        this.serverLog = serverLog;
        this.port = port;
        this.fixedThreadPoolNum = fixedThreadPoolNum;
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(this.fixedThreadPoolNum);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            serverLog.severe("Failed creating server.");
            serverLog.severe(e.getMessage());
            System.exit(1);
        }

        /**

         */
        while (true) {
            Socket tmpSocket = null;
            try {
                tmpSocket = serverSocket.accept();
                if (tmpSocket != null) {
                    DataInputStream inputStream = new DataInputStream(tmpSocket.getInputStream());
                    DataOutputStream outputStream = new DataOutputStream(tmpSocket.getOutputStream());

                    Runnable clientThread = new ClientHandler(blockingQueue, tmpSocket, inputStream, outputStream);
                    executor.execute(clientThread);
                }

            } catch (IOException e) {
                serverLog.severe(e.getMessage());
            }

        }

    }
}
