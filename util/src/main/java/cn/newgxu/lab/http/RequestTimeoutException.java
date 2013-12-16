/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 408 Request Timeout.
 * <p>If a client takes too long to complete its request, a server can send back this status
 * code and close down the connection. The length of this timeout varies from server to server
 * but generally is long enough to accommodate any legitimate request.</p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class RequestTimeoutException extends RuntimeException {

	public RequestTimeoutException() {
		super();
	}

	public RequestTimeoutException(String message) {
		super(message);
	}

	public RequestTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestTimeoutException(Throwable cause) {
		super(cause);
	}
}
