package com.baidu.dan.conan.server.realtimewritter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.dan.conan.server.core.GlobalComputeObject;

public class ConsoleRealTimeWritter implements IRealTimeCommonWritter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConsoleRealTimeWritter.class);

	@Override
	public final void doWork() {

		//
		// get
		//

		boolean isComputeOk = GlobalComputeObject.getInstance().isComputeOk();

		if (isComputeOk) {

			List<String> resultList = GlobalComputeObject.getInstance()
					.getResultList();
			for (String result : resultList) {

				LOGGER.info(result);
			}
		}
	}
}
