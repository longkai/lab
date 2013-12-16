/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 410 Gone.
 * <p>Similar to 404, except that the server once held the resource. Used mostly for web site
 * maintenance, so a server's administrator can notify clients when a resource has been removed
 * .</p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class GoneException extends RuntimeException {

	public GoneException() {
		super();
	}

	public GoneException(String message) {
		super(message);
	}

	public GoneException(String message, Throwable cause) {
		super(message, cause);
	}

	public GoneException(Throwable cause) {
		super(cause);
	}
}
