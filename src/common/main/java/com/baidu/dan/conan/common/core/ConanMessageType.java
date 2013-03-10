package com.baidu.dan.conan.common.core;

/**
 * 消息类型ENUM
 * 
 * @author liaoqiqi
 * 
 */
public enum ConanMessageType {

	// 输入类型
	CONAN_REQUEST_TYPE(100),

	// 输入类型
	CONAN_ECHO_TYPE(200),

	// 输出类型
	CONAN_REPLY_TYPE_DATA(1000);

	private int value;

	private ConanMessageType(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
