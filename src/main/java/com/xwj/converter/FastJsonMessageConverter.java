package com.xwj.converter;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

public class FastJsonMessageConverter extends AbstractMessageConverter {
	
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	public FastJsonMessageConverter() {
		super();
	}

	/**
	 * createMessage 方法实现逻辑： 
	 * 1、通过 ObjectMapper 将 Java 对象转化为 JSON 字符串，再将字符串转为byte[] 
	 * 2、设置 MessageProperties，contentType 设为application/json，contentEncoding 设为 UTF-8，contentLength 设为 byte[] 长度 
	 * 3、向MessageProperties 的 headers 属性中添加 __TypeId__，其值为 Java 对象的类全名
	 */
	@Override
	protected Message createMessage(Object object, MessageProperties messageProperties) {
		
		return null;
	}

	/**
	 * fromMessage 方法实现逻辑： 
	 * 1、如果入参 Message 对象的 MessageProperties 属性为 null，或者消息属性contentType 值既不为空又不包含 json 关键字，则直接返回 Message 的 body （byte[]） 
	 * 2、从MessageProperties 的 headers 属性中读出 __TypeId__ 的值，通过 Jackson 的 API 将之转化为JavaType 对象，再将 message body 转化为 Java 对象
	 */
	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		return null;
	}

}
