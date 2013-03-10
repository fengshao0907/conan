package com.baidu.dan.conan.common.core.message;

import java.io.Serializable;

/**
 * 只是一条ECHO消息
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class EchoMessage extends BaseMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1709516264471977471L;

	@Override
	public final String getContent() {
		return "";
	}
}
//