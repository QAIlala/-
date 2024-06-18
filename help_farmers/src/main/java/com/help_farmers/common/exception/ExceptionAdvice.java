package com.help_farmers.common.exception;


import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 作者：wz
 * 时间：2023/5/24 14:59
 * 描述： 原理还是aop，异常退出增强
 */
@ControllerAdvice
public class ExceptionAdvice {

    /**
     * 当Controller 发生APPException 时增强
     * 当Controller bean 的任何方法调用 抛出APPException 就来处理
     */
    @ExceptionHandler(AppException.class)
    @ResponseBody
    public Result appExceptionHandler(AppException ex, HttpServletRequest request) {
        System.out.println("进入异常处理。。。");
        return new Result(ex.getCode(),ex.getMessage(),null);
    }

    /**
     * 统一异常处理
     * 兜个底，碰到没发现的其他异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result ExceptionHandler(Exception ex, HttpServletRequest request) {
        System.out.println("进入Exception异常处理。。。" + ex.toString());
        return new Result(ResponseCode.SYSTEM_ERROR,null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result accessDeniedExceptionHandler(AccessDeniedException ex, HttpServletRequest request) {
        System.out.println("进入AccessDeniedException异常处理。。。" + ex.toString());
        return new Result(ResponseCode.NO_AUTHORITY, null);
    }

}
