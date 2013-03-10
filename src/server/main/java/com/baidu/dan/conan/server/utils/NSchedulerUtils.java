package com.baidu.dan.conan.server.utils;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public final class NSchedulerUtils {

	private NSchedulerUtils() {
	}

	/**
	 * 
	 * @Description: 调度任务运行, 在指定的小时、分钟开始运行，有时间间隔。如果今天时间点已经过了，则从明天开始运行, 支持立即运行.
	 * @param timer
	 * @param task
	 * @param hour
	 */
	public static void taskScheduler(final Timer timer, final TimerTask task,
			final int hour, final int miniute, final int interval,
			final Boolean isAtOnce) {

		if (isAtOnce == null || isAtOnce.equals(true)) {
			// run at once
			task.run();
		}

		// run every day at specified time
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, miniute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.HOUR_OF_DAY, hour);

		Calendar currentCal = Calendar.getInstance();
		if (!currentCal.before(calendar)) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

		timer.scheduleAtFixedRate(task, calendar.getTime(), interval);
	}

}
