package com.xwj.service;

/**
 * RabbitMq�ӿ�
 * 
 * @author xuwenjin
 */
public interface IRabbitMqService {

	/**
	 * ����fanout���͵���Ϣ
	 * 
	 * @param message
	 *            ��Ϣ
	 */
	void sendFanoutMessage(Object message);

	/**
	 * ����topic���͵���Ϣ
	 * 
	 * @param message
	 *            ��Ϣ
	 * @param routingKey
	 *            ���а󶨽�������key
	 */
	void sendTopicMessage(Object message, String routingKey);

	/**
	 * ����direct���͵���Ϣ
	 * 
	 * @param message
	 *            ��Ϣ
	 * @param routingKey
	 *            ���а󶨽�������key
	 */
	void sendDirectMessage(Object message, String routingKey);

}
