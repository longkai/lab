/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 416 Requested Range Not Satisfiable.
 * <p>Used when the request message requested a range of a given resource and that range either
 * was invalid or could not be met.</p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class RequestedRangeNotSatisfiableException extends RuntimeException {

	public RequestedRangeNotSatisfiableException() {
		super();
	}

	public RequestedRangeNotSatisfiableException(String message) {
		super(message);
	}

	public RequestedRangeNotSatisfiableException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestedRangeNotSatisfiableException(Throwable cause) {
		super(cause);
	}
}
