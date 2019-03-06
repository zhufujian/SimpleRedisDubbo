package com.study.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.study.demo.RPCService.RedisCacheStorageService;


@RunWith(value= SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:applicationContext.xml"})
public class RedisCacheStorageServiceTest {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisCacheStorageService redisCacheStorageService;
	
	@Test
	public void set(){ 
		logger.info("返回報文："+redisCacheStorageService.set("test2", "hello"));
	}
	@Test
	public void get(){
		logger.info("返回報文："+JSON.toJSONString(redisCacheStorageService.get("test2")));
	}
}
