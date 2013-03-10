package com.baidu.dan.conan.server.handler;

import com.baidu.dan.conan.common.core.message.EchoMessage;
import com.baidu.dan.conan.common.core.message.ReplyMessage;

public final class EchoMessageHandler {

	private EchoMessageHandler() {

	}

	public static ReplyMessage process(final EchoMessage billingEchoData) {

		//
		// 设置要回送的消息
		//
		ReplyMessage billingReply = new ReplyMessage();
		billingReply.setKey(billingEchoData.getKey());
		billingReply.setSuccess(true);

		// result
		billingReply.setInfoString("ECHO");

		return billingReply;
	}
}
