package com.baidu.dan.conan.server.core.data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.baidu.dan.conan.server.config.StatisticsDataConstants;

/**
 * 基本量
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public abstract class ClientDataInfoBase {

	// 时间量
	protected ConcurrentMap<String, AtomicLong> monitorTimeMap = new ConcurrentHashMap<String, AtomicLong>();

	// 统计量
	protected ConcurrentMap<String, AtomicLong> monitorInfoMap = new ConcurrentHashMap<String, AtomicLong>();

	/**
	 * 构造法
	 */
	public ClientDataInfoBase() {

		init(monitorTimeMap, new AtomicLong(0L));
		init(monitorInfoMap, new AtomicLong(0L));
	}

	/**
	 * 
	 * @Description: 初始化
	 * 
	 * @param map
	 * @return void
	 * @author liaoqiqi
	 * @date 2013-3-8
	 */
	protected <U extends Number> void init(final ConcurrentMap<String, U> map,
			U initValue) {

		//
		// 模块响应请求数
		//
		map.put(StatisticsDataConstants.REQUEST_NUMBER_SUCCESS, initValue);
		map.put(StatisticsDataConstants.REQUEST_NUMBER_FAILED, initValue);
		map.put(StatisticsDataConstants.REQUEST_NUMBER_TOTAL_SUCCESS, initValue);
		map.put(StatisticsDataConstants.REQUEST_NUMBER_TOTAL_FAILED, initValue);
	}

	public String getInfo() {
		return "";
	}

	/**
	 * 
	 * @Description: 复制一份 并清除0
	 * 
	 * @param clientDataInfoBase
	 * @return void
	 * @author liaoqiqi
	 * @date 2013-3-8
	 */
	public void compute(final ClientDataInfoBase clientDataInfoBase) {

		if (clientDataInfoBase != null) {

			copyAndCleanMap(clientDataInfoBase.monitorInfoMap,
					this.monitorInfoMap);
			copyAndCleanMap(clientDataInfoBase.monitorTimeMap,
					this.monitorTimeMap);

		}
	}

	/**
	 * 
	 * @Description: 复制MAP并将数据清0
	 * 
	 * @param srcMap
	 * @param destMap
	 * @return void
	 * @author liaoqiqi
	 * @date 2013-3-8
	 */
	private void copyAndCleanMap(
			final ConcurrentMap<String, AtomicLong> srcMap,
			final ConcurrentMap<String, AtomicLong> destMap) {

		if (srcMap != null && destMap != null) {

			for (String key : srcMap.keySet()) {

				destMap.put(key, srcMap.get(key));

				srcMap.get(key).set(0L);
			}
		}
	}

	/**
	 * 
	 * @Description: 增加响应成功次数，会同步增加TOTAL_SUCCESS
	 * 
	 * @param consumeTime
	 * @return void
	 * @author liaoqiqi
	 * @date 2013-3-5
	 */
	public final void addRequestNumberSuccess(final long consumeTime) {

		addRequestNumberSuccess(StatisticsDataConstants.REQUEST_NUMBER_SUCCESS,
				consumeTime);
		addRequestNumberSuccess(
				StatisticsDataConstants.REQUEST_NUMBER_TOTAL_SUCCESS,
				consumeTime);
	}

	/**
	 * 
	 * @Description: 增加API响应次数
	 * 
	 * @param consumeTime
	 * @return void
	 * @author liaoqiqi
	 * @date 2013-3-5
	 */
	public final void addApiNumber(final long consumeTime) {

		addRequestNumberSuccess(StatisticsDataConstants.API_NUMBER, consumeTime);
	}

	private void addRequestNumberSuccess(final String key,
			final long consumeTime) {

		monitorInfoMap.get(key).incrementAndGet();
		monitorTimeMap.get(key).getAndAdd(consumeTime);
	}

	@Override
	public String toString() {
		return "ClientDataInfoBase [monitorTimeMap=" + monitorTimeMap
				+ ", monitorInfoMap=" + monitorInfoMap + "]";
	}
}
