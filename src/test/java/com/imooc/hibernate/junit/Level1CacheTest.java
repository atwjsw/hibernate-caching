package com.imooc.hibernate.junit;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.imooc.hibernate.entity.Employee;
import com.imooc.hibernate.util.HibernateUtil;

/*
 * Hibernate一级缓存测试类
 * 
 *1. Hibernate一级缓存又称为“Session级缓存”、“会话级缓存”
 *2. 通过Session从数据库查询实体时会把实体在内存中存储起来，下一次查询同一实体时不再从数据库获取，而是
 *   从内存中获取  
 *3. 一级缓存的生命周期和Session相同，Session销毁，它也销毁。 
 *4. 一级缓存是Hibernate默认开启的，可通过两个方法管理：
 *	evict(): 用于将某个对象从Session的一级缓存中清除；clear(); 将缓存中所有对象清除。
 *5. 一级缓存也有时候会对程序性能产生影响，例如增删改后需要更新缓存
 */
public class Level1CacheTest {

	@Test
	
	public void testLevel1Caching() {
		
		Session session = HibernateUtil.getSession();
		
		Employee employee1 = (Employee)session.get(Employee.class, 1);		
		System.out.println(employee1.getName());
		
		//如果重新获取一次session，则需要重新发送SQL，因为一级缓存是Session级的，不同session保存的对象不同。
		session = HibernateUtil.getSession();
		
		//由于有Session级缓存，第二次查询同一对象不需要发送SQL
		employee1 = (Employee)session.get(Employee.class, 1);		
		System.out.println(employee1.getName());
		
		
		HibernateUtil.closeSession(session);		
	};	
	
	@Test
	public void testLevel1CachingMgt() {
		
		Session session = HibernateUtil.getSession();
		
		Employee employee1 = (Employee)session.get(Employee.class, 1);		
		System.out.println(employee1.getName());
		
		//evict将某个对象从Session的一级缓存中清除，因此以下查询语句会再次发送SQL.
		session.evict(employee1);
		
		//clear(); 将缓存中所有对象清除，因此以下查询语句会再次发送SQL.
		//session.clear();
		
		employee1 = (Employee)session.get(Employee.class, 1);		
		System.out.println(employee1.getName());
		
		
		HibernateUtil.closeSession(session);		
	};	
	
	@Test
	public void testQueryByCaching() {
		
		Session session = HibernateUtil.getSession();
		
		Query query = session.createQuery(" from Employee");
		List<Employee> employees = query.list();
		
		for (Employee e: employees) {
			System.out.println(e.getName());
		}		
		
		//Query.list不使用缓存。在同一个session内执行query.list会再次发送SQL
		/*employees = query.list();
		
		for (Employee e: employees) {
			System.out.println(e.getName());
		}		*/
		
		//query.iterate的执行原理：1）先去数据库取主键，2）根据主键去缓存中取对象，如果缓存中没有则去数据库取。
		
		Iterator it = query.iterate();
		while(it.hasNext()) {
			Employee e = (Employee) it.next();
			System.out.println(e.getName());
		}		
		HibernateUtil.closeSession(session);		
	};	
}
