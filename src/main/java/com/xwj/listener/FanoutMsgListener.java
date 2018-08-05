package com.xwj.listener;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 自定义消息监听器
 * 
 * @author xuwenjin
 */
public class FanoutMsgListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			String body = new String(message.getBody(), "UTF-8");
			System.out.println("fanout, 接收到的消息：" + body);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
