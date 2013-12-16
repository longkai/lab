/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 412 Precondition Failed.
 * <p>Used if a client makes a conditional request and one of the conditions fails. Conditional
 * requests occur when a client includes an Expect header.</p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class PreconditionFailedException extends RuntimeException {

	public PreconditionFailedException() {
		super();
	}

	public PreconditionFailedException(String message) {
		super(message);
	}

	public PreconditionFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PreconditionFailedException(Throwable cause) {
		super(cause);
	}
}
