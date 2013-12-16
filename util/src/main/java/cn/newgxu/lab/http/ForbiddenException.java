/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 403 Forbidden,
 * <p>Used to indicate that the request was refused by the server. If the server wants to
 * indicate why the request was denied, it can include an entity body describing the reason.
 * However, this code usually is used when the server does not want to reveal the reason for the
 * refusal.</p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class ForbiddenException extends RuntimeException {

	public ForbiddenException() {
		super();
	}

	public ForbiddenException(String message) {
		super(message);
	}

	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForbiddenException(Throwable cause) {
		super(cause);
	}
}
