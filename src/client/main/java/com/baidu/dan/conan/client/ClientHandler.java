package com.baidu.dan.conan.client;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.dan.conan.common.core.message.ReplyMessage;

public class ClientHandler extends SimpleChannelUpstreamHandler {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(ClientHandler.class);

	public ClientHandler() {
	}

	@Override
	public final void messageReceived(final ChannelHandlerContext ctx,
			final MessageEvent e) {

		// 转成要处理的消息格式
		ReplyMessage billingReply;
		billingReply = (ReplyMessage) e.getMessage();

		//
		// handle 消息
		//
		LOGGER.info("client received reply : " + billingReply.toString());
	}

	@Override
	public final void exceptionCaught(final ChannelHandlerContext ctx,
			final ExceptionEvent e) {

		// Close the connection when an exception is raised.
		LOGGER.error("Unexpected exception from downstream.", e.getCause());
		e.getChannel().close();
	}

	@Override
	public final void channelConnected(final ChannelHandlerContext ctx,
			final ChannelStateEvent e) {
		//LOGGER.info("connected to server.");
	}

	@Override
	public final void channelDisconnected(final ChannelHandlerContext ctx,
			final ChannelStateEvent e) throws Exception {

		//LOGGER.info("disconnected with server.");
	}

	@Override
	public final void channelClosed(final ChannelHandlerContext ctx,
			final ChannelStateEvent e) throws Exception {

		//LOGGER.info("Close connection with server.");
	}

	@Override
	public final void handleUpstream(final ChannelHandlerContext ctx,
			final ChannelEvent e) throws Exception {

		if (e instanceof ChannelStateEvent) {
			//LOGGER.info(e.toString());
		}
		super.handleUpstream(ctx, e);
	}
}