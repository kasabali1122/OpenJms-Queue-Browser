package com.queueBrowser;


import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class OpenJMS {

	private static OpenJMS ourInstance;

	static {
		try {
			ourInstance = new OpenJMS();
		} catch (JMSException   e) {
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private QueueSession session;
	private Context context;
	private QueueConnection connection;

	public static OpenJMS getInstance() {
		return ourInstance;
	}

	private OpenJMS() throws JMSException, NamingException {
		
		System.out.println("Open Jms Queue Browser Version 1.0.3");
		
		Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, Constant.jndiName);
		properties.put(Context.PROVIDER_URL, Constant.URL);
		
		/*
		Optional parts
		properties.put(Context.SECURITY_PRINCIPAL, Constant.username);
		properties.put(Context.SECURITY_CREDENTIALS,Constant.password);
		*/
		
		context = new InitialContext(properties);
		QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup(Constant.factoryName);
		try {
			this.connection = connectionFactory.createQueueConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.connection.start();
		session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		System.out.println("Connection Created");
		
	}

	public QueueSession getSession() {
		return session;
	}

	public Context getContext() {
		return context;
	}

	public QueueConnection getConnection() {
		return connection;
	}

}
