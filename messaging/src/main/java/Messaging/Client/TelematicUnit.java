package Messaging.Client;

import Messaging.Common.QueueObject;
import Messaging.Common.Severity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * TelematicUnit sends every 10 Seconds a random
 * generated QueueObject to the DrivingData queue.
 * <p>
 * Main method starts to instances of a TelematicUnit.
 *
 * @author Lukas Metzner; sINFlumetz
 */
public class TelematicUnit implements Runnable {

    private final String NORMAL_QUEUE = "DrivingData";
    private final String HOST = "localhost";

    private UUID id;
    private Random random;
    private ObjectMapper objectMapper;
    private ConnectionFactory connectionFactory;

    public TelematicUnit() {
        this.id = UUID.randomUUID();
        this.random = new Random();
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setHost(HOST);
        this.objectMapper = new ObjectMapper();
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            executorService.submit(new TelematicUnit());
        }
    }

    public void run() {
        try (Connection connection = connectionFactory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(NORMAL_QUEUE, false, false, false, null);

            while (true) {
                QueueObject queueObject = generateRandomMessage();
                channel.basicPublish(
                        "",
                        NORMAL_QUEUE,
                        null,
                        objectMapper.writeValueAsBytes(queueObject)
                );
                System.out.println("Published: " + queueObject.getId());
                Thread.sleep(10000);
            }
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Generates random data for a QueueObject
     *
     * @return
     */
    public QueueObject generateRandomMessage() {

        DecimalFormat df = new DecimalFormat("###.######");
        String longitude = df.format(random.nextDouble() * 100);
        String latitude = df.format(random.nextDouble() * 100);
        Severity severity;
        float randSeverity = random.nextFloat();
        if (randSeverity < 0.7f) {
            severity = Severity.NORMAL;
        } else {
            severity = Severity.ALARM;
        }

        int rndKm = Math.round(random.nextFloat() * 100);

        return new QueueObject(
                id,
                "GPSlong:" + longitude + "GPSlat:" + latitude,
                rndKm,
                LocalTime.now().toString(),
                severity
        );
    }
}
