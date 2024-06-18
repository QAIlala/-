package com.help_farmers.common.config.security.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.result.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: WZ
 * @Date: 2024/1/17 23:12
 * @Description:
 */
@Component
public class HelpFarmersAuthenticationFailedHandler extends SimpleUrlAuthenticationFailureHandler {

    @Resource
    private ThreadLocal<ResponseCode> threadLocal;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
       try {
           response.setContentType("application/json;charset=UTF-8");
           ResponseCode code = threadLocal.get();
           if (ObjectUtil.isNotNull(code)) {
               response.getWriter().write(JSONUtil.toJsonStr(new Result(code, null)));
               return;
           }
           response.getWriter().write(JSONUtil.toJsonStr(new Result(ResponseCode.USERNAME_OR_PASSWORD_INVALID, null)));
       }finally {
           threadLocal.remove();
       }
    }

}
