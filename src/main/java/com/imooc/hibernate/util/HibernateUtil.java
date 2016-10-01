package com.imooc.hibernate.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	private static Session session;
	//private Transaction transaction;

	static {
		Configuration config = new Configuration().configure();
		sessionFactory = config.buildSessionFactory();
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static Session getSession() {
		session =  sessionFactory.openSession();
		return session;
	}
	
	public static void closeSession(Session session) {
		if (session != null) {
			session.close();
		}		
	}
}
