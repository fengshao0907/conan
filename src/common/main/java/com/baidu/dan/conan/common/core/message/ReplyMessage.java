package com.baidu.dan.conan.common.core.message;

import java.io.Serializable;

/**
 * 返回的消息
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class ReplyMessage extends BaseMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5274261826716378581L;

	private boolean isSuccess;
	private String infoString;

	public final boolean isSuccess() {
		return isSuccess;
	}

	public final void setSuccess(final boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public final String getInfoString() {
		return infoString;
	}

	public final void setInfoString(final String infoString) {
		this.infoString = infoString;
	}

	@Override
	public final String toString() {
		return "BillingReply [isSuccess=" + isSuccess + ", infoString="
				+ infoString + "]";
	}

	@Override
	public final String getContent() {
		return this.getKey() + "_" + this.toString();
	}

}
