/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.core;

import cn.newgxu.lab.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 全局异常处理器,不管是html还是ajax，统一用json写错误信息。
 *
 * @User longkai
 * @Date 13-3-28
 * @Mail im.longkai@gmail.com
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		l.error(ex.getClass().getSimpleName(), ex.getLocalizedMessage());
		// no cache
		headers.add("Content-Type", "text/plain; charset=utf-8");
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return super.handleExceptionInternal(ex, ex.getLocalizedMessage(), headers, status, request);
	}

	/**
	 * 400 默认的异常处理
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handException(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.BAD_REQUEST, webRequest);
	}

	/**
	 * 400 Bad Request.
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequest(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.BAD_REQUEST, webRequest);
	}

	/**
	 * 401 Unauthorized.
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<?> unauthorized(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.UNAUTHORIZED, webRequest);
	}

	/**
	 * 402 Payment Required.
	 */
	@ExceptionHandler(PaymentRequiredException.class)
	public ResponseEntity<?> paymentRequired(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.PAYMENT_REQUIRED, webRequest);
	}

	/**
	 * 403 Forbidden,
	 */
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<?> forbidden(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.FORBIDDEN, webRequest);
	}

	/**
	 * 404 Not Found.
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> notFound(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.NOT_FOUND, webRequest);
	}

	/**
	 * 405 Method Not Allowed.
	 */
	@ExceptionHandler(MethodNotAllowedException.class)
	public ResponseEntity<?> methodNotAllowed(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.METHOD_NOT_ALLOWED, webRequest);
	}

	/**
	 * 406 Not Acceptable.
	 */
	@ExceptionHandler(NotAcceptableException.class)
	public ResponseEntity<?> notAcceptable(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.NOT_ACCEPTABLE, webRequest);
	}

	/**
	 * 407 Proxy Authentication Required.
	 */
	@ExceptionHandler(ProxyAuthenticationRequiredException.class)
	public ResponseEntity<?> proxyAuthenticationRequired(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.PROXY_AUTHENTICATION_REQUIRED, webRequest);
	}

	/**
	 * 408 Request Timeout.
	 */
	@ExceptionHandler(RequestTimeoutException.class)
	public ResponseEntity<?> requestTimeout(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.REQUEST_TIMEOUT, webRequest);
	}

	/**
	 * 409 Conflict.
	 */
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<?> conflict(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.CONFLICT, webRequest);
	}

	/**
	 * 410 Gone.
	 */
	@ExceptionHandler(GoneException.class)
	public ResponseEntity<?> gone(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.GONE, webRequest);
	}

	/**
	 * 411 Length Required.
	 */
	@ExceptionHandler(LengthRequiredException.class)
	public ResponseEntity<?> lengthRequired(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.LENGTH_REQUIRED, webRequest);
	}

	/**
	 * 412 Precondition Failed.
	 */
	@ExceptionHandler(PreconditionFailedException.class)
	public ResponseEntity<?> preconditionFail(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.PRECONDITION_FAILED, webRequest);
	}

	/**
	 * 413 Request Entity Too Large.
	 */
	@ExceptionHandler(RequestEntityTooLargeException.class)
	public ResponseEntity<?> requestEntityTooLarge(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.REQUEST_ENTITY_TOO_LARGE, webRequest);
	}

	/**
	 * 414 Request URI Too Long.
	 */
	@ExceptionHandler(RequestURITooLongException.class)
	public ResponseEntity<?> requestURITooLong(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.REQUEST_URI_TOO_LONG, webRequest);
	}

	/**
	 * 415 Unsupported Media Type.
	 */
	@ExceptionHandler(UnsupportedMediaTypeException.class)
	public ResponseEntity<?> unsupportedMediaType(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.UNSUPPORTED_MEDIA_TYPE, webRequest);
	}

	/**
	 * 416 Requested Range Not Satisfiable.
	 */
	@ExceptionHandler(RequestedRangeNotSatisfiableException.class)
	public ResponseEntity<?> requestedRangeNotSatisfiable(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, webRequest);
	}

	/**
	 * 417 Expectation Failed.
	 */
	@ExceptionHandler(ExpectationFailedException.class)
	public ResponseEntity<?> expectationFailed(Exception ex, WebRequest webRequest) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.EXPECTATION_FAILED, webRequest);
	}

	private static Logger l = LoggerFactory.getLogger(GlobalExceptionHandler.class);
}
