package com.sz7road.web.common.util;

import com.sz7road.web.common.listener.SystemConfig;

import redis.clients.jedis.Jedis;  
import redis.clients.jedis.JedisPool;  
import redis.clients.jedis.JedisPoolConfig;  
  
public class JedisFactory {  
      
    private static JedisPool jedisPool;  
    
    public JedisFactory() {  
        super();
    }

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(Integer.parseInt(SystemConfig.getProperty("jedis.pool.maxActive")));  //最大活跃数
        config.setMaxIdle(Integer.parseInt(SystemConfig.getProperty("jedis.pool.maxIdle")));  //最大空闲连接数
        config.setMaxWait(Integer.parseInt(SystemConfig.getProperty("jedis.pool.maxWait")));  //最大等待时间
        config.setTestOnBorrow(true);
         
        jedisPool = new JedisPool(config, SystemConfig.getProperty("redis.server.ip"),Integer.parseInt(SystemConfig.getProperty("redis.server.port")),5000);
    }

    public Jedis getJedisInstance() { 
    	Jedis jedis = jedisPool.getResource();
    	//jedis.auth("love7road");
        return jedis;  
    }  
      
    /** 
     * 配合使用getJedisInstance方法后将jedis对象释放回连接池中 
     *  
     * @param jedis 使用完毕的Jedis对象 
     * @return true 释放成功；否则返回false 
     */  
    public boolean release(Jedis jedis) {
        if (jedisPool != null && jedis != null) {  
            jedisPool.returnResource(jedis);  
            return true;  
        }  
        return false;  
    }  
  
}  