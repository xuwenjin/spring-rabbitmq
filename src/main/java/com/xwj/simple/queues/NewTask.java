package com.xwj.simple.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.xwj.simple.consts.ConfigKey;

public class NewTask {

	private final static String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] argv) throws Exception {
		// 创建连接工厂
		ConnectionFactory factory = new ConnectionFactory();

		// 设置RabbitMQ地址
		factory.setHost(ConfigKey.HOST);

		// 创建一个新的连接
		Connection connection = factory.newConnection();

		// 创建一个频道
		Channel channel = connection.createChannel();

		// 声明一个队列
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

		// 分发消息
		for (int i = 0; i < 6; i++) {
			String message = "Hello World--- " + i;
			channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println(" P Sent '" + message + "'");
		}

		// 关闭频道和连接
		channel.close();
		connection.close();
	}

}
