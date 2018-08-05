package com.xwj.service;

/**
 * RabbitMq接口
 * 
 * @author xuwenjin
 */
public interface IRabbitMqService {

	/**
	 * 发送fanout类型的消息
	 * 
	 * @param message
	 *            消息
	 */
	void sendFanoutMessage(Object message);

	/**
	 * 发送topic类型的消息
	 * 
	 * @param message
	 *            消息
	 * @param routingKey
	 *            队列绑定交换机的key
	 */
	void sendTopicMessage(Object message, String routingKey);

	/**
	 * 发送direct类型的消息
	 * 
	 * @param message
	 *            消息
	 * @param routingKey
	 *            队列绑定交换机的key
	 */
	void sendDirectMessage(Object message, String routingKey);

}
