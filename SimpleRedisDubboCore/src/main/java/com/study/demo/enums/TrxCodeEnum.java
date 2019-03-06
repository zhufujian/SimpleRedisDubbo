package com.study.demo.enums;

/**
 * 
*
* @Description: 接口名称
* @ClassName: TrxCodeEnum 
* @author zhufj
* @date 2019年3月6日 下午2:38:18 
*
 */
public enum TrxCodeEnum {
	
	REDIS_SET("1001","REDIS存储数值方法"),
	REDIS_GET("1002","REDIS获取数值方法"),
	REDIS_REMOVE("1003","REDIS删除数值方法"),
	;
	
	public final String code;

	public final String msg;

	/** 
	* @Title:
	* @Description: 
	* @param code
	* @param msg
	*/
	private TrxCodeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
}
