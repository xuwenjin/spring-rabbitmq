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

public class Worker1 {

	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ConfigKey.HOST);
		final Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		System.out.println("Worker1 Waiting for messages");

		// 同一时刻服务器只会发一条消息给消费者(能者多劳模式)  
		channel.basicQos(1);

		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");

				System.out.println("Worker1 Received '" + message + "'");
				try {
					doWork(message);
				} finally {
					System.out.println("Worker1 Done");
					// 消息处理完成，手动确认提交
					// deliveryTag 该消息的index
					// multiple：是否批量 true:将一次性ack所有小于deliveryTag的消息。
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};

		/*消息消费完成确认
		 * autoAck 是否自动确认 true自动确认 false手动确认
		 * 模式1：自动确认 只要消息从队列中获取，无论消费者获取到消息后是否成功消息，都认为是消息已经成功消费。 
		 * 模式2：手动确认
		 * 消费者从队列中获取消息后，服务器会将该消息标记为不可用状态，等待消费者的反馈，如果消费者一直没有反馈，那么 该消息将一直处于不可用状态。
		 * 如果选用自动确认,在消费者拿走消息执行过程中出现宕机时,消息可能就会丢失！！
		 */
		channel.basicConsume(TASK_QUEUE_NAME, false, consumer);

	}

	private static void doWork(String task) {
		try {
			Thread.sleep(3000); // 暂停1秒钟
		} catch (InterruptedException _ignored) {
			Thread.currentThread().interrupt();
		}
	}

}
