package com.queueBrowser;


import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.NamingException;

public class JMSFactory {

    private static Session session;
    private static Context context;

    public static Session getSession() throws NamingException, JMSException {
        if (session == null) init();
        return session;
    }

    public static Context getContext() throws NamingException, JMSException {
        if (context == null) init();
        return context;
    }

    private static void init() throws JMSException, NamingException {

    }
}
