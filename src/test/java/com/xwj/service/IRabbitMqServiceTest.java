package com.xwj.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwj.entity.XwjUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-rabbitmq*.xml" })
public class IRabbitMqServiceTest {

	@Autowired
	private IRabbitMqService service;

	@Test
	public void testSendDirectMessage() {
		for (int i = 0; i < 5; i++) {
			XwjUser user = new XwjUser(i, "Hello world" + i, new Date());
			service.sendDirectMessage(user, "queue.key." + i);
		}
	}

	@Test
	public void testSendTopicMessage() {
		service.sendTopicMessage("Hello1", "xwj.abc");
		service.sendTopicMessage("Hello2", "abc.xwj");
		service.sendTopicMessage("Hello3", "xwj.bacd.dd");
		service.sendTopicMessage("Hello4", "abc.skdjf");
	}

	@Test
	public void testSendFanoutMessage() {
		service.sendFanoutMessage("Hello1");
		service.sendFanoutMessage("Hello2");
		service.sendFanoutMessage("Hello3");
		service.sendFanoutMessage("Hello4");
	}

}
