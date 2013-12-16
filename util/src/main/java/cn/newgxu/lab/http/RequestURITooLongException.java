/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 414 Request URI Too Long.
 * <p>Used when a client sends a request with a request URL that is larger than the server can
 * or wants to process.</p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class RequestURITooLongException extends RuntimeException {

	public RequestURITooLongException() {
		super();
	}

	public RequestURITooLongException(String message) {
		super(message);
	}

	public RequestURITooLongException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestURITooLongException(Throwable cause) {
		super(cause);
	}
}
