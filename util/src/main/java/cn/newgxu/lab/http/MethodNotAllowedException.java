/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 405 Method Not Allowed.
 * <p>Used when a request is made with a method that is not supported for the requested URL. The
 * Allow header should be included in the response to tell the client what methods are allowed on
 * the requested resource.</p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class MethodNotAllowedException extends RuntimeException {

	public MethodNotAllowedException() {
		super();
	}

	public MethodNotAllowedException(String message) {
		super(message);
	}

	public MethodNotAllowedException(String message, Throwable cause) {
		super(message, cause);
	}

	public MethodNotAllowedException(Throwable cause) {
		super(cause);
	}
}
