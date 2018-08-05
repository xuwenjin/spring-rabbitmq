package com.xwj.simple.publish.direct;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xwj.simple.consts.ConfigKey;

/**
 * 交换机使用direct模式
 * 
 * @author xuwenjin
 */
public class Sender {

	// 交换机名称
	private static final String EXCHANGE_NAME = "direct_logs";

	// 路由关键字
	private static final String[] routingKeys = new String[] { "info", "warning", "error" };

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ConfigKey.HOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// 创建direct类型的exchange,
		channel.exchangeDeclare(EXCHANGE_NAME, ConfigKey.EX_DIRECT);

		// 发送消息
		for (String key : routingKeys) {
			String message = "Send the message level:" + key;
			channel.basicPublish(EXCHANGE_NAME, key, null, message.getBytes());
			System.out.println(" [x] Sent '" + key + "':'" + message + "'");
		}

		channel.close();
		connection.close();
	}

}
