package reactive_system.ServerSide.Handler;

import reactive_system.ServerSide.Employee.EmployeeManagement;
import reactive_system.ServerSide.Event;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * The EventHandler takes events from
 * the blocking queue and notifies the
 * employee management
 *
 * @author Name: Lukas Metzner ; Matrikel: 884220 ; <Lukas.Metzner@stud.fh-rosenheim.de>
 */
public class EventHandler implements Runnable {

    private final Logger serverLog;
    private final BlockingQueue<Event> blockingQueue;

    public EventHandler(BlockingQueue<Event> blockingQueue, Logger serverLog) {
        this.blockingQueue = blockingQueue;
        this.serverLog = serverLog;
    }

    @Override
    public void run() {
        while (true) {
            if (!blockingQueue.isEmpty()) {
                try {
                    EmployeeManagement.getInstance().notify(blockingQueue.take());
                } catch (InterruptedException e) {
                    serverLog.severe(e.getMessage());
                }
            }
        }
    }
}
