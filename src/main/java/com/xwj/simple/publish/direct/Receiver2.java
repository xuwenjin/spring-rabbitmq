package com.xwj.simple.publish.direct;

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
 * ������ʹ��directģʽ
 * 
 * @author xuwenjin
 */
public class Receiver2 {

	// ����������
	private static final String EXCHANGE_NAME = "direct_logs";

	// ·�ɹؼ���
	private static final String[] routingKeys = new String[] { "error" };

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ConfigKey.HOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// ����direct���͵�exchange,
		channel.exchangeDeclare(EXCHANGE_NAME, ConfigKey.EX_DIRECT);
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
