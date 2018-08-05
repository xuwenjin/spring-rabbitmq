package com.xwj.simple.hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xwj.simple.consts.ConfigKey;

/**
 * rabbitmq服务通道是持久通道,如果该queue 已经存在, 而且通道属性跟最近修改后的属性不一致,会导致运行报错
 * 
 * @author xuwenjin
 */
public class P {

	private final static String QUEUE_NAME = "hello1";

	public static void main(String[] argv) throws Exception {
		// 创建连接工厂
		ConnectionFactory factory = new ConnectionFactory();

		// 设置RabbitMQ地址
		factory.setHost(ConfigKey.HOST);

		// 创建一个新的连接
		Connection connection = factory.newConnection();

		// 创建一个频道
		Channel channel = connection.createChannel();

		// 声明一个队列 --
		// 在RabbitMQ中，队列声明是幂等性的（一个幂等操作的特点是其任意多次执行所产生的影响均与一次执行的影响相同），也就是说，如果不存在，就创建，如果存在，不会对已经存在的队列产生任何影响。
		// queue 队列名称
		// durable 为true时server重启队列不会消失 (是否持久化)
		// exclusive 队列是否是独占的，如果为true只能被一个connection使用，其他连接建立时会抛出异常 
		// autoDelete 当没有任何消费者使用时，自动删除该队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		String message = "Hello World!";

		/* 
         * 向server发布一条消息 
         * 参数1：exchange名字，若为空则使用默认的exchange 
         * 参数2：routing key 
         * 参数3：其他的属性 
         * 参数4：消息体 
         * RabbitMQ默认有一个exchange，叫default exchange，它用一个空字符串表示，它是direct exchange类型， 
         * 任何发往这个exchange的消息都会被路由到routing key的名字对应的队列上，如果没有对应的队列，则消息会被丢弃 
         */  
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));

		System.out.println("P Sent '" + message + "'");

		// 关闭频道和连接
		channel.close();
		connection.close();
	}

}
