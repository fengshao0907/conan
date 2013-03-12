package com.baidu.dan.conan.server.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.dan.conan.server.core.data.ClientDataInfo;
import com.baidu.dan.conan.server.core.data.ClientDataInfoBase;

/**
 * 系统核心值
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class GlobalStatisticsData {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GlobalStatisticsData.class);

	//
	// 客户数据汇总值，放着引用数据
	//
	private ConcurrentMap<Long, ClientDataInfoBase> clientDataInfoMap = null;

	public GlobalStatisticsData() {

		clientDataInfoMap = new ConcurrentHashMap<Long, ClientDataInfoBase>();

		defaultClient();
	}

	private void defaultClient() {

		clientDataInfoMap.put(1L, new ClientDataInfo());
	}

	public final ConcurrentMap<Long, ClientDataInfoBase> getObject() {

		return clientDataInfoMap;
	}

	//
	// 单例实现
	//

	private static class SingletonHolder {
		private static GlobalStatisticsData instance = new GlobalStatisticsData();
	}

	public static GlobalStatisticsData getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * 
	 * @Description: 增加响应成功次数，会同步增加TOTAL_SUCCESS
	 * 
	 * @param consumeTime
	 * @return void
	 * @author liaoqiqi
	 * @date 2013-3-4
	 */
	public final void addRequestNumberSuccess(final long clientId,
			final long consumeTime) {

		if (clientDataInfoMap.containsKey(clientId)) {

			clientDataInfoMap.get(clientId)
					.addRequestNumberSuccess(consumeTime);
		}
	}

	/**
	 * 
	 * @Description: 增加响应成功次数，会同步增加TOTAL_SUCCESS
	 * 
	 * @param consumeTime
	 * @return void
	 * @author liaoqiqi
	 * @date 2013-3-4
	 */
	public final void addApiNumber(final long clientId, final long consumeTime) {

		if (clientDataInfoMap.containsKey(clientId)) {

			clientDataInfoMap.get(clientId).addApiNumber(consumeTime);
		}
	}
}
