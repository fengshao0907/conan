package com.baidu.dan.conan.common.core.message;

/**
 * 基础消息
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public abstract class BaseMessage {

	// 每条日志的唯一标识，相同内容的日志的标识也是不一样的
	private String key;

	// 每条日志的时间标识，精确到秒粒度
	private String timeString;

	public final String getKey() {
		return key;
	}

	public final void setKey(final String key) {
		this.key = key;
	}

	public final String getTimeString() {
		return timeString;
	}

	public final void setTimeString(final String timeString) {
		this.timeString = timeString;
	}

	public abstract String getContent();
}
