package com.xwj.simple.hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xwj.simple.consts.ConfigKey;

/**
 * rabbitmq����ͨ���ǳ־�ͨ��,�����queue �Ѿ�����, ����ͨ�����Ը�����޸ĺ�����Բ�һ��,�ᵼ�����б���
 * 
 * @author xuwenjin
 */
public class P {

	private final static String QUEUE_NAME = "hello1";

	public static void main(String[] argv) throws Exception {
		// �������ӹ���
		ConnectionFactory factory = new ConnectionFactory();

		// ����RabbitMQ��ַ
		factory.setHost(ConfigKey.HOST);

		// ����һ���µ�����
		Connection connection = factory.newConnection();

		// ����һ��Ƶ��
		Channel channel = connection.createChannel();

		// ����һ������ --
		// ��RabbitMQ�У������������ݵ��Եģ�һ���ݵȲ������ص�����������ִ����������Ӱ�����һ��ִ�е�Ӱ����ͬ����Ҳ����˵����������ڣ��ʹ�����������ڣ�������Ѿ����ڵĶ��в����κ�Ӱ�졣
		// queue ��������
		// durable Ϊtrueʱserver�������в�����ʧ (�Ƿ�־û�)
		// exclusive �����Ƿ��Ƕ�ռ�ģ����Ϊtrueֻ�ܱ�һ��connectionʹ�ã��������ӽ���ʱ���׳��쳣 
		// autoDelete ��û���κ�������ʹ��ʱ���Զ�ɾ���ö���
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		String message = "Hello World!";

		/* 
         * ��server����һ����Ϣ 
         * ����1��exchange���֣���Ϊ����ʹ��Ĭ�ϵ�exchange 
         * ����2��routing key 
         * ����3������������ 
         * ����4����Ϣ�� 
         * RabbitMQĬ����һ��exchange����default exchange������һ�����ַ�����ʾ������direct exchange���ͣ� 
         * �κη������exchange����Ϣ���ᱻ·�ɵ�routing key�����ֶ�Ӧ�Ķ����ϣ����û�ж�Ӧ�Ķ��У�����Ϣ�ᱻ���� 
         */  
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));

		System.out.println("P Sent '" + message + "'");

		// �ر�Ƶ��������
		channel.close();
		connection.close();
	}

}
