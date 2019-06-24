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
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class TelematicsUnit {

    private static String Id;
    private static Random random;
    private static ObjectMapper objectMapper;
    private static ConnectionFactory connectionFactory;
    private final static String NORMAL_QUEUE = "DrivingData";

    public static void main(String[] args) throws InterruptedException {
        Id = UUID.randomUUID().toString();
        random = new Random();
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        objectMapper = new ObjectMapper();
        Channel channel = null;

        try (Connection connection = connectionFactory.newConnection()) {
            channel = connection.createChannel();
            channel.queueDeclare(NORMAL_QUEUE, false, false, false, null);

            if (channel == null){
                System.out.println("Could not create channel! Exiting...");
                System.exit(1);
            }

            while (true) {
                QueueObject queueObject = generateRandomMessage();
                channel.basicPublish(
                        "",
                        NORMAL_QUEUE,
                        null,
                        objectMapper.writeValueAsBytes(queueObject)
                );
                System.out.println("Published: " + queueObject.getId());
                Thread.sleep(1000);
            }
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static QueueObject generateRandomMessage() {

        DecimalFormat df = new DecimalFormat("###.######");
        String longitude = df.format(random.nextDouble() * 100);
        String latitude = df.format(random.nextDouble() * 100);
        Severity severity;
        float randSeverity = random.nextFloat();
        if(randSeverity < 0.7f){
            severity = Severity.NORMAL;
        } else {
            severity = Severity.ALARM;
        }

        int rndKm = Math.round(random.nextFloat() * 100);

        QueueObject message = new QueueObject(
                Id,
                "GPSlong:" + longitude + "GPSlat:" + latitude,
                rndKm,
                LocalTime.now().toString(),
                severity
        );
        return message;
    }
}
