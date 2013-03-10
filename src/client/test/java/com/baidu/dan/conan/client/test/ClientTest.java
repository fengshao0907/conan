package com.baidu.dan.conan.client.test;

import org.junit.Test;

import com.baidu.dan.conan.client.ConanClientConnector;
import com.baidu.dan.conan.common.core.ConanFuncsEnum;
import com.baidu.dan.conan.common.core.message.RequestMessage;
import com.baidu.dan.conan.common.exception.ConanException;
import com.baidu.dan.conan.statistics.utils.ConanUtils;

public class ClientTest {

	private int port = 8677;
	private String host = "127.0.0.1";

	@Test
	public final void test() {

		ConanClientConnector conanClientConnector = new ConanClientConnector(
				host, port);

		RequestMessage billingData = createNewBillingData();

		try {

			for (int i = 0; i < 1000; ++i) {

				conanClientConnector.sendCommand(billingData);
				System.out.println("message has been sent. " + i);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

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

	private static RequestMessage createNewBillingData() {

		RequestMessage billingData = new RequestMessage();

		Long timestamp = System.currentTimeMillis();
		billingData.setKey(timestamp.toString());
		billingData.setFuncId(ConanFuncsEnum.CONAN_API_CONSUME_TIME);
		billingData.setTimeString(ConanUtils.getCurTimeStr());
		billingData.setValue(333);

		return billingData;
	}

	public static void main(final String[] args) {

		ClientTest billingClientTest = new ClientTest();
		billingClientTest.test();
	}
}
