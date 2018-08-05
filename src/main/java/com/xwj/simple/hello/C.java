package com.xwj.simple.hello;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.xwj.simple.consts.ConfigKey;

public class C {

	private final static String QUEUE_NAME = "hello1";

	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException,
			InterruptedException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ConfigKey.HOST);

		// ��������
		Connection connection = factory.newConnection();

		// ����ͨ��
		Channel channel = connection.createChannel();

		// ����Ҫ��ע�Ķ���
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println("C Waiting for messages");

		// DefaultConsumer��ʵ����Consumer�ӿڣ�ͨ������һ��Ƶ�������߷�����������Ҫ�Ǹ�
		// Ƶ������Ϣ�����Ƶ��������Ϣ���ͻ�ִ�лص�����handleDelivery
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("C Received '" + message + "'");
			}
		};

		// �Զ��ظ�����Ӧ�� -- RabbitMQ�е���Ϣȷ�ϻ���
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}

}
