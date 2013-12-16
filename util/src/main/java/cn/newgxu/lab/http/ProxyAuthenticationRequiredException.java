/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 407 Proxy Authentication Required.
 * <p>Like the 401 status code, but used for proxy servers that require authentication for a
 * resource.</p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class ProxyAuthenticationRequiredException extends RuntimeException {

	public ProxyAuthenticationRequiredException() {
		super();
	}

	public ProxyAuthenticationRequiredException(String message) {
		super(message);
	}

	public ProxyAuthenticationRequiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProxyAuthenticationRequiredException(Throwable cause) {
		super(cause);
	}
}
