package com.hxq.springActiveMqTest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:applicationContext-mq.xml")
public class TopicTest {

	@Autowired
	@Qualifier("jmsTopicTemplate")
	private JmsTemplate jmsTemplate;
	
	@Test
	public void Test1()
	{
		for (int i = 0; i < 10; i++) {
			final int m=i;
			jmsTemplate.send("spring_topic", new MessageCreator() {
				
				@Override
				public Message createMessage(Session arg0) throws JMSException {
					// TODO Auto-generated method stub
					TextMessage textMessage = arg0.createTextMessage("使用springActiveMQ发送topic消息"+m);
					return textMessage;
				}
			});
		}
		
	}
}
