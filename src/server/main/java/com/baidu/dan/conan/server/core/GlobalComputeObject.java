package com.baidu.dan.conan.server.core;

import java.util.ArrayList;
import java.util.List;
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
 * 全局实时计算值(单例)
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public final class GlobalComputeObject {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GlobalComputeObject.class);

	//
	// 实时计费值 map,数据对外界透明
	//
	private ConcurrentMap<Long, ClientDataInfoBase> clientStatisticsTmpMap = null;

	// 同步用
	private volatile boolean isComputeOk = false;

	private GlobalComputeObject() {

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
	public void merge() {

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
				// 进行计算
				clientDataInfoNew.compute(value);

			} else {

				// 实例化全局量
				clientDataInfoNew = new ClientDataStatisticInfo();

				// 进行计算
				clientDataInfoNew.compute(value);
				clientStatisticsTmpMap.put(key, clientDataInfoNew);
			}
		}

		isComputeOk = true;
	}

	/**
	 * 
	 * @Description: 获取结果, 供外部程序读取，计算方法对外部透明
	 * 
	 * @return
	 * @return List<String>
	 * @author liaoqiqi
	 * @date 2013-3-12
	 */
	public List<String> getResultList() {

		List<String> resultList = new ArrayList<String>();

		Set<Entry<Long, ClientDataInfoBase>> entrySet = clientStatisticsTmpMap
				.entrySet();
		for (Entry<Long, ClientDataInfoBase> entry : entrySet) {

			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append("clientid=");
			sBuffer.append(entry.getKey());
			sBuffer.append("\t");
			sBuffer.append("value=");
			sBuffer.append(entry.getValue().getInfo());
			resultList.add(sBuffer.toString());
		}

		return resultList;
	}

	public boolean isComputeOk() {
		return isComputeOk;
	}

	public void setComputeOk(final boolean isComputeOk) {
		this.isComputeOk = isComputeOk;
	}
}
