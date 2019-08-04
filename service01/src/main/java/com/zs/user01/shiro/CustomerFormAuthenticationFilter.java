package com.zs.user01.shiro;

import com.alibaba.fastjson.JSON;
import com.zs.user01.contant.HTTPState;
import com.zs.user01.dto.BaseResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class CustomerFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            if (this.isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }

                return this.executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }

                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
            }

            this.out(response,new BaseResponseDTO(HTTPState.UNAUTH.getCode(),HTTPState.UNAUTH.getMsg(),null));
            return false;
        }
    }

    private void out(ServletResponse hresponse, BaseResponseDTO baseResponseDTO)
            throws IOException {
        try {
            HttpServletResponse res = (HttpServletResponse)hresponse;
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setContentType("application/json; charset=utf-8");
            res.setCharacterEncoding("UTF-8");
            PrintWriter out = hresponse.getWriter();
            out.write(JSON.toJSONString(baseResponseDTO));
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("Error!auth Exception >>"+e.getMessage(),e);
        }
    }
}
