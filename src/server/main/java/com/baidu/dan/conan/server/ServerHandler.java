package com.baidu.dan.conan.server;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.dan.conan.common.core.message.EchoMessage;
import com.baidu.dan.conan.common.core.message.ReplyMessage;
import com.baidu.dan.conan.common.core.message.RequestMessage;
import com.baidu.dan.conan.server.handler.EchoMessageHandler;
import com.baidu.dan.conan.server.handler.RequestHandler;

/**
 * 
 * server handle
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class ServerHandler extends SimpleChannelUpstreamHandler {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServerHandler.class);

	/**
	 * 
	 * @Description: 抓取未可识别消息的返回
	 * @return
	 */
	private ReplyMessage getNoRecognizeMessageReply() {

		ReplyMessage billingReply = new ReplyMessage();

		billingReply.setInfoString("Un recognized command");
		billingReply.setSuccess(false);

		return billingReply;
	}

	/**
	 * 主Handler
	 */
	@Override
	public final void messageReceived(final ChannelHandlerContext ctx,
			final MessageEvent e) {

		@SuppressWarnings("unused")
		ReplyMessage replyMessage = null;

		// ECHO消息
		if ((e.getMessage() instanceof EchoMessage)) {

			replyMessage = EchoMessageHandler.process((EchoMessage) e
					.getMessage());

		} else if ((e.getMessage() instanceof RequestMessage)) {
			// 请求消息
			replyMessage = RequestHandler.process((RequestMessage) e
					.getMessage());

		} else {

			// 未可识别的命令
			replyMessage = getNoRecognizeMessageReply();
		}

		//
		// 不回送REPLY
		//
		// LOGGER.info("Process done: " + replyMessage);
	}

	@Override
	public final void handleUpstream(final ChannelHandlerContext ctx,
			final ChannelEvent e) throws Exception {

		if (e instanceof ChannelStateEvent) {
			LOGGER.debug(e.toString());
		}
		super.handleUpstream(ctx, e);
	}

	/**
	 * 断开连接
	 */
	@Override
	public final void channelDisconnected(final ChannelHandlerContext ctx,
			final ChannelStateEvent e) throws Exception {
		LOGGER.info("channelDisconnected");
	}

	@Override
	public final void exceptionCaught(final ChannelHandlerContext ctx,
			final ExceptionEvent e) {

		// 遇到问题，将此通道关闭
		LOGGER.warn("Unexpected exception from downstream.", e.getCause());
		e.getChannel().close();
	}

}
