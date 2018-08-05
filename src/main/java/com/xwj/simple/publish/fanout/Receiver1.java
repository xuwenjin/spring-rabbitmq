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

/**
 * ������ʹ��fanoutģʽ
 * 
 * @author xuwenjin
 */
public class Receiver1 {

	private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConfigKey.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, ConfigKey.EX_FANOUT);
        
        //��ȡ��һ������������ơ���amq.gen-jzty20brgko-hjmujj0wlg
        String queueName = channel.queueDeclare().getQueue();
        
        //�����и����������а�
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" Receiver1 Waiting for messages...");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" Receiver1 --- '" + message + "'");
            }
        };
        //�Զ�ȷ��
        channel.basicConsume(queueName, true, consumer);
    }

}
