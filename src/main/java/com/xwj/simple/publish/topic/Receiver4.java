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
 * 交换机使用topic模式
 * 
 * @author xuwenjin
 */
public class Receiver4 {

	// 交换机名称
	private static final String EXCHANGE_NAME = "topic_logs";

	// 路由关键字
	private static final String[] routingKeys = new String[] { "*" };

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ConfigKey.HOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// 创建direct类型的exchange,
		channel.exchangeDeclare(EXCHANGE_NAME, ConfigKey.EX_TOPIC);
		// 获取临时队列名称
		String queueName = channel.queueDeclare().getQueue();

		// 根据路由关键字进行多重绑定
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
		// 自动确认消息
		channel.basicConsume(queueName, true, consumer);
	}

}
