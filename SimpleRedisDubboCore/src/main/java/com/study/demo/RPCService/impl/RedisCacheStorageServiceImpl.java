package com.study.demo.RPCService.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.study.demo.RedisCache;
import com.study.demo.RPCService.RedisCacheStorageService;
import com.study.demo.annotation.PerfAnalyzer;
import com.study.demo.enums.TrxCodeEnum;

@Service
public class RedisCacheStorageServiceImpl<V> implements RedisCacheStorageService<String, V> {

	 /**
     * 日志记录
     */
    public static final Log LOG = LogFactory.getLog(RedisCacheStorageServiceImpl.class);
    
    /**
     * 默认过时时间(60 * 60 * 24)
     */
    private static final int EXPIRE_TIME = 86400;
    
    @Autowired
    private RedisCache redisCache;
    
    /**
     * 在redis数据库中插入 key和value
     */
    @PerfAnalyzer(trx = TrxCodeEnum.REDIS_SET)
	public boolean set(String key, V value) {
		 // 设置默认过时时间
        return set(key, value, EXPIRE_TIME);
	}

	/**
	 * 在redis数据库中插入 key和value 并且设置过期时间
	 */
    @PerfAnalyzer(trx = TrxCodeEnum.REDIS_SET)
	public boolean set(String key, V value, int exp) {
		Jedis jedis = null;
        // 将key 和value  转换成 json 对象
        String jKey = JSON.toJSONString(key);
        String jValue = JSON.toJSONString(value);
        // 操作是否成功
        boolean isSucess = true;
        if (StringUtils.isEmpty(jKey)) {
            LOG.info("key is empty");
            return false;
        }
        try {
            // 获取客户端对象
            jedis = redisCache.getResource();
            // 执行插入
            jedis.setex(jKey, exp, jValue);
        } catch (Exception e) {
            LOG.info("client can't connect server");
            isSucess = false;
            if (null != jedis) {
                // 释放jedis对象
                redisCache.brokenResource(jedis);
            }
            return false;
        } finally {
            if (isSucess) {
                // 返还连接池
                redisCache.returnResource(jedis);
            }
        }
        return true;
	}

	/**
	 * 根据key去redis中获取value
	 */
	@SuppressWarnings("unchecked")
	@PerfAnalyzer(trx = TrxCodeEnum.REDIS_GET)
	public V get(String key) {
		Jedis jedis = null;
        // 将key 和value  转换成 json 对象
        String jKey = JSON.toJSONString(key);
        V jValue = null;
        // key 不能为空
        if (StringUtils.isEmpty(jKey)) {
            LOG.info("key is empty");
            return null;
        }
        try {
            // 获取客户端对象
            jedis = redisCache.getResource();
            // 执行查询
            String value = jedis.get(jKey);
            // 判断值是否非空
            if (StringUtils.isEmpty(value)) {
                return null;
            } else {
                jValue = (V) JSON.parse(value);
            }
            // 返还连接池
            redisCache.returnResource(jedis);
        } catch (Exception e) {
            LOG.info("client can't connect server");
            if (null != jedis) {
                // 释放jedis对象
                redisCache.brokenResource(jedis);
            }
        }
        return jValue;
	}

	/**
	 * 删除redis库中的数据
	 */
	@PerfAnalyzer(trx = TrxCodeEnum.REDIS_REMOVE)
	public boolean remove(String key) {
		Jedis jedis = null;
        // 将key 和value  转换成 json 对象
        String jKey = JSON.toJSONString(key);
        // 操作是否成功
        boolean isSucess = true;
        if (StringUtils.isEmpty(jKey)) {
            LOG.info("key is empty");
            return false;
        }
        try {
            jedis = redisCache.getResource();
            // 执行删除
            jedis.del(jKey);
        } catch (Exception e) {
            LOG.info("client can't connect server");
            isSucess = false;
            if (null != jedis) {
                // 释放jedis对象
                redisCache.brokenResource(jedis);
            }
            return false;
        } finally {
            if (isSucess) {
                // 返还连接池
                redisCache.returnResource(jedis);
            }
        }
        return true;
	}

	/**
	 * 设置哈希类型数据到redis数据库
	 */
	public boolean hset(String cacheKey, String key, V value) {
		Jedis jedis = null;
        // 将key 和value  转换成 json 对象
        String jKey = JSON.toJSONString(key);
        String jCacheKey = JSON.toJSONString(cacheKey);
        String jValue = JSON.toJSONString(value);
        // 操作是否成功
        boolean isSucess = true;
        if (StringUtils.isEmpty(jCacheKey)) {
            LOG.info("cacheKey is empty");
            return false;
        }
        try {
            jedis = redisCache.getResource();
            // 执行插入哈希
            jedis.hset(jCacheKey, jKey, jValue);
        } catch (Exception e) {
            LOG.info("client can't connect server");
            isSucess = false;
            if (null != jedis) {
                // 释放jedis对象
                redisCache.brokenResource(jedis);
            }
            return false;
        } finally {
            if (isSucess) {
                // 返还连接池
                redisCache.returnResource(jedis);
            }
        }
        return true;
	}
	/**
	 *  获取哈希表数据类型的值
	 */
	public V hget(String cacheKey, String key) {
		Jedis jedis = null;
        // 将key 和value  转换成 json 对象
        String jKey = JSON.toJSONString(key);
        String jCacheKey = JSON.toJSONString(cacheKey);
        V jValue = null;
        if (StringUtils.isEmpty(jCacheKey)) {
            LOG.info("cacheKey is empty");
            return null;
        }
        try {
            // 获取客户端对象
            jedis = redisCache.getResource();
            // 执行查询
            String value = jedis.hget(jCacheKey, jKey);
            // 判断值是否非空
            if (StringUtils.isEmpty(value)) {
                return null;
            } else {
                jValue = (V) JSON.parse(value);
            }
            // 返还连接池
            redisCache.returnResource(jedis);
        } catch (Exception e) {
            LOG.info("client can't connect server");
            if (null != jedis) {
                // 释放jedis对象
                redisCache.brokenResource(jedis);
            }
        }
        return jValue;
	}

	/**
	 * 获取哈希类型的数据
	 */
	public Map<String, V> hget(String cacheKey) {
		 String jCacheKey = JSON.toJSONString(cacheKey);
	        // 非空校验
	        if (StringUtils.isEmpty(jCacheKey)) {
	            LOG.info("cacheKey is empty!");
	            return null;
	        }
	        Jedis jedis = null;
	        Map<String, V> result = null;
	        try {
	            jedis = redisCache.getResource();
	            // 获取列表集合
	            Map<String, String> map = jedis.hgetAll(jCacheKey);

	            if (null != map) {
	                for (Map.Entry<String, String> entry : map.entrySet()) {
	                    if (result == null) {
	                        result = new HashMap<String, V>();
	                    }
	                    result.put((String) JSON.parse(entry.getKey()), (V) JSON.parse(entry.getValue()));
	                }
	            }
	        } catch (Exception e) {
	            LOG.info("client can't connect server");
	            if (null != jedis) {
	                // 释放jedis对象
	                redisCache.brokenResource(jedis);
	            }
	        }
	        return result;
	    }

}
