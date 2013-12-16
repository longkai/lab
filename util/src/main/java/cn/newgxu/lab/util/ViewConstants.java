/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.util;

/**
 * 一些视图常量，一般都是lab中约定的。
 *
 * @User longkai
 * @Date 13-3-29
 * @Mail im.longkai@gmail.com
 */
public class ViewConstants {

	/**
	 * html格式，包含utf8编码
	 */
	public static final String MEDIA_TYPE_HTML = "text/html; charset=utf-8";

	/**
	 * json格式，包含utf8编码
	 */
	public static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";

	/**
	 * ajax请求的状态
	 */
	public static final String STATUS = "status";

	/**
	 * ajax请求返回的信息键
	 */
	public static final String MSG = "msg";

	public static final String SUCCESS = "success";

	/**
	 * 产生异常的原因
	 */
	public static final String EXP_REASON = "reason";

	/**
	 * 默认的ajax请求成功标记
	 */
	public static final String JSON_STATUS_OK = "{\"status\":1,\"success\":true}";

	/**
	 * 默认的ajax请求失败标记
	 */
	public static final String JSON_STATUS_NO = "{\"status\":0,\"success\":false}";

	/**
	 * 错误状态码
	 */
	public static final int NO = 0;
	/**
	 * 正确状态码
	 */
	public static final int OK = 1;

	/**
	 * 默认的登录拦截成功信息
	 */
	public static final String JSON_STATUS_NON_LOGIN = "{\"status\":0,\"msg\":\"non_login\",\"reason\":\"请您登陆后再进行相关操作！\"}";

	/**
	 * 用于只返回ajax视图而不返回默认的html视图
	 */
	public static final String BAD_REQUEST = "bad_request";

	public static final String UNKNOWN_ERROR = "对不起，出错了，未知的原因，请您联系管理员或者稍后再试！";

	/**
	 * 默认的错误提示视图名
	 */
	public static final String ERROR_PAGE = "error";
}
