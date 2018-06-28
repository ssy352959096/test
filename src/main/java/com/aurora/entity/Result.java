package com.aurora.entity;
/**
 * 返回结果集
 * @author SSY 2017-12-5
 *
 * @param <T>
 */
public class Result<T> {

	public static final String STATE_SUCCESS = "success";
	public static final String STATE_ERROR = "error";
	public static final String STATE_FAILED = "failed";

	/**
	 * 返回提示信息;
	 */
	private String msg;
	/**
	 * 响应状态,error错误,failed错误请求,success成功;
	 */
	private String state; 
	/**
	 * 返回参数
	 */
	private T result;
	 
	/**
	 * 获取: message
	 * @return the message
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置: message
	 * @param message the message to set
	 */
	public void setMsg(String message) {
		this.msg = message;
	}

	/**
	 * 获取: state
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * 设置: state
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 获取: result
	 * @return the result
	 */
	public T getResult() {
		return result;
	}

	/**
	 * 设置: result
	 * @param result the result to set
	 */
	public void setResult(T result) {
		this.result = result;
	}


}
