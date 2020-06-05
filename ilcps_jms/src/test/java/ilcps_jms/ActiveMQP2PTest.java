package ilcps_jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.spring.ActiveMQConnectionFactoryFactoryBean;
import org.junit.Test;

public class ActiveMQP2PTest {

	/**
	 * 使用p2p模式发送消息
	 * @throws JMSException
	 */
	@Test
	public void AMQP2PSendrTest() throws JMSException
	{
		//创建连接工厂
		ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory();
		//创建连接
		Connection createConnection = factory.createConnection();
		//开启连接
		createConnection.start();
		//创建session会话 参数1:是否开启事务,参数二是否自动
		Session createSession = createConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建队列
		Queue queue = createSession.createQueue("hxq090821");
		for (int i = 0; i < 10; i++) {
			//创建生产者,参数1 指定生产者在哪个队列中生产消息
			MessageProducer producer = createSession.createProducer(queue);
			//创建消息
			TextMessage createTextMessage = createSession.createTextMessage(i+":你好啊,第一次使用ActiveMQ消息中间件的p2p模式");
			//发送消息
			producer.send(createTextMessage);
		}
		//关闭会话
		createSession.close();
		//关闭连接
		createConnection.close();
	}
	
	/**
	 * 使用p2p模式手动接收消息
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
		Queue queue = createSession.createQueue("hxq090821");
		//创建消费者
		MessageConsumer createConsumer = createSession.createConsumer(queue);
		//开始消费
		Message message = createConsumer.receive();
		System.out.println(((TextMessage)message).getText());
		//关闭会话
		createSession.close();
		//关闭连接
		connection.close();
	}
	
	/**
	 * 使用p2p模式监听接收消息
	 * @throws JMSException
	 */
	@Test
	public void AMQP2PReciverListenerTest1() throws JMSException
	{
		ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory();
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("hxq090821");
		MessageConsumer consumer = session.createConsumer(queue);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message arg0) {
				TextMessage message=(TextMessage)arg0;
				try {
					System.out.println(message.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		while(true);
	}
	
	/**
	 * 使用p2p模式监听接收消息
	 * @throws JMSException
	 */
	@Test
	public void AMQP2PReciverListenerTest2() throws JMSException
	{
		ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory();
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("hxq090821");
		MessageConsumer consumer = session.createConsumer(queue);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message arg0) {
				TextMessage message=(TextMessage)arg0;
				try {
					System.out.println(message.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		while(true);
	}
}
