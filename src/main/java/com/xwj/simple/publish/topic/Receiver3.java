package com.xwj.simple.publish.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.xwj.simple.consts.ConfigKey;

/**
 * ������ʹ��topicģʽ
 * 
 * @author xuwenjin
 */
public class Receiver3 {

	// ����������
	private static final String EXCHANGE_NAME = "topic_logs";

	// ·�ɹؼ���
	private static final String[] routingKeys = new String[] { "#." };

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ConfigKey.HOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// ����direct���͵�exchange,
		channel.exchangeDeclare(EXCHANGE_NAME, ConfigKey.EX_TOPIC);
		// ��ȡ��ʱ��������
		String queueName = channel.queueDeclare().getQueue();

		// ����·�ɹؼ��ֽ��ж��ذ�
		for (String key : routingKeys) {
			channel.queueBind(queueName, EXCHANGE_NAME, key);
			System.out.println("Receiver2 exchange:" + EXCHANGE_NAME + ", queue:" + queueName + ", key:" + key);
		}
		System.out.println("Receiver2 waiting for message...");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("Received2 '" + envelope.getRoutingKey() + "':'" + message + "'");
			};
		};
		// �Զ�ȷ����Ϣ
		channel.basicConsume(queueName, true, consumer);
	}

}
