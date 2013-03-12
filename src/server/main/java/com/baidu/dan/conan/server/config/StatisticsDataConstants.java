package com.baidu.dan.conan.server.config;

public final class StatisticsDataConstants {

	private StatisticsDataConstants() {

	}

	/**
	 * 请求数量
	 */

	// 模块响应请求数,单位时间平均值，默认5s
	public static final String REQUEST_NUMBER_SUCCESS = "REQUEST_NUMBER_SUCCESS";
	public static final String REQUEST_NUMBER_FAILED = "REQUEST_NUMBER_FAILED";

	/**
	 * 简单的API数量
	 */
	public static final String API_NUMBER = "API_NUMBER";

}
