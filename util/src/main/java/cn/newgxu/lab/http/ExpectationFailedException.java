/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 417 Expectation Failed.
 * <p>Used when the request contained an expectation in the Expect request header that the
 * server could not satisfy.</p>
 * <p>A proxy or other intermediary application can send this response code if it has
 * unambiguous evidence that the origin server will generate a failed expectation for the request
 * .</p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class ExpectationFailedException extends RuntimeException {

	public ExpectationFailedException() {
		super();
	}

	public ExpectationFailedException(String message) {
		super(message);
	}

	public ExpectationFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpectationFailedException(Throwable cause) {
		super(cause);
	}
}
