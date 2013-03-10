package com.baidu.dan.conan.client.test.benchmark;

import java.util.Calendar;

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

		RequestMessage billingData = createNewBillingData();

		long startTime = Calendar.getInstance().getTimeInMillis();
		int processCounter = 0;
		try {

			for (int i = 0; i < testCaseNum; ++i) {
				billingClientConnector.sendCommand(billingData);
				processCounter++;
			}

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

	private static RequestMessage createNewBillingData() {

		RequestMessage billingData = new RequestMessage();

		Long timestamp = System.currentTimeMillis();
		billingData.setKey(timestamp.toString());
		billingData.setFuncId(ConanFuncsEnum.CONAN_API_CONSUME_TIME);
		billingData.setTimeString(ConanUtils.getCurTimeStr());
		billingData.setValue(333);

		return billingData;
	}
}
