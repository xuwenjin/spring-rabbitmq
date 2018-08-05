package com.xwj.service.impl;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.xwj.service.IRabbitMqService;

/**
 * RabbitMq�ӿ�ʵ����
 * 
 * @author xuwenjin
 */
@Service
public class RabbitMqServiceImpl implements IRabbitMqService {

	@Resource(name = "directRabbitTemplate")
	private RabbitTemplate directRabbitTemplate;

	@Resource(name = "topicRabbitTemplate")
	private RabbitTemplate topicRabbitTemplate;

	@Resource(name = "fanoutRabbitTemplate")
	private RabbitTemplate fanoutRabbitTemplate;

	@Override
	public void sendDirectMessage(Object message, String routingKey) {
		directRabbitTemplate.convertAndSend(routingKey, message);
		System.out.println("����direct��Ϣ������Ϊ��" + message);
	}

	@Override
	public void sendTopicMessage(Object message, String routingKey) {
		topicRabbitTemplate.convertAndSend(routingKey, message);
		System.out.println("����topic��Ϣ, ����Ϊ��" + message);
	}

	@Override
	public void sendFanoutMessage(Object message) {
		fanoutRabbitTemplate.convertAndSend(message);
		System.out.println("����fanout��Ϣ, ����Ϊ��" + message);
	}

}
