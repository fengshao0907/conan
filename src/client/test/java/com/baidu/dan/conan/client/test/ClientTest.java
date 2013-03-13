package com.baidu.dan.conan.client.test;

import java.util.Random;

import org.junit.Test;

import com.baidu.dan.conan.client.ConanClientConnector;
import com.baidu.dan.conan.common.core.ConanFuncsEnum;
import com.baidu.dan.conan.common.core.message.RequestMessage;
import com.baidu.dan.conan.common.exception.ConanException;
import com.baidu.dan.conan.statistics.utils.ConanUtils;

/**
 * 
 * 连接器级别的测试
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class ClientTest {

	private int port = 8900;
	private String host = "127.0.0.1";

	@Test
	public final void test() {

		ConanClientConnector conanClientConnector = new ConanClientConnector(
				host, port);

		try {

			long averageTime = 0;
			long totalCount = 10000;

			for (int i = 0; i < totalCount; ++i) {

				Random random = new Random();
				int sleepTime = random.nextInt(500);
				System.out.println("sleep time: " + sleepTime);
				averageTime += sleepTime;

				RequestMessage billingData = createNewBillingData(sleepTime);
				conanClientConnector.sendCommand(billingData);
				System.out.println("message has been sent. " + i);

				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			System.out.println(String.format(
					"Count: %s, CumulativeTime: %s, AverageTime: %s",
					totalCount, averageTime, averageTime * 1.0 / totalCount));

		} catch (ConanException e) {
			e.printStackTrace();
		}

		//
		// 等待一段时间再close
		//
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		conanClientConnector.close();
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

	public static void main(final String[] args) {

		ClientTest billingClientTest = new ClientTest();
		billingClientTest.test();
	}
}
