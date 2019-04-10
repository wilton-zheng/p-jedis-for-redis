package com.lingdu.jedis_test;

import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis连接池的使用
 * @author LingDu
 */
public class JedisConnectionPoolTest {
	//定义连接池对象
	private static JedisPool jedisPool = null;
	
    // Redis服务器IP
    private static String ADDR = "192.168.20.33";
    
    // Redis的端口号
    private static int PORT = 6379;
    
    // 访问密码
    private static String AUTH = "123456";
    
    /**
     * 初始化Redis连接池
     */
    @BeforeClass
    public static void init(){
    	try {
    		//创建连接池配置实例
            JedisPoolConfig config = new JedisPoolConfig();
            
            // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
            config.setBlockWhenExhausted(true);
            
            // 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
            config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
            
            // 是否启用pool的jmx管理功能, 默认true
            config.setJmxEnabled(true);
            
            // 最大空闲连接数, 默认8个 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(8);
            
            // 最大连接数, 默认8个
            config.setMaxTotal(200);
            
            // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 100);
            
            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            
            //实例化连接池对象
            jedisPool = new JedisPool(config, ADDR, PORT, 3000, AUTH);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
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
