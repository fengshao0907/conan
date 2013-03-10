package com.baidu.dan.conan.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.dan.conan.common.core.ConanCommonConstants;
import com.baidu.dan.conan.common.core.ConanMessageType;
import com.baidu.dan.conan.common.core.NsHead;
import com.baidu.dan.conan.common.core.message.BaseMessage;
import com.baidu.gson.Gson;
import com.baidu.gson.GsonBuilder;
import com.baidu.gson.JsonElement;
import com.baidu.mcpack.Mcpack;

/**
 * server使用，只会发送REPLY消息
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class ServerEncoder extends OneToOneEncoder {

	// 日志
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServerEncoder.class);

	// MCPACK
	static Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping()
			.serializeSpecialFloatingPointValues().create();

	@Override
	protected final Object encode(final ChannelHandlerContext ctx,
			final Channel channel, final Object msg) throws Exception {

		// 不是计费类型的消息，不进行解析, 直接返回
		if (!(msg instanceof BaseMessage)) {
			// Ignore what this encoder can't encode.
			return msg;
		}

		//
		// 开始解析
		//

		// 将REPLY消息封装
		JsonElement element = gson.toJsonElement(msg);
		Mcpack mcpack = new Mcpack();
		byte[] dataByte = mcpack.toMcpack(ConanCommonConstants.ENCODING,
				element);

		// 设置消息头
		NsHead nsHead = new NsHead();
		nsHead.setReserved(ConanCommonConstants.DEFAULT_RESERVED);
		nsHead.setType((short) ConanMessageType.CONAN_REPLY_TYPE_DATA
				.getValue());
		nsHead.setVersion(ConanCommonConstants.CURRENT_VERSION);
		nsHead.setBodyLength(dataByte.length);

		// 发送消息
		byte[] headerByte = nsHead.toBytes();
		ChannelBuffer cb = ChannelBuffers.wrappedBuffer(
				ChannelBuffers.LITTLE_ENDIAN, headerByte, dataByte);

		LOGGER.debug("server encode: " + element.toString());

		return cb;
	}
}
