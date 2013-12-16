/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.notty;

import cn.newgxu.core.Spring;
import cn.newgxu.lab.util.Resources;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.InputStream;

/**
 * 校园信息运行时配置
 *
 * @User longkai
 * @Date 13-8-2
 * @Mail im.longkai@gmail.com
 */
public class Notty {

	private static JsonNode notty;

	static {
		InputStream inputStream = null;
		try {
			inputStream = Resources.getInputStream("classpath:notty.json");
			notty = Spring.OBJECT_MAPPER.readTree(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("notty app configuration error!", e);
		} finally {
			Resources.close(null, inputStream);
		}
	}

	public static final String APP = notty.get("app").asText();
	public static final String SESSION_USER = notty.get("session_user").asText();
	public static final long MAX_FILE_SIZE = notty.get("max_file_size").asInt();
	public static final String UPLOAD_RELATIVE_DIR = notty.get("upload_relative_dir").asText();
	public static final int MAX_USERS_COUNT = notty.get("max_users_count").asInt();
	public static final int DEFAULT_USERS_COUNT = notty.get("default_users_count").asInt();
	public static final int MAX_NOTICES_COUNT = notty.get("max_notices_count").asInt();
	public static final int DEFAULT_NOTICES_COUNT = notty.get("default_notices_count").asInt();
	public static final int MIN_ACCOUNT_LENGTH = notty.get("min_account_length").asInt();
	public static final int MAX_ACCOUNT_LENGTH = notty.get("max_account_length").asInt();
	public static final int MIN_PASSWORD_LENGTH = notty.get("min_password_length").asInt();
	public static final int MAX_PASSWORD_LENGTH = notty.get("max_password_length").asInt();
	public static final String PASSWORD_PRIVATE_KEY = notty.get("password_private_key").asText();
	public static final String[] ACCEPT_FILE_TYPE = notty.get("accept_file_type").asText().split(",");

	public static final String PASSWORD_RANGE = notty.get("password_range").asText();
	public static final String PASSWORDS_NOT_EQUALS = notty.get("passwords_not_equals").asText();
	public static final String OLD_PASSWORD_NOT_MATCHES = notty.get("old_password_not_matches").asText();
	public static final String ACCOUNT_RANGE = notty.get("account_range").asText();
	public static final String ORG_NOT_NULL = notty.get("org_not_null").asText();
	public static final String AUTHED_NAME_NOT_NULL = notty.get("authed_name_not_null").asText();
	public static final String ACCOUNT_EXISTS = notty.get("account_exists").asText();
	public static final String ACCOUNT_PASSWORD_NOT_MATCHES = notty.get("account_password_not_matches").asText();
	public static final String ACCOUNT_BLOCKED = notty.get("account_blocked").asText();
	public static final String USERS_ARG_ERROR = notty.get("users_arg_error").asText();
	public static final String NOTICES_ARG_ERROR = notty.get("notices_arg_error").asText();
	public static final String USER_NOT_NULL = notty.get("user_not_null").asText();
	public static final String TITLE_NOT_NULL = notty.get("title_not_null").asText();
	public static final String CONTENT_NOT_NULL = notty.get("content_not_null").asText();
	public static final String NOT_SUPPORT = notty.get("not_support").asText();
	public static final String NOT_FOUND = notty.get("not_found").asText();
	public static final String NO_PERMISSION = notty.get("no_permission").asText();
	public static final String REQUIRED_LOGIN = notty.get("required_login").asText();
	public static final String FILE_UPLOAD_FAIL = notty.get("file_upload_fail").asText();
	public static final String DELETE_FILE_ERROR = notty.get("delete_file_error").asText();

	public static final String NOTICE = "notice";
	public static final String NOTICES = "notices";
	public static final String AUTH_USER = "auth_user";
	public static final String AUTH_USERS = "auth_users";
}