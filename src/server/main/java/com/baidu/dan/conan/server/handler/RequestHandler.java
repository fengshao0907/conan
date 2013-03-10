package com.baidu.dan.conan.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.dan.conan.common.core.message.ReplyMessage;
import com.baidu.dan.conan.common.core.message.RequestMessage;
import com.baidu.dan.conan.server.core.GlobalStatisticsData;

/**
 * 
 * 处理器
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public final class RequestHandler {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RequestHandler.class);

	private RequestHandler() {
	}

	/**
	 * 
	 * @Description: 发送请求
	 * @param billingData
	 * @return
	 */
	public ReplyMessage process(final RequestMessage requestMessage) {

		// logger.info("server hanlder. It's key and type info:"
		// + requestMessage.getContent());

		switch (requestMessage.getFuncId()) {

		//
		// 响应成功数
		//
		case CONAN_REQUEST_SUCCESS_CONSUME_TIME:

			GlobalStatisticsData.getInstance().addRequestNumberSuccess(
					requestMessage.getClientId(), requestMessage.getValue());
			break;

		//
		// API请求数
		//
		case CONAN_API_CONSUME_TIME:

			GlobalStatisticsData.getInstance().addRequestNumberSuccess(
					requestMessage.getClientId(), requestMessage.getValue());
			break;

		default:
			break;
		}

		// 获取默认的返回值
		ReplyMessage billingReply = new ReplyMessage();

		return billingReply;
	}
}
