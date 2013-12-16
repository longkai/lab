/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 403 Payment Required.
 * <p>Currently this status code is not used, but it has been set aside for future use.</p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class PaymentRequiredException extends RuntimeException {

	public PaymentRequiredException() {
		super();
	}

	public PaymentRequiredException(String message) {
		super(message);
	}

	public PaymentRequiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public PaymentRequiredException(Throwable cause) {
		super(cause);
	}
}
