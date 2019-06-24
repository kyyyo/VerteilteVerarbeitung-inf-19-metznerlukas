package Messaging.Server;

import Messaging.Common.QueueObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * Takes every object out of the DrivingData queue
 * and pushes it either in the NORMAL or the ALARM queue
 *
 * @author Lukas Metzner; sINFlumetz
 */
public class IncomeFilter {

    private final static String RCV_QUEUE = "DrivingData";
    private final static String SND_EXCHANGE = "drivingLogs";
    private final static String HOST = "localhost";

    private static Channel channelSend;
    private static ObjectMapper objectMapper;

    public static void main(String[] args) throws IOException, TimeoutException {
        objectMapper = new ObjectMapper();

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);

        Connection connection = connectionFactory.newConnection();
        Channel channelRecv = connection.createChannel();

        channelRecv.queueDeclare(RCV_QUEUE, false, false, false, null);
        channelRecv.addShutdownListener(Throwable::printStackTrace);

        channelSend = connection.createChannel();
        channelSend.exchangeDeclare(SND_EXCHANGE, "direct");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                QueueObject received = objectMapper.readValue(
                        new String(delivery.getBody(), StandardCharsets.UTF_8),
                        QueueObject.class
                );

                System.out.println("Received: " + received.getId());

                //TODO => FILTER
                channelSend.basicPublish(
                        SND_EXCHANGE,
                        received.getMessageIsSevere().name(),
                        null,
                        objectMapper.writeValueAsBytes(received)
                );

                System.out.println("Published: " + received.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        channelRecv.basicConsume(RCV_QUEUE, true, deliverCallback, consumerTag -> {
        });
    }
}
