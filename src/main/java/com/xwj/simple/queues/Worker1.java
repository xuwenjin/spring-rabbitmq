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

		// ͬһʱ�̷�����ֻ�ᷢһ����Ϣ��������(���߶���ģʽ)  
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
					// ��Ϣ������ɣ��ֶ�ȷ���ύ
					// deliveryTag ����Ϣ��index
					// multiple���Ƿ����� true:��һ����ack����С��deliveryTag����Ϣ��
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};

		/*��Ϣ�������ȷ��
		 * autoAck �Ƿ��Զ�ȷ�� true�Զ�ȷ�� false�ֶ�ȷ��
		 * ģʽ1���Զ�ȷ�� ֻҪ��Ϣ�Ӷ����л�ȡ�����������߻�ȡ����Ϣ���Ƿ�ɹ���Ϣ������Ϊ����Ϣ�Ѿ��ɹ����ѡ� 
		 * ģʽ2���ֶ�ȷ��
		 * �����ߴӶ����л�ȡ��Ϣ�󣬷������Ὣ����Ϣ���Ϊ������״̬���ȴ������ߵķ��������������һֱû�з�������ô ����Ϣ��һֱ���ڲ�����״̬��
		 * ���ѡ���Զ�ȷ��,��������������Ϣִ�й����г���崻�ʱ,��Ϣ���ܾͻᶪʧ����
		 */
		channel.basicConsume(TASK_QUEUE_NAME, false, consumer);

	}

	private static void doWork(String task) {
		try {
			Thread.sleep(3000); // ��ͣ1����
		} catch (InterruptedException _ignored) {
			Thread.currentThread().interrupt();
		}
	}

}
