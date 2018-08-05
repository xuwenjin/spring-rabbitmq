package com.xwj.simple.publish.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xwj.simple.consts.ConfigKey;

/**
 * 交换机使用topic模式
 * 
 * @author xuwenjin
 */
public class Sender2 {

	// 交换机名称
	private static final String EXCHANGE_NAME = "topic_logs";

	// 路由关键字
	// 待发送的消息
	private static final String[] routingKeys = new String[] { 
			"", "..", "A" };

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ConfigKey.HOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// 创建direct类型的exchange,
		channel.exchangeDeclare(EXCHANGE_NAME, ConfigKey.EX_TOPIC);

		// 发送消息
		for (String key : routingKeys) {
			String message = "From " + key + " routingKey' s message";
			channel.basicPublish(EXCHANGE_NAME, key, null, message.getBytes());
			System.out.println(" [x] Sent '" + key + "':'" + message + "'");
		}

		channel.close();
		connection.close();
	}

}
