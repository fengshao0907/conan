package com.baidu.dan.conan.common.core.message;

import java.io.Serializable;

/**
 * 无法识别的消息数据
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class UnRecognizedMessage extends BaseMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 644821407857295162L;

	@Override
	public final String getContent() {
		return "UnRecoginzed message.";
	}
}
