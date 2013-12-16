/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 406 Not Acceptable.
 * <p>Clients can specify parameters about what types of entities they are willing to accept.
 * This code is used when the server has no resource matching the URL that is acceptable for the
 * client. Often, servers include headers that allow the client to figure out why the request
 * could not be satisfied. </p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class NotAcceptableException extends RuntimeException {

	public NotAcceptableException() {
		super();
	}

	public NotAcceptableException(String message) {
		super(message);
	}

	public NotAcceptableException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotAcceptableException(Throwable cause) {
		super(cause);
	}
}
