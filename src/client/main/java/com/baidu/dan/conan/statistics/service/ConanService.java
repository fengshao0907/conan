package com.baidu.dan.conan.statistics.service;

import com.baidu.dan.conan.client.ConanClientConnector;
import com.baidu.dan.conan.common.core.message.RequestMessage;

public final class ConanService {

	private int port = 8900;
	//private String host = "127.0.0.1";
	private String host = "10.40.70.85";

	private ConanClientConnector conanClientConnector = null;

	private ConanService() {

		conanClientConnector = new ConanClientConnector(host, port);
	}

	private static class SingletonHolder {

		private static ConanService instance = new ConanService();
	}

	public static ConanService getInstance() {
		return SingletonHolder.instance;
	}

	public void sendCommand(final RequestMessage requestMessage) {

		if (conanClientConnector != null) {

			try {
				conanClientConnector.sendCommand(requestMessage);
			} catch (Exception e) {

			}
		}
	}

	public void close() {

		if (conanClientConnector != null) {

			conanClientConnector.close();
		}
	}
}
