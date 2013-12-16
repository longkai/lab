/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 411 Length Required.
 * <p>Used when the server requires a Content-Length header in the request message.</p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class LengthRequiredException extends RuntimeException {

	public LengthRequiredException() {
		super();
	}

	public LengthRequiredException(String message) {
		super(message);
	}

	public LengthRequiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public LengthRequiredException(Throwable cause) {
		super(cause);
	}
}
