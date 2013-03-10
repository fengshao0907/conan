package com.baidu.dan.conan.server.core;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.dan.conan.server.core.data.ClientDataInfoBase;
import com.baidu.dan.conan.server.core.data.ClientDataStatisticInfo;

/**
 * 
 * 计算值
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class GlobalComputeObject {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GlobalComputeObject.class);

	//
	// 实时计费值
	//
	private ConcurrentMap<Long, ClientDataInfoBase> clientStatisticsTmpMap = null;

	// 同步用
	private volatile boolean isComputeOk = false;

	public void ComputeObject() {

		clientStatisticsTmpMap = new ConcurrentHashMap<Long, ClientDataInfoBase>();
	}

	//
	// 单例实现
	//

	private static class SingletonHolder {
		private static GlobalComputeObject instance = new GlobalComputeObject();
	}

	public static GlobalComputeObject getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * 
	 * @Description: 对client的每个计算值进行汇总,将数据进行合并
	 * 
	 * @return void
	 * @author liaoqiqi
	 * @date 2013-3-8
	 */
	public final void merge() {

		Set<Entry<Long, ClientDataInfoBase>> entrySet = GlobalStatisticsData
				.getInstance().getObject().entrySet();

		isComputeOk = false;

		for (Entry<Long, ClientDataInfoBase> entry : entrySet) {

			//
			// client的值
			//
			Long key = entry.getKey();
			ClientDataInfoBase value = entry.getValue();

			//
			// 计算值
			//
			ClientDataInfoBase clientDataInfoNew = null;

			if (clientStatisticsTmpMap.containsKey(key)) {

				clientDataInfoNew = clientStatisticsTmpMap.get(key);

			} else {

				clientDataInfoNew = new ClientDataStatisticInfo();
			}

			// 进行计算
			clientDataInfoNew.compute(value);
		}

		isComputeOk = true;
	}

	public boolean isComputeOk() {
		return isComputeOk;
	}

	public void setComputeOk(boolean isComputeOk) {
		this.isComputeOk = isComputeOk;
	}
}
