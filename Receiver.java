package com.queueBrowser;

import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.NamingException;

public class Receiver {
	

	public static void main(String[] args) throws JMSException, InterruptedException {

		if (args.length >0 && args[0] != null)	/// Url can be changed with parameter[0]
			Constant.URL = args[0];

		OpenJMS instance = OpenJMS.getInstance();
		final Thread currentThread = Thread.currentThread();
		QueueSession session = instance.getSession();
		Context context = instance.getContext();
		QueueConnection connection = instance.getConnection();
		Queue destination = null;

		try {
			destination = (Queue) context.lookup(Constant.responseQueue);
		} catch (NamingException e) {
			System.out.println(e.getClass().getName() + " :" + e.getMessage());
		}

		connection.start();
		
		listMessages(session, destination);
	
		System.out.println("List operation is finished");
		synchronized (currentThread) {
			currentThread.wait();
		}
		System.out.println("System out");
		System.exit(0);

	}

	/// for delete messages, call by JMSMessageID
	public static void deleteMessage(String JMSMessageID, QueueSession qs, Destination d) throws JMSException {
		MessageConsumer receiver = qs.createConsumer(d, String.format("JMSMessageID='%s'", JMSMessageID));
		receiver.receive();
	}

	public static void printJmsResponse(TextMessage message) throws JMSException {
		System.out.println("Response : " + message.getText() + "");
		System.out.println("Send TimeStamp:" + message.getJMSTimestamp());
		System.out.println("Jms Corrletaion ID:" + message.getJMSCorrelationID());
		System.out.println("Message ID:" + message.getJMSMessageID());
		System.out.println("");
	}

	public static void listMessages(QueueSession session, Queue destination) throws JMSException {
		QueueBrowser browser = session.createBrowser(destination);
		Enumeration e = browser.getEnumeration();
		while (e.hasMoreElements()) {
			TextMessage message = (TextMessage) e.nextElement();
			printJmsResponse(message);
	
		}
		browser.close();
	}

}
