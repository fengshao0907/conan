package com.baidu.dan.conan.server.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.dan.conan.server.core.GlobalComputeObject;
import com.baidu.dan.conan.server.handler.RequestHandler;

public class ConsoleWritter implements ICommonWritter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConsoleWritter.class);

	@Override
	public final void doWork() throws Exception {

		//
		// get
		//

		boolean isComputeOk = GlobalComputeObject.getInstance().isComputeOk();

		RequestHandler.statisticsData.merge2Global();
		Double averageTime = RequestHandler.statisticsData.getAverageTime();

		if (averageTime != null) {
			LOGGER.info(String.format("%s ms", averageTime));
		} else {
			LOGGER.info("There are no data in client.");
		}

	}
}
