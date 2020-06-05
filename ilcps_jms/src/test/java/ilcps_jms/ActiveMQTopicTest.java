package ilcps_jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class ActiveMQTopicTest {
	
	/**
	 * 使用topic模式发送消息
	 * @throws JMSException
	 */
	@Test
	public void ActiveMQTopicSender() throws JMSException
	{
	 ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory();
	 Connection connection = factory.createConnection();
	 connection.start();
	 Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	 Topic topic = session.createTopic("hxq1908212210");
	 MessageProducer producer = session.createProducer(topic);
	 for (int i = 0; i < 10; i++) {
		MapMessage mapMessage = session.createMapMessage();
		mapMessage.setString("msg", i+":你好啊,第一次使用ActiveMQ消息中间件的topic模式");
		mapMessage.setString("num", String.valueOf(i));
		producer.send(mapMessage);
	 }
	 session.close();
	 connection.close();
	}
	
	/**
	 * 使用topic模式手动接收消息
	 * @throws JMSException
	 */
	@Test
	public void AMQP2PReciveTest() throws JMSException
	{
		//创建连接工厂
		ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory();
		//创建连接
		Connection connection = factory.createConnection();
		//开启连接
		connection.start();
		//创建会话
		Session createSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建队列
		Topic topic = createSession.createTopic("hxq1908212210");
		//创建消费者
		MessageConsumer createConsumer = createSession.createConsumer(topic);
		//开始消费
		Message message = createConsumer.receive();
		System.out.println(((MapMessage)message).getString("msg"));
		//关闭会话
		createSession.close();
		//关闭连接
		connection.close();
	}
	
	/**
	 * 使用topic模式监听接收消息
	 * @throws JMSException
	 */
	@Test
	public void AMQP2PReciverListenerTest1() throws JMSException
	{
		ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory();
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("hxq1908212210");
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message arg0) {
				MapMessage message=(MapMessage)arg0;
				try {
					System.out.println(message.getString("msg"));
					System.out.println(message.getString("num"));
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		while(true);
	}
	
	/**
	 * 使用topic模式监听接收消息
	 * @throws JMSException
	 */
	@Test
	public void AMQP2PReciverListenerTest2() throws JMSException
	{
		ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory();
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("hxq1908212210");
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message arg0) {
				MapMessage message=(MapMessage)arg0;
				try {
					System.out.println(message.getString("msg"));
					System.out.println(message.getString("num"));
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		while(true);
	}
}
