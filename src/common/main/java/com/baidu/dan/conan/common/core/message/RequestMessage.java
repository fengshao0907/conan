package com.baidu.dan.conan.common.core.message;

import java.io.Serializable;

import com.baidu.dan.conan.common.core.ConanFuncsEnum;

/**
 * 请求数据
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class RequestMessage extends BaseMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -441470849634517190L;

	/**
	 * 客户ID
	 */
	private long clientId;

	/**
	 * 功能ID
	 */
	private ConanFuncsEnum funcId;

	/**
	 * 消息的值
	 */
	private long value;

	public final long getClientId() {
		return clientId;
	}

	public final void setClientId(final long clientId) {
		this.clientId = clientId;
	}

	public final ConanFuncsEnum getFuncId() {
		return funcId;
	}

	public final void setFuncId(final ConanFuncsEnum funcId) {
		this.funcId = funcId;
	}

	public final long getValue() {
		return value;
	}

	public final void setValue(final long value) {
		this.value = value;
	}

	@Override
	public final String toString() {
		return "ConanRequestMessage [funcId=" + clientId + ", value=" + value
				+ "]";
	}

	@Override
	public final String getContent() {
		return this.getKey() + "_" + this.toString();
	}
}
