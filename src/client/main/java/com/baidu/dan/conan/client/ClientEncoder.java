package com.baidu.dan.conan.client;

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
import com.baidu.gson.Gson;
import com.baidu.gson.GsonBuilder;
import com.baidu.gson.JsonElement;
import com.baidu.mcpack.Mcpack;

/**
 * CLIENT使用的ENCODE，发出计费数据
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class ClientEncoder extends OneToOneEncoder {

	// 日志
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ClientEncoder.class);

	// MCPACK
	private static Gson gson = new GsonBuilder().serializeNulls()
			.disableHtmlEscaping().serializeSpecialFloatingPointValues()
			.create();

	@Override
	protected final Object encode(final ChannelHandlerContext ctx,
			final Channel channel, final Object msg) throws Exception {

		// 将消息封装
		JsonElement element = gson.toJsonElement(msg);
		Mcpack mcpack = new Mcpack();
		byte[] dataByte = mcpack.toMcpack(ConanCommonConstants.ENCODING,
				element);

		// LOGGER.debug("client encode: " + element.toString());

		// 设置消息头 NSHEAD
		NsHead nsHead = new NsHead();
		nsHead.setBodyLength(dataByte.length);
		nsHead.setReserved(ConanCommonConstants.DEFAULT_RESERVED);
		nsHead.setType((short) ConanMessageType.CONAN_REQUEST_TYPE.getValue());
		nsHead.setVersion(ConanCommonConstants.CURRENT_VERSION);
		byte[] headerByte = nsHead.toBytes();

		// 发送消息头与正文
		ChannelBuffer cb = ChannelBuffers.wrappedBuffer(
				ChannelBuffers.LITTLE_ENDIAN, headerByte, dataByte);

		return cb;
	}
}
