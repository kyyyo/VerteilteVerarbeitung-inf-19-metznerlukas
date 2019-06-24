package Messaging.Server;

import Messaging.Common.QueueObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class IncomeFilter {

    private final static String RCV_QUEUE = "DrivingData";
    private final static String SND_EXCHANGE = "drivingLogs";
    private static ConnectionFactory connectionFactory;
    private static Channel channelRecv;
    private static Channel channelSend;
    private static ObjectMapper objectMapper;

    public static void main(String[] args) throws InterruptedException, IOException, TimeoutException {
        connectionFactory = new ConnectionFactory();
        objectMapper = new ObjectMapper();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        channelRecv = connection.createChannel();
        channelRecv.queueDeclare(RCV_QUEUE, false, false, false, null);
        channelRecv.addShutdownListener(e -> e.printStackTrace());

        channelSend = connection.createChannel();
        channelSend.exchangeDeclare(SND_EXCHANGE, "direct");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try{
                QueueObject received = objectMapper.readValue(
                        new String(delivery.getBody(), "UTF-8"),
                        QueueObject.class
                );

                System.out.println("Received: " + received.getId());

                /**
                 * Possible Filter
                 */

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

        channelRecv.basicConsume(RCV_QUEUE, true, deliverCallback, consumerTag -> {});
    }
}
