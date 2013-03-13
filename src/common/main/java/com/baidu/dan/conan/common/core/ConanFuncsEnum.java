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
	API_CONSUME_TIME(100),

	// 输入类型
	REQUEST_SUCCESS_NUM(101),

	// 输入类型
	REQUEST_FAILD_NUM(102);

	private int value;

	private ConanFuncsEnum(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
