package Messaging.Server;

import Messaging.Common.QueueObject;
import Messaging.Common.Severity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * Takes QueueObjects out of the driving log and
 * stores them in a list for each vehicle.
 * Typing "calculate" in the console while calculate
 * the whole Km counter for each vehicle.
 *
 * @author Lukas Metzner; sINFlumetz
 */
public class DrivingBook {

    private final static String RCV_EXCHANGE = "drivingLogs";

    private static Map<UUID, List<QueueObject>> drivingBook;
    private static ObjectMapper objectMapper;


    public static void main(String[] args) throws IOException, TimeoutException {
        drivingBook = new HashMap<>();
        objectMapper = new ObjectMapper();

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(RCV_EXCHANGE, "direct");

        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, RCV_EXCHANGE, Severity.NORMAL.name());
        channel.queueBind(queueName, RCV_EXCHANGE, Severity.ALARM.name());

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                QueueObject received = objectMapper.readValue(
                        new String(delivery.getBody(), StandardCharsets.UTF_8),
                        QueueObject.class
                );
                if (!drivingBook.containsKey(received.getId()))
                    drivingBook.put(received.getId(), new ArrayList<>());

                drivingBook.get(received.getId()).add(received);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        channel.basicConsume(queueName, true, deliverCallback, consumterTag -> {
        });
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("calculate"))
                showData();
        }
    }

    private static void showData() {
        drivingBook.forEach((key, value) -> {
            int drivenKm = 0;
            for (QueueObject queueObject : value) {
                drivenKm += queueObject.getDrivenKm();
            }
            System.out.println("Vehicle with id " + key + " drove " + drivenKm + "km");
        });
    }

}
