package com.cubic.jms;

import java.util.Properties;

import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JMSConnectionTest {
	public static void main(String[] args) throws Exception {
		final Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
		props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
		props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
		final InitialContext ctx = new InitialContext(props);
		
		final QueueConnectionFactory qcFactory = (QueueConnectionFactory) ctx.lookup("jms/qcf");
		QueueConnection qc = null;
		try {
			qc = qcFactory.createQueueConnection();
			qc.start();
			System.out.println("Created Queue Connsction Sucessfully");
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
