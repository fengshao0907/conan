package com.baidu.dan.conan.server;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.dan.conan.server.writer.ConsoleWritter;
import com.baidu.dan.conan.server.writer.ICommonWritter;

public final class ConanOutput {

	private ConanOutput() {

	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConanOutput.class);

	private static Timer timer = new Timer();

	public static void run() {

		initTimer();
	}

	/**
	 * 
	 * @Description: 结果输出线程
	 * 
	 * @return void
	 * @author liaoqiqi
	 * @date 2013-3-4
	 */
	private static void initTimer() {

		final ICommonWritter consoleWritter = new ConsoleWritter();

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				try {
					consoleWritter.doWork();

				} catch (Exception e) {
					LOGGER.warn(e.toString());
				}
			}
		}, 1000, 5000);
	}
}
