/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.notty.service;

import cn.newgxu.lab.notty.entity.AuthorizedUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 校园信息认证用户服务接口。
 *
 * @User longkai
 * @Date 13-3-28
 * @Mail im.longkai@gmail.com
 */
public interface AuthService {

	/**
	 * 查询最新的列表
	 */
	int LIST_LATEST = 1;

	/**
	 * 用于获取更多用户
	 */
	int LIST_MORE = 2;

	/**
	 * 查询最新的用户列表列表（不包括屏蔽的）
	 */
	int LATEST = 1;

	/**
	 * 只查看被屏蔽用户列表
	 */
	int BLOCKED = 2;

	/**
	 * 所有用户的列表（包含屏蔽和未被屏蔽的）
	 */
	int ALL = 3;

	/**
	 * 默认按照注册时间降序
	 */
	String DEFAULT_ORDER_BY = "id DESC";


	void create(AuthorizedUser user, String confirmedPassword);

	/**
	 * 修改密码
	 */
	void resetPassword(AuthorizedUser user, String newPassword, String confirmPassword, String oldPassword);

	/**
	 * 修改个人信息
	 */
	void update(AuthorizedUser user, String plainPassword);

	/**
	 * 将一个用户账号冻结掉。
	 */
	void toggleBlock(AuthorizedUser user);

	AuthorizedUser find(long pk);

	AuthorizedUser login(String account, String password, String ip);

	long total();

	AuthorizedUser checkLogin(HttpServletRequest request);

	AuthorizedUser checkAdmin(HttpServletRequest request);

	List<AuthorizedUser> users(int type, int count);

	List<AuthorizedUser> users(int type, int count, boolean append, long offset);

	List<AuthorizedUser> sync(long lastTimestamp, int count);
}
