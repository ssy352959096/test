package com.aurora.redis;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;





/**
 * redis cache 工具类
 *
 * @author BYG 2017-11-13
 * @version 1.0
 */
@Repository("redisUtil")
public class RedisUtil {

	//注解注入redisTemplate
	@Resource(name = "redisTemplate")
    private RedisTemplate<Serializable, Object> redisTemplate;

	/**
     * 批量删除key对应的value
     *
     * @param keys
     */
    public void removeBatch(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 删除key对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (hasKey(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean hasKey(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

  /**
   * @Title: getAllKey 
   * @Description: 查询所有的Key 
   * @param    
   * @return boolean  
   * @author SSY
   * @date 2018年5月5日 下午3:36:27
   */
    public Set<Serializable> getAllKey(String pattern) {
    	Set<Serializable> keys = null;
        try {
        	keys = redisTemplate.keys("*");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keys;
    }

    
    
    
    
    
}

