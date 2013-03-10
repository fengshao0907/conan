package com.baidu.dan.conan.statistics.utils;

import java.util.Date;

import com.baidu.dan.conan.statistics.constants.ConanStatisticsConstants;

public class ConanUtils {

	private ConanUtils() {

	}

	public static String getCurTimeStr() {
		return DateUtils.formatDate(new Date(),
				ConanStatisticsConstants.SOCKET_TIME_FORMAT);
	}
}
