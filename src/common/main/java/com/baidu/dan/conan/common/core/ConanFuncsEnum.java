package com.baidu.dan.conan.common.core;

/**
 * 功能ID
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public enum ConanFuncsEnum {

	// 输入类型
	CONAN_API_CONSUME_TIME(100),

	// 输入类型
	CONAN_REQUEST_SUCCESS_CONSUME_TIME(101);

	private int value;

	private ConanFuncsEnum(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
