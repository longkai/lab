/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps;

import cn.newgxu.lab.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import static cn.newgxu.lab.util.ViewConstants.*;

/**
 * 全局异常处理器,不管是html还是ajax，统一用json写错误信息。
 *
 * @User longkai
 * @Date 13-3-28
 * @Mail im.longkai@gmail.com
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理器，支持ajax，html的错误视图~
     * @param t 触发异常
     * @return json or html depends on what the client wants!
     */
    @ExceptionHandler(Throwable.class)
    public ModelAndView ex(Throwable t) {
        l.error("global exception occor!", t);
        ModelAndView mav = new ModelAndView(ERROR_PAGE);
        mav.addObject(STATUS, NO);
        mav.addObject(SUCCESS, false);
        mav.addObject(MSG, TextUtils.isEmpty(t.getMessage()) ? UNKNOWN_ERROR : t.getMessage());
        mav.addObject(EXP_REASON, t.getCause() == null ? null : t.getCause().getMessage());
        return mav;
    }

    private static Logger l = LoggerFactory.getLogger(GlobalExceptionHandler.class);
}
