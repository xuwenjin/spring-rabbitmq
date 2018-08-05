package com.xwj.listener;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * �Զ�����Ϣ������
 * 
 * @author xuwenjin
 */
public class DirectMsgListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			String body = new String(message.getBody(), "UTF-8");
			System.out.println("direct, ���յ�����Ϣ��" + body);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
