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
	 * createMessage ����ʵ���߼��� 
	 * 1��ͨ�� ObjectMapper �� Java ����ת��Ϊ JSON �ַ������ٽ��ַ���תΪbyte[] 
	 * 2������ MessageProperties��contentType ��Ϊapplication/json��contentEncoding ��Ϊ UTF-8��contentLength ��Ϊ byte[] ���� 
	 * 3����MessageProperties �� headers ��������� __TypeId__����ֵΪ Java �������ȫ��
	 */
	@Override
	protected Message createMessage(Object object, MessageProperties messageProperties) {
		
		return null;
	}

	/**
	 * fromMessage ����ʵ���߼��� 
	 * 1�������� Message ����� MessageProperties ����Ϊ null��������Ϣ����contentType ֵ�Ȳ�Ϊ���ֲ����� json �ؼ��֣���ֱ�ӷ��� Message �� body ��byte[]�� 
	 * 2����MessageProperties �� headers �����ж��� __TypeId__ ��ֵ��ͨ�� Jackson �� API ��֮ת��ΪJavaType �����ٽ� message body ת��Ϊ Java ����
	 */
	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		return null;
	}

}
