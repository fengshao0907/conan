package com.baidu.dan.conan.common.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.dan.conan.common.core.ConanCommonConstants;
import com.baidu.dan.conan.common.core.ConanMessageType;
import com.baidu.dan.conan.common.core.NsHead;
import com.baidu.dan.conan.common.core.message.BaseMessage;
import com.baidu.dan.conan.common.core.message.EchoMessage;
import com.baidu.dan.conan.common.core.message.ReplyMessage;
import com.baidu.dan.conan.common.core.message.RequestMessage;
import com.baidu.dan.conan.common.core.message.UnRecognizedMessage;
import com.baidu.gson.Gson;
import com.baidu.gson.GsonBuilder;
import com.baidu.gson.JsonElement;
import com.baidu.mcpack.Mcpack;

/**
 * 
 * 解码, server 和 client 共同使用
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class Decoder extends ReplayingDecoder<Decoder.DecodingState> {

	// 日志
	private static final Logger LOGGER = LoggerFactory.getLogger(Decoder.class);

	// 消息头
	private NsHead nsHead = new NsHead();

	// MCPACK
	private static Gson gson = new GsonBuilder().serializeNulls()
			.disableHtmlEscaping().serializeSpecialFloatingPointValues()
			.create();

	public Decoder() {
		super(DecodingState.ID);
	}

	/**
	 * 
	 */
	@Override
	protected final Object decode(final ChannelHandlerContext ctx,
			final Channel channel, final ChannelBuffer buffer,
			final DecodingState state) throws Exception {

		ChannelBuffer charBuffer = null;

		switch (state) {

		//
		// 开始读取头,CLIENT和SERVER都可能收到
		//
		case ID:

			// 未读取到，则返回
			if (buffer.readableBytes() < NsHead.getHeaderSize()) {
				return null;
			}

			// 解析消息头
			nsHead = new NsHead(buffer.readBytes(NsHead.getHeaderSize())
					.array());

			//
			// 判断类型
			//

			// 请求数据
			if (nsHead.getType() == ConanMessageType.CONAN_REQUEST_TYPE
					.getValue()) {

				checkpoint(DecodingState.REQUEST_MESSAGE);
				return null;

			} else if (nsHead.getType() == ConanMessageType.CONAN_ECHO_TYPE
					.getValue()) {
				// echo 消息
				checkpoint(DecodingState.ECHO_MESSAGE);
				return null;

				// REPLY
			} else if (nsHead.getType() == ConanMessageType.CONAN_REPLY_TYPE_DATA
					.getValue()) {
				checkpoint(DecodingState.REPLY);
				return null;

			} else {

				// 错误的类型，无法识别
				checkpoint(DecodingState.UNRECOGNIZE_MESSAGE);
				return null;
			}

			//
			// ECHO for heartbeat
			//
		case ECHO_MESSAGE:

			// 读取
			charBuffer = buffer.readBytes(nsHead.getBodyLength());
			byte[] bytes = charBuffer.array();

			// 转成PACK
			Mcpack pack = new Mcpack();
			JsonElement jsonElement = pack.toJsonElement(
					ConanCommonConstants.ENCODING, bytes);
			BaseMessage echoData = gson.fromJson(jsonElement,
					new EchoMessage().getClass());

			LOGGER.debug("server decode: " + echoData.toString());

			// 重置，准备读取下个数据
			reset();
			return echoData;

			//
			// 消息数据正文
			// 只有SERVER才可能收到消息正文，CLIENT不可能收到
			//
		case REQUEST_MESSAGE:

			// 读取
			charBuffer = buffer.readBytes(nsHead.getBodyLength());
			bytes = charBuffer.array();

			// for (int i = 0; i < bytes.length; ++i) {
			// System.out.print(bytes[i] + " ");
			// }
			// System.out.println();

			// 转成REQIEST
			pack = new Mcpack();
			jsonElement = pack.toJsonElement(ConanCommonConstants.ENCODING,
					bytes);
			BaseMessage requestData = gson.fromJson(jsonElement,
					new RequestMessage().getClass());

			// LOGGER.debug("server decode: " + requestData.toString());

			// 重置，准备读取下个数据
			reset();
			return requestData;

			//
			// REPLY
			// 只有client才可能收到REPLY，SERVER不可能收到REPLY
			//
		case REPLY:

			// 读取
			charBuffer = buffer.readBytes(nsHead.getBodyLength());
			bytes = charBuffer.array();

			// 转成PACK
			pack = new Mcpack();
			jsonElement = pack.toJsonElement(ConanCommonConstants.ENCODING,
					bytes);
			BaseMessage replyMessage = gson.fromJson(jsonElement,
					new ReplyMessage().getClass());

			// LOGGER.debug("client decode: " + replyMessage.toString());

			// 重置，准备读取下个数据
			reset();
			return replyMessage;

			//
			// DISCARD
			// 只有client才可能收到REPLY，SERVER不可能收到REPLY
			//
		case UNRECOGNIZE_MESSAGE:

			// 读取
			charBuffer = buffer.readBytes(nsHead.getBodyLength());
			bytes = charBuffer.array();

			// 转成PACK
			pack = new Mcpack();
			jsonElement = pack.toJsonElement(ConanCommonConstants.ENCODING,
					bytes);
			BaseMessage unrecognizeMessage = gson.fromJson(jsonElement,
					new UnRecognizedMessage().getClass());

			// LOGGER.debug("client decode: " + unrecognizeMessage.toString());

			// 重置，准备读取下个数据
			reset();
			return unrecognizeMessage;

		default:
			throw new Exception("Unknown decoding state: " + state);
		}
	}

	/**
	 * 重置
	 * 
	 * @Description:
	 */
	private void reset() {
		checkpoint(DecodingState.ID);
		this.nsHead = new NsHead();
	}

	/**
	 * 验证状态
	 * 
	 * @author liaoqiqi
	 * @email liaoqiqi@baidu.com
	 * 
	 */
	protected static enum DecodingState {
		ID, REQUEST_MESSAGE, ECHO_MESSAGE, UNRECOGNIZE_MESSAGE, REPLY;
	}
}
