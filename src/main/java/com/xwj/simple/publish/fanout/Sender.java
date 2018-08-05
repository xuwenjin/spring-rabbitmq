package com.xwj.simple.publish.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xwj.simple.consts.ConfigKey;

/**
 * 交换机使用fanout模式
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
         * 使用fanout类型创建的交换器
         * exchange：交换机名称
         * type：交换机类型(direct/topic/fanout)
         */
        channel.exchangeDeclare(EXCHANGE_NAME, ConfigKey.EX_FANOUT);

//      分发消息
        for(int i = 0 ; i < 5; i++){
            String message = "Hello World! " + i;
             channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
             System.out.println(" [x] Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }

}
