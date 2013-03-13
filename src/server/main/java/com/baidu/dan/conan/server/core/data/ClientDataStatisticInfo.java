package com.baidu.dan.conan.server.core.data;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.dan.conan.common.core.ConanFuncsEnum;

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

		init(monitorAvgMap, new Double(0.0), double.class);
	}

	/**
	 * 
	 * @Description: 获取平均值表示
	 * 
	 * @return
	 * @return String
	 * @author liaoqiqi
	 * @date 2013-3-12
	 */
	@Override
	public final String getInfo() {

		StringBuffer sBuffer = new StringBuffer();

		Set<Entry<String, Double>> entrySet = monitorAvgMap.entrySet();

		for (Entry<String, Double> entry : entrySet) {

			sBuffer.append(entry.getKey());
			sBuffer.append(" ");
			sBuffer.append(entry.getValue());
			sBuffer.append(" ");
		}
		return sBuffer.toString();
	}

	@Override
	public final void compute(final ClientDataInfoBase clientDataInfoBase) {

		// 复制数据
		super.compute(clientDataInfoBase);

		// 计算平均量
		computeAvg();
	}

	private void computeAvg() {

		try {

			// 计算平均时长
			for (String key : this.monitorTimeMap.keySet()) {

				if (this.monitorTimeMap.get(key).longValue() > 0) {

					long count = this.monitorInfoMap.get(key).longValue();
					long time = this.monitorTimeMap.get(key).longValue();

					double avg = computeAvg(key, count, time);
					monitorAvgMap.put(key, avg);

					// 功能KEY，请求数，累积时间，平均时间
					// LOGGER.info(String.format(
					// "key: %s, Count: %s, Time:%s ms, Avg:%s", key,
					// count, time, avg));

				} else {
					monitorAvgMap.put(key, 0.0);
				}
			}
		} catch (Exception e) {

			LOGGER.error("error", e);
		}
	}

	/**
	 * 
	 * @Description: 不同KEY有不同的计算方法
	 * 
	 * @param key
	 * @param count
	 * @param timeConsume
	 * @return
	 * @return double
	 * @author liaoqiqi
	 * @date 2013-3-13
	 */
	private double computeAvg(String key, long count, double timeConsume) {

		double avg = 0.0;

		if (key.equals(ConanFuncsEnum.API_CONSUME_TIME.toString())) {

			// 每次请求的平均耗时
			avg = timeConsume / count;
		} else {

			// 每1秒多少次请求
			avg = count * 1000.0 / timeConsume;
		}

		return avg;
	}

	@Override
	public final String toString() {
		return "ClientDataStatisticInfo [monitorAvgMap=" + monitorAvgMap + "]";
	}
}
