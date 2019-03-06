package com.study.demo.aspect;

import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.study.demo.annotation.PerfAnalyzer;
import com.study.demo.utils.ThreadUtil;

/**
 * 性能监控切面
*
* @Description: 
* @ClassName: PerfAnalyzerAspect 
* @author zhufj
* @date 2019年3月6日 下午2:41:59 
*
 */
@Component
@Aspect
public class PerfAnalyzerAspect {
	private static Logger logger = LoggerFactory.getLogger(PerfAnalyzerAspect.class);
	@Value("${threadhold}")
	private String threadhold;
	@Pointcut("@annotation(perfAnalyzer)")
	private void pointCutMethod(PerfAnalyzer perfAnalyzer) {
	}
	@Around("pointCutMethod(perfAnalyzer)")
	public Object doAround(ProceedingJoinPoint pjp, PerfAnalyzer perfAnalyzer) throws Throwable {
		long startTime = System.currentTimeMillis();
		ThreadUtil.clean(); // 清理线程变量
		ThreadUtil.setCorrelationID(UUID.randomUUID().toString()); // 设置日志关联ID
		try {
			logger.info("correlationID[{}] {} 接口请求报文{}", ThreadUtil.getCorrelationID(), perfAnalyzer.trx().msg,
					JSON.toJSONString(pjp.getArgs()));
			Object result = pjp.proceed(pjp.getArgs());
			logger.info("correlationID[{}] {} 接口返回报文{}", ThreadUtil.getCorrelationID(), perfAnalyzer.trx().msg,
					JSON.toJSONString(result));
			long entTime = System.currentTimeMillis();
			long elapse = entTime - startTime;
			if (elapse > Long.parseLong(threadhold)) {
				logger.warn("correlationID[{}] {} 接口耗时:{} MS 耗时超长", ThreadUtil.getCorrelationID(), perfAnalyzer.trx().msg,
						entTime - startTime);
			} else {
				logger.info("correlationID[{}] {} 接口耗时:{} MS 耗时正常", ThreadUtil.getCorrelationID(), perfAnalyzer.trx().msg,
						entTime - startTime);
			}
			return result;
		} catch (Exception ex) {
			throw ex;
		} finally {
			ThreadUtil.clean();
		}
	}
}
