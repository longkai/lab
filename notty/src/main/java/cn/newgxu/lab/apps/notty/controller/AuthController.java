/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.notty.controller;

import cn.newgxu.lab.apps.notty.Notty;
import cn.newgxu.lab.apps.notty.entity.AuthorizedUser;
import cn.newgxu.lab.apps.notty.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static cn.newgxu.lab.util.ViewConstants.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * 校园信息认证用户的主控制器。
 *
 * @User longkai
 * @Date 13-8-1
 * @Mail im.longkai@gmail.com
 */
@Controller
@Scope("session")
public class AuthController {


    @Inject
    private AuthService authService;

    /**
     * RESTful API，用POST请求创建一个由服务生成器URI标识的用户。
     *
     * @param user
     * @param password 确认密码
     * @return json，假设不是json，那么就会返回一个错误的html页面。
     */
    @RequestMapping(value = "/auth_users/create", method = POST, produces = MEDIA_TYPE_JSON)
    @ResponseBody
    public AuthorizedUser create(HttpServletRequest request,
                                 @ModelAttribute("user") AuthorizedUser user,
                                 @RequestParam("confirmed_password") String password) {
        l.info("trying registration! org: {}, name: {}", user.getOrg(), user.getAuthorizedName());
        authService.checkAdmin(request);
        authService.create(user, password);
        return user;
    }

    @RequestMapping(value = "/auth_users/auth", method = GET)
    public String auth(HttpServletRequest request) {
        authService.checkAdmin(request);
        return Notty.APP + "/auth";
    }

    //	管理员给用户重置密码
    @RequestMapping(value = "/auth_users/reset_password", method = PUT, produces = MEDIA_TYPE_JSON)
    @ResponseBody
    public String resetPassword(HttpServletRequest request,
                                @RequestParam("uid") long uid,
                                @RequestParam("password") String password) {
	    authService.checkAdmin(request);
        AuthorizedUser user = authService.find(uid);
        authService.resetPassword(user, password, password, null);
        return JSON_STATUS_OK;
    }

    /**
     * RESTful API，用GET请求返回一个唯一的URI标识
     *
     * @param request
     * @param password
     * @param account
     * @return only json
     */
    @RequestMapping(value = "/notice/login", method = GET, produces = MEDIA_TYPE_JSON)
    @ResponseBody
    public AuthorizedUser login(HttpServletRequest request,
                                @RequestParam("password") String password,
                                @RequestParam("account") String account) {
        String ip = request.getRemoteAddr();
        AuthorizedUser user = authService.login(account, password, ip);
        request.getSession().setAttribute(Notty.SESSION_USER, user);
        return user;
    }

    /**
     * RESTful API，使用PUT方式来退出。
     *
     * @param request
     * @return only josn
     */
    @RequestMapping(value = "/notice/logout", method = GET, produces = MEDIA_TYPE_JSON)
    @ResponseBody
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return JSON_STATUS_OK;
    }

    @RequestMapping(value = "/auth_users/{uid}/password", method = PUT, produces = MEDIA_TYPE_JSON)
    @ResponseBody
    public String modifyPassword(HttpServletRequest request,
                                 @PathVariable("uid") long uid,
                                 @RequestParam("old_password") String oldPassword,
                                 @RequestParam("new_password") String newPassword,
                                 @RequestParam("confirmed_password") String password) {
        AuthorizedUser user = authService.checkLogin(request);
        checkPermission(user, uid);
        authService.resetPassword(user, newPassword, password, oldPassword);
        return JSON_STATUS_OK;
    }

    @RequestMapping(value = "/auth_users/{uid}/profile", method = PUT)
    @ResponseBody
    public String modifyProfile(HttpServletRequest request,
                                @PathVariable("uid") long uid,
                                @RequestParam("about") String about,
                                @RequestParam("contact") String contact,
                                @RequestParam("password") String password) {
        AuthorizedUser user = authService.checkLogin(request);
        checkPermission(user, uid);

        user.setAbout(about);
        user.setContact(contact);
        authService.update(user, password);
        return JSON_STATUS_OK;
    }

    @RequestMapping(value = "/auth_users/{uid}/modify", method = GET)
    public String modify(Model model, HttpServletRequest request,
                         @PathVariable("uid") long uid) {
        AuthorizedUser auth_user = authService.checkLogin(request);
        checkPermission(auth_user, uid);
	    System.err.println(auth_user);
	    model.addAttribute(Notty.AUTH_USER, auth_user);
        return Notty.APP + "/auth_user_modifying";
    }

    @RequestMapping(value = "/auth_users/{uid}", method = GET, produces = MEDIA_TYPE_JSON)
    @ResponseBody
    public AuthorizedUser profile(Model model, @PathVariable("uid") long uid) {
        return authService.find(uid);
    }

    @RequestMapping(value = "/auth_users/view", method = GET)
    public String viewUsers(Model model, HttpServletRequest request) {
        authService.checkLogin(request);
        List<AuthorizedUser> auth_users = authService.users(AuthService.ALL, 20);
        model.addAttribute(Notty.AUTH_USERS, auth_users);
        return Notty.APP + "/auth_users";
    }

    @RequestMapping(value = "/auth_users", method = GET, produces = MEDIA_TYPE_JSON)
    public String users(Model model,
                                      @RequestParam("type") int type,
                                      @RequestParam("count") int count,
                                      @RequestParam(value = "offset", defaultValue = "0") long offset,
                                      @RequestParam(value = "append", defaultValue = "false") boolean append) {
        List<AuthorizedUser> auth_users =
                offset <= 0 ? authService.users(type, count)
                        : authService.users(type, count, append, offset);
        model.addAttribute(Notty.AUTH_USERS, auth_users);
        return BAD_REQUEST;
    }

    private static void checkPermission(AuthorizedUser user, long uid) {
        if (uid != user.getId()) {
            throw new SecurityException(Notty.NO_PERMISSION);
        }
    }

    private static Logger l = LoggerFactory.getLogger(AuthController.class);
}
