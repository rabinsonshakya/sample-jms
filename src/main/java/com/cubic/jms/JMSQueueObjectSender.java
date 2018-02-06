package com.cubic.jms;

import java.util.Properties;

import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.sun.messaging.jmq.jmsserver.core.Session;

public class JMSQueueObjectSender {
	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
		props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
		props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
		InitialContext ctx = new InitialContext(props);
		
		
		QueueConnectionFactory qcFactory = (QueueConnectionFactory) ctx.lookup("jms/qcf");
		QueueConnection qc = null;
		QueueSession session = null;
		Queue queue = null;
		try {
			qc = qcFactory.createQueueConnection();
			qc.start();
			System.out.println("Got the Connection");
			session = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE); 
			queue = (Queue) ctx.lookup("jms/MyQueue");
			
			ObjectMessage message = session.createObjectMessage();
			message.setObject(new Person("Robinson", "Cruiser"));
			
			QueueSender sender = session.createSender(queue);
			sender.send(message);
			System.out.println("Message is sent");
			qc.stop();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if (session != null) {
				session.close();
			}
		}
		
	}

}
