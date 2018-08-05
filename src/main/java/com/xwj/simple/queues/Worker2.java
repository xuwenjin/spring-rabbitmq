package com.xwj.simple.queues;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.xwj.simple.consts.ConfigKey;

public class Worker2 {

	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ConfigKey.HOST);
		final Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		System.out.println("Worker2 Waiting for messages");

		// 同一时刻服务器只会发一条消息给消费者(能者多劳模式)  
		channel.basicQos(1);

		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");

				System.out.println("Worker2 Received '" + message + "'");
				try {
					doWork(message);
				} finally {
					System.out.println("Worker2 Done");
					// 消息处理完成确认
					// deliveryTag 该消息的index
					// multiple：是否批量 true:将一次性ack所有小于deliveryTag的消息。
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		// 消息消费完成确认
		// autoAck 是否自动ack，如果不自动ack，需要使用channel.ack、channel.nack、channel.basicReject进行消息应答
		channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
	}

	private static void doWork(String task) {
		try {
			Thread.sleep(1000); // 暂停1秒钟
		} catch (InterruptedException _ignored) {
			Thread.currentThread().interrupt();
		}
	}

}
