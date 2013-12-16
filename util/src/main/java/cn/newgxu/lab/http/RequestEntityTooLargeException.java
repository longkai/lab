/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 413 Request Entity Too Large.
 * <p>Used when a client sends an entity body that is larger than the server can or wants to
 * process.</p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class RequestEntityTooLargeException extends RuntimeException {

	public RequestEntityTooLargeException() {
		super();
	}

	public RequestEntityTooLargeException(String message) {
		super(message);
	}

	public RequestEntityTooLargeException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestEntityTooLargeException(Throwable cause) {
		super(cause);
	}
}
