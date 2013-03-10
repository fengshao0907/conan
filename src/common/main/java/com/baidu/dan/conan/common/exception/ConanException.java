package com.baidu.dan.conan.common.exception;

/**
 * redis counter exception
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class ConanException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -751873453695882396L;

	public ConanException() {
		super();
	}

	public ConanException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ConanException(final String message) {
		super(message);
	}

	public ConanException(final Throwable cause) {
		super(cause);
	}
}
