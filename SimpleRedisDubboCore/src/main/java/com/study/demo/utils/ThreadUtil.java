package com.study.demo.utils;

/**
 * 
*
* @Description: 线程工具
* @ClassName: ThreadUtil 
* @author zhufj
* @date 2019年3月6日 下午2:42:35 
*
 */
public class ThreadUtil {
	private static ThreadLocal<String> correlationID = new ThreadLocal<String>();
	private static ThreadLocal<String> tag = new ThreadLocal<String>();

	public static void setCorrelationID(String correlationID) {
		ThreadUtil.correlationID.set(correlationID);
	}

	public static String getCorrelationID() {
		return ThreadUtil.correlationID.get();
	}

	public static void setTag(String tag) {
		ThreadUtil.tag.set(tag);
	}

	public static String getTag() {
		return ThreadUtil.tag.get();
	}

	public static void clean() {
		ThreadUtil.correlationID.remove();
		ThreadUtil.tag.remove();
	}
}
