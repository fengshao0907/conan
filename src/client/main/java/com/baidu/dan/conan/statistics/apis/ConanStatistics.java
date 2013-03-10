package com.baidu.dan.conan.statistics.apis;

import com.baidu.dan.conan.common.core.ConanFuncsEnum;
import com.baidu.dan.conan.common.core.message.RequestMessage;
import com.baidu.dan.conan.statistics.service.ConanService;
import com.baidu.dan.conan.statistics.utils.ConanUtils;

/**
 * 
 * 对外公布的接口列表
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public final class ConanStatistics {

	private ConanStatistics() {

	}

	/**
	 * 
	 * @Description: 计算API耗时
	 * 
	 * @param serviceName
	 *            监控项的全路径，以服务名为第一个key，如service.key.subkey
	 * @param computeTime
	 *            消耗的时间,毫秒计算
	 * @return void
	 * @author liaoqiqi
	 * @date 2012-11-6
	 */
	public static void addApiConsumeTime(final String serviceName,
			final long computeTime) {

		RequestMessage requestMessage = new RequestMessage();
		requestMessage.setFuncId(ConanFuncsEnum.CONAN_API_CONSUME_TIME);
		requestMessage.setKey(serviceName);
		requestMessage.setValue(computeTime);
		requestMessage.setTimeString(ConanUtils.getCurTimeStr());

		ConanService.getInstance().sendCommand(requestMessage);
	}

	public static void close() {

		ConanService.getInstance().close();
	}
}
