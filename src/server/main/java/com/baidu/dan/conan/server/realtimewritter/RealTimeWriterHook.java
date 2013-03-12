package com.baidu.dan.conan.server.realtimewritter;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 实时输出数据的钩子对象(单例)
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public final class RealTimeWriterHook {

	private List<IRealTimeCommonWritter> writters = new ArrayList<IRealTimeCommonWritter>();

	private RealTimeWriterHook() {

		IRealTimeCommonWritter iRealTimeCommonWritter = new ConsoleRealTimeWritter();

		writters.add(iRealTimeCommonWritter);
	}

	//
	// 单例实现
	//

	private static class SingletonHolder {
		private static RealTimeWriterHook instance = new RealTimeWriterHook();
	}

	public static RealTimeWriterHook getInstance() {
		return SingletonHolder.instance;
	}

	//
	// 进行输出
	//
	public void doWork() {

		for (IRealTimeCommonWritter realTimeCommonWritter : writters) {

			if (realTimeCommonWritter != null) {
				realTimeCommonWritter.doWork();
			}
		}
	}
}
