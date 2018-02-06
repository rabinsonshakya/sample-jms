package com.cubic.jms;

import java.util.Properties;

import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.sun.messaging.jmq.jmsserver.core.Session;

public class JMSPublisher {
	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
		props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
		props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
		InitialContext ctx = new InitialContext(props);
		TopicConnectionFactory factory = (TopicConnectionFactory) ctx.lookup("jms/tcf");
		TopicConnection qc = null;
		
		try {
			qc = factory.createTopicConnection();
			qc.start();
			System.out.println("Got the Connection");
			TopicSession session = qc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE); 
			Topic topic = (Topic) ctx.lookup("jms/MyTopic");
			TopicPublisher pub = session.createPublisher(topic);
			
			TextMessage message = session.createTextMessage();
			message.setText("Hello 123 Topic Message");
			pub.publish(message);
			System.out.println("Message is sent to Topic");
			qc.stop();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if (qc != null) {
				qc.close();
			}
		}
		
	}

}
