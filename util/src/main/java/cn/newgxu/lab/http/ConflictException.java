/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 409 Conflict.
 * <p>Used to indicate some conflict that the request may be causing on a resource. Servers
 * might send this code when they fear that a request could cause a conflict. The response should
 * contain a body describing the conflict.</p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class ConflictException extends RuntimeException {

	public ConflictException() {
		super();
	}

	public ConflictException(String message) {
		super(message);
	}

	public ConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConflictException(Throwable cause) {
		super(cause);
	}
}
