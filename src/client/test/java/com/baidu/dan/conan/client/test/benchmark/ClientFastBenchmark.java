package com.baidu.dan.conan.client.test.benchmark;

import java.util.Calendar;
import java.util.Random;

import com.baidu.dan.conan.client.ConanClientConnector;
import com.baidu.dan.conan.common.core.ConanFuncsEnum;
import com.baidu.dan.conan.common.core.message.RequestMessage;
import com.baidu.dan.conan.common.exception.ConanException;
import com.baidu.dan.conan.statistics.utils.ConanUtils;

public final class ClientFastBenchmark {

	private ClientFastBenchmark() {

	}

	public static void main(final String[] args) throws Exception {

		if (args.length != 3) {
			System.out.println("parameter error: ip port");
			return;
		}

		String curHost = args[0];
		Integer curPort = Integer.parseInt(args[1]);
		Integer testCaseNum = Integer.parseInt(args[2]);

		System.out.println("using:" + curHost + " " + curPort + ", test num: "
				+ testCaseNum);

		// 连接器
		ConanClientConnector billingClientConnector = new ConanClientConnector(
				curHost, curPort);

		long startTime = Calendar.getInstance().getTimeInMillis();
		int processCounter = 0;

		try {

			long averageTime = 0;
			long totalCount = 10000;

			for (int i = 0; i < testCaseNum; ++i) {

				Random random = new Random();
				int sleepTime = random.nextInt(50000);
				averageTime += sleepTime;

				RequestMessage billingData = createNewBillingData(sleepTime);
				billingClientConnector.sendCommand(billingData);
				processCounter++;
			}

			System.out.println(String.format(
					"Count: %s, CumulativeTime: %s, AverageTime: %s",
					totalCount, averageTime, averageTime * 1.0 / totalCount));

		} catch (ConanException e) {
			e.printStackTrace();
		}
		long useTime = Calendar.getInstance().getTimeInMillis() - startTime;
		float speed = testCaseNum * 1000.0f / useTime;

		//
		System.out.println("Client has process " + processCounter
				+ " items. Speed: " + speed);

		// 关闭服务
		billingClientConnector.close();

	}

	private static RequestMessage createNewBillingData(final long consumeTime) {

		RequestMessage billingData = new RequestMessage();

		Long timestamp = System.currentTimeMillis();
		billingData.setKey(timestamp.toString());
		billingData.setClientId(1L);
		billingData.setFuncId(ConanFuncsEnum.API_CONSUME_TIME);
		billingData.setTimeString(ConanUtils.getCurTimeStr());
		billingData.setValue(consumeTime);

		return billingData;
	}
}
