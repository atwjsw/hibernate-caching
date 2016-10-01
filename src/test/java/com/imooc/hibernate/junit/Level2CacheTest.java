package com.imooc.hibernate.junit;

import org.hibernate.Session;
import org.junit.Test;

import com.imooc.hibernate.entity.Employee;
import com.imooc.hibernate.util.HibernateUtil;

/*
 * Hibernate二级缓存测试类
 * 
 * 如何让不同session能够共享缓存？ 使用二级缓存，session公用的缓存
 * 二级缓存非默认开启，需进行配置，配置步骤如下：
 * 1. 添加二级缓存对应的Jar包：common-logging-.1.1.3.jar, ehcache.jar.zip
 * 2. 在Hibernate配置文件中添加Provider类的描述
 * 3. 添加二级缓存的属性配置文件
 * 4. 在需要被缓存的持久类对象配置文件中添加cache属性。
 * 
 * 二级缓存的特点：
 * 1. 又称为“全局缓存”、‘应用级缓存“
 * 2. 二级缓存中的数据可适用范围是当前应用的所有会话。
 * 3. 二级缓存是可插拔式缓存，默认是EHCache,还支持其他二级缓存组件如：Hashtable, OSCache
 * , SwarmCache, JBoss TreeCache等
 * 
 * 二级缓存的适用场景：
 * 1. 很少被修改的数据
 * 2. 不是很重要的数据，允许出现偶尔并发的数据。（银行的金额这些就不适合）
 * 3. 不会被并发访问的数据
 * 4. 参考数据 （码表）
 * 
 * 
 */
public class Level2CacheTest {

	@Test
	public void testLevel2Caching() {
		
		Session session = HibernateUtil.getSession();
		
		Employee employee1 = (Employee)session.get(Employee.class, 1);		
		System.out.println(employee1.getName());
		
		session = HibernateUtil.getSession();
		employee1 = (Employee)session.get(Employee.class, 1);		
		System.out.println(employee1.getName());
		
		HibernateUtil.closeSession(session);		
	};	
}
