package com.baidu.dan.conan.common.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NetBytesSwitch {

	private NetBytesSwitch() {

	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NetBytesSwitch.class);

	private static String STR_ENCODE = "GBK";

	public static byte[] intToBytes(final int x) {
		byte[] b = new byte[4];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) (x >> i * 8 & 0xFF);
		}

		return b;
	}

	public static byte[] longToBytes(final long x) {
		byte[] b = new byte[4];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) (int) (x >> i * 8 & 0xFF);
		}

		return b;
	}

	public static byte[] longToLongBytes(final long x) {
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) (int) (x >> i * 8 & 0xFF);
		}

		return b;
	}

	public static int bytesToInt(final byte[] x) {
		int iOutcome = 0;

		for (int i = 0; i < 4; i++) {
			byte bLoop = x[i];
			iOutcome += ((bLoop & 0xFF) << 8 * i);
		}

		return iOutcome;
	}

	public static long bytesToLong(final byte[] x) {
		long iOutcome = 0L;

		for (int i = 0; i < 4; i++) {
			byte bLoop = x[i];
			iOutcome += ((bLoop & 0xFF) << 8 * i);
		}

		return iOutcome;
	}

	public static byte[] stringToBytes(final String input) {
		byte[] result = (byte[]) null;
		if (input != null) {
			try {
				result = input.getBytes(STR_ENCODE);
			} catch (UnsupportedEncodingException e) {
				LOGGER.debug("StringToBytes", e);
			}
		}
		return result;
	}

	public static String bytesToString(final byte[] input) {
		String result = null;
		if (input != null) {
			try {
				result = new String(input, STR_ENCODE).trim();
			} catch (Exception e) {
				throw new RuntimeException("This jdk does not support GBK");
			}
		}
		return result;
	}

	public static byte[] stringToBytes(final String input, final int length) {
		byte[] tmp = stringToBytes(input);
		byte[] bytes = new byte[length];
		System.arraycopy(tmp, 0, bytes, 0, tmp.length);
		return bytes;
	}

	public static long bytesToLong2(final byte[] x) throws Exception {
		try {
			ByteBuffer buffer = ByteBuffer.allocate(x.length);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			buffer.put(x);
			return buffer.getLong(0);
		} catch (Exception e) {
			LOGGER.warn(e.toString());
		}
		throw new Exception("exception in transfer byte to long...");
	}
}