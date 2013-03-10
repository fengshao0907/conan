package com.baidu.dan.conan.server.core.data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.dan.conan.server.config.StatisticsDataConstants;

/**
 * 实时计算量
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class ClientDataStatisticInfo extends ClientDataInfoBase {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ClientDataStatisticInfo.class);

	// 平均量(只有在计算实时量时才会存储)
	private ConcurrentMap<String, Double> monitorAvgMap = new ConcurrentHashMap<String, Double>();

	public ClientDataStatisticInfo() {

		super();

		init(monitorAvgMap, new Double(0.0));
	}

	public void compute(final ClientDataInfoBase clientDataInfoBase) {

		// 复制数据
		super.compute(clientDataInfoBase);

		// 计算平均量
		computeAvg();
	}

	private void computeAvg() {

		LOGGER.info(String.format("Count: %s, consumTime:%s ms",
				this.monitorInfoMap
						.get(StatisticsDataConstants.REQUEST_NUMBER_SUCCESS)),
				this.monitorTimeMap
						.get(StatisticsDataConstants.REQUEST_NUMBER_SUCCESS));

		// 计算平均时长
		for (String key : this.monitorTimeMap.keySet()) {

			if (this.monitorTimeMap.get(key).longValue() > 0) {

				long count = this.monitorInfoMap.get(key).longValue();
				long time = this.monitorTimeMap.get(key).longValue();
				double avg = count * 1.0 / time;

				monitorAvgMap.put(key, avg);

				// 功能KEY，请求数，累积时间，平均时间
				LOGGER.info(String.format(
						"FuncKey: %s, Count: %s, Time:%s ms, Avg:%s", key,
						count, time, avg));

			} else {
				monitorAvgMap.put(key, null);
			}
		}
	}
}
