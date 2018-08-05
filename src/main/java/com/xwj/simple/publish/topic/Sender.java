package com.xwj.simple.publish.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xwj.simple.consts.ConfigKey;

/**
 * ������ʹ��topicģʽ
 * 
 * @author xuwenjin
 */
public class Sender {

	// ����������
	private static final String EXCHANGE_NAME = "topic_logs";

	// ·�ɹؼ���
	// �����͵���Ϣ
	private static final String[] routingKeys = new String[] { 
			"quick.orange.rabbit", 
			"lazy.orange.elephant",
			"quick.orange.fox", 
			"lazy.brown.fox", 
			"quick.brown.fox", 
			"quick.orange.male.rabbit",
			"lazy.orange.male.rabbit" };

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ConfigKey.HOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// ����direct���͵�exchange,
		channel.exchangeDeclare(EXCHANGE_NAME, ConfigKey.EX_TOPIC);

		// ������Ϣ
		for (String key : routingKeys) {
			String message = "From " + key + " routingKey' s message";
			channel.basicPublish(EXCHANGE_NAME, key, null, message.getBytes());
			System.out.println(" [x] Sent '" + key + "':'" + message + "'");
		}

		channel.close();
		connection.close();
	}

}
