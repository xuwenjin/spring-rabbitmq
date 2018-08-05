package com.xwj.simple.publish.fanout;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.xwj.simple.consts.ConfigKey;

public class Receiver2 {

	private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConfigKey.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, ConfigKey.EX_FANOUT);
        
        /*
         * 获取到一个临时队列名称。
         * channel.queueDeclare()：创建一个非持久化、独立、自动删除的队列名称
         * 此队列是临时的，随机的，一旦我们断开消费者，队列会立即被删除
         * 随机队列名，如amq.gen-jzty20brgko-hjmujj0wlg
         */
        String queueName = channel.queueDeclare().getQueue();
        
        /*
         * 将队列跟交换器进行绑定
         * queue：队列名称
         * exchange：交换机名称
         * routingKey：队列跟交换机绑定的键值
         */
        channel.queueBind(queueName, EXCHANGE_NAME, "black");

        System.out.println(" Receiver2 Waiting for messages...");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" Receiver2 --- '" + message + "'");
            }
        };
        //自动确认
        channel.basicConsume(queueName, true, consumer);
    }

}
