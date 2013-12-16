/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.http;

/**
 * 415 Unsupported Media Type.
 * <p>Used when a client sends an entity of a content type that the server does not understand
 * or support.</p>
 *
 * @author longkai
 * @date 2013-12-15
 */
public class UnsupportedMediaTypeException extends RuntimeException {

	public UnsupportedMediaTypeException() {
		super();
	}

	public UnsupportedMediaTypeException(String message) {
		super(message);
	}

	public UnsupportedMediaTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedMediaTypeException(Throwable cause) {
		super(cause);
	}
}
