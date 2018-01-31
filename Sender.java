package com.queueBrowser;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.NamingException;

public class Sender {

	public static void main(String[] args) throws NamingException, JMSException {

		if (args.length >0 && args[0] != null)		/// Url can be changed with parameter[0]
			Constant.URL = args[0];
		
		OpenJMS instance = OpenJMS.getInstance();
		Context context = instance.getContext();
		QueueConnection connection = instance.getConnection();
		QueueSession session = instance.getSession();
		Queue destination = (Queue) context.lookup(Constant.requestQueue);
		connection.start();
		QueueSender sender = session.createSender(destination);
		
		String xml = "Hello World !! ";   // to be sent data

		TextMessage message = session.createTextMessage(xml);

		sender.send(message);

	}
}
