package Messaging.Server;

import Messaging.Common.QueueObject;
import Messaging.Common.Severity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class DrivingBook {

    private final static String RCV_EXCHANGE = "drivingLogs";

    private static Map<String, List<QueueObject>> drivingBook;
    private static ObjectMapper objectMapper;
    private static ConnectionFactory connectionFactory;
    private static Connection connection;
    private static Channel channel;
    private static String queueName;


    public static void main(String[] args) throws IOException, TimeoutException {
        drivingBook = new HashMap<>();
        objectMapper = new ObjectMapper();
        connectionFactory = new ConnectionFactory();

        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(RCV_EXCHANGE, "direct");
        queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, RCV_EXCHANGE, Severity.NORMAL.name());
        channel.queueBind(queueName, RCV_EXCHANGE, Severity.ALARM.name());

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                QueueObject received = objectMapper.readValue(
                        new String(delivery.getBody(), "UTF-8"),
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
        while(true){
            String line = scanner.nextLine();
            if(line.equals("calculate"))
                showData();
        }
    }

    private static void showData() {
        drivingBook.entrySet().forEach(queueObjectList -> {
            int drivenKm = 0;
            for (QueueObject queueObject : queueObjectList.getValue()) {
                drivenKm += queueObject.getDrivenKm();
            }
            System.out.println("Vehicle with id " + queueObjectList.getKey() + " drove " + drivenKm + "km");
        });
    }

}
