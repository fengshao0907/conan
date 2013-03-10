package com.baidu.dan.conan.common.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.baidu.dan.conan.common.exception.ConanException;

/**
 * 消息头
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class NsHead {

	// 头长度
	private static final int HEADER_SIZE = 20;
	private static final int MAGIC_NUM = 0xfb709394;

	// 版本
	private short version = 1;

	// 类型
	private short type = 0;

	// 长度
	private int bodyLength = 0;

	// 保存位
	private long reserved;

	private int magicNumber = MAGIC_NUM;

	/**
	 * 
	 * @Description: 转成字节码
	 * @return
	 * @throws Exception
	 */
	public final byte[] toBytes() throws ConanException {

		ByteBuffer result = ByteBuffer.allocate(getHeaderSize());
		result.order(ByteOrder.LITTLE_ENDIAN);

		try {
			result.putShort(version);
			result.putShort(type);
			result.putLong(reserved);
			result.putInt(magicNumber);
			result.putInt(bodyLength);

		} catch (Exception e) {
			throw new ConanException("danweb billing() NSHEAD toBytes failed");
		}
		return result.array();
	}

	public NsHead(final byte[] input) throws ConanException {
		wrap(input);
	}

	public NsHead() {
	}

	/**
	 * 
	 * @Description: 将input数据设置自己
	 * @param input
	 */
	public final void wrap(final byte[] input) throws ConanException {

		ByteBuffer receiveData = ByteBuffer.allocate(getHeaderSize());
		receiveData.order(ByteOrder.LITTLE_ENDIAN);
		receiveData.put(input);
		receiveData.flip();

		this.version = receiveData.getShort();
		this.type = receiveData.getShort();
		this.reserved = receiveData.getLong();
		this.magicNumber = receiveData.getInt();
		this.bodyLength = receiveData.getInt();

		if (this.magicNumber != MAGIC_NUM) {
			throw new ConanException("magic number is wrong");
		}
	}

	public static int getHeaderSize() {
		return HEADER_SIZE;
	}

	public final short getVersion() {
		return version;
	}

	public final void setVersion(final short version) {
		this.version = version;
	}

	public final int getType() {
		return type;
	}

	public final void setType(final short type) {
		this.type = type;
	}

	public final int getBodyLength() {
		return bodyLength;
	}

	public final void setBodyLength(final int bodyLength) {
		this.bodyLength = bodyLength;
	}

	public final long getReserved() {
		return reserved;
	}

	public final void setReserved(final long reserved) {
		this.reserved = reserved;
	}

}