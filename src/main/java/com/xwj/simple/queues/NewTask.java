package com.xwj.simple.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.xwj.simple.consts.ConfigKey;

public class NewTask {

	private final static String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] argv) throws Exception {
		// �������ӹ���
		ConnectionFactory factory = new ConnectionFactory();

		// ����RabbitMQ��ַ
		factory.setHost(ConfigKey.HOST);

		// ����һ���µ�����
		Connection connection = factory.newConnection();

		// ����һ��Ƶ��
		Channel channel = connection.createChannel();

		// ����һ������
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

		// �ַ���Ϣ
		for (int i = 0; i < 6; i++) {
			String message = "Hello World--- " + i;
			channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println(" P Sent '" + message + "'");
		}

		// �ر�Ƶ��������
		channel.close();
		connection.close();
	}

}
