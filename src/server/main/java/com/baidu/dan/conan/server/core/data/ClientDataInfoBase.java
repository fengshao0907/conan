package com.baidu.dan.conan.server.core.data;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.baidu.dan.conan.common.core.ConanFuncsEnum;

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

		init(monitorTimeMap, new AtomicLong(0L), long.class);
		init(monitorInfoMap, new AtomicLong(0L), long.class);
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
			U initValue, Class parameter) {

		//
		// 模块响应请求数
		//
		map.put(ConanFuncsEnum.REQUEST_SUCCESS_NUM.toString(),
				getNewInstance(initValue, parameter));
		map.put(ConanFuncsEnum.REQUEST_FAILD_NUM.toString(),
				getNewInstance(initValue, parameter));

		//
		// api
		//
		map.put(ConanFuncsEnum.API_CONSUME_TIME.toString(),
				getNewInstance(initValue, parameter));
	}

	/**
	 * 
	 * @Description: 生成一个新实例
	 * 
	 * @param <U>
	 * @param initValue
	 * @param parameter
	 * @return
	 * @return U
	 * @author liaoqiqi
	 * @date 2013-3-12
	 */
	private <U extends Number> U getNewInstance(U initValue, Class parameter) {

		U newInstance = null;
		try {

			Constructor con = initValue.getClass().getConstructor(
					new Class[] { parameter });
			newInstance = (U) con.newInstance(new Object[] { 0 });

		} catch (Exception e) {
			e.printStackTrace();
		}
		return newInstance;
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

				AtomicLong atomicLong = new AtomicLong();
				atomicLong.set(srcMap.get(key).longValue());
				destMap.put(key, atomicLong);

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

		addRequestNumberSuccess(
				ConanFuncsEnum.REQUEST_SUCCESS_NUM.toString(),
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

		addRequestNumberSuccess(
				ConanFuncsEnum.API_CONSUME_TIME.toString(), consumeTime);
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
