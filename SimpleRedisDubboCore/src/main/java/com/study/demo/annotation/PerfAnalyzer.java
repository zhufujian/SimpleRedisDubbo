package com.study.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.study.demo.enums.TrxCodeEnum;

/**
 * 
*
* @Description: 接口性能监控
* @ClassName: PerfAnalyzer 
* @author zhufj
* @date 2019年3月6日 下午2:35:55 
*
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PerfAnalyzer {
	/** 接口名称 */
	public TrxCodeEnum trx();
}
