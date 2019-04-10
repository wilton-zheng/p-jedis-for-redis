package com.lingdu.jedis_test;

import java.io.IOException;
import java.util.Properties;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis连接池的使用
 * @author LingDu
 */
public class JedisConnectionPoolConfigTest {
	//定义连接池对象
	private static JedisPool jedisPool = null;
	//定义用于读取配置文件的Properties对象
	private static Properties pro;
    
	/**
	 * 读取配置redis.properties文件
	 */
	static{
		pro = new Properties();
		try {
			//加载配置文件
			pro.load(JedisConnectionPoolConfigTest.class.getClassLoader().getResourceAsStream("redis.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    /**
     * 初始化Jedis连接池
     */
    @BeforeClass
    public static void initJedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();  
        config.setMaxTotal(Integer.valueOf(pro.getProperty("redis.pool.maxTotal")));
        config.setMinIdle(Integer.valueOf(pro.getProperty("redis.pool.minIdle")));  
        config.setMaxIdle(Integer.valueOf(pro.getProperty("redis.pool.maxIdle")));  
        config.setMaxWaitMillis(Long.valueOf(pro.getProperty("redis.pool.maxWait")));  
        config.setTestWhileIdle(Boolean.valueOf(pro.getProperty("redis.pool.testWhileIdle")));  
        config.setTestOnBorrow(Boolean.valueOf(pro.getProperty("redis.pool.testOnBorrow")));  
        config.setTestOnReturn(Boolean.valueOf(pro.getProperty("redis.pool.testOnReturn")));  
        jedisPool = new JedisPool(config, pro.getProperty("redis.ip"), 
        		Integer.valueOf(pro.getProperty("redis.port")),
        		3000, 
        		pro.getProperty("redis.auth"));
    }
    

	@Test
	public void testGetJedisObject() {
		// 从池中获取一个Jedis对象  
        Jedis jedis = jedisPool.getResource();  
       
        jedis.set("username", "admin");
        System.out.println(jedis.get("username"));
        
        // 释放jedis资源 
        jedis.close();
	}
}
