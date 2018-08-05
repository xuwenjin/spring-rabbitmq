package com.xwj.simple.publish.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xwj.simple.consts.ConfigKey;

/**
 * ������ʹ��fanoutģʽ
 * 
 * @author xuwenjin
 */
public class Sender {

	private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConfigKey.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /*
         * ʹ��fanout���ʹ����Ľ�����
         * exchange������������
         * type������������(direct/topic/fanout)
         */
        channel.exchangeDeclare(EXCHANGE_NAME, ConfigKey.EX_FANOUT);

//      �ַ���Ϣ
        for(int i = 0 ; i < 5; i++){
            String message = "Hello World! " + i;
             channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
             System.out.println(" [x] Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }

}
