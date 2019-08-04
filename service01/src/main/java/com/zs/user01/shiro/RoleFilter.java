package com.zs.user01.shiro;

import com.alibaba.fastjson.JSON;
import com.zs.user01.contant.HTTPState;
import com.zs.user01.dto.BaseResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义过滤器覆盖默认，且转或
 */
@Slf4j
public class RoleFilter extends AuthorizationFilter {

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {

        this.out(response,new BaseResponseDTO(HTTPState.UNAUTH.getCode(),HTTPState.UNAUTH.getMsg(),null));
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
        String[] arra = (String[]) mappedValue;

        if (arra == null || arra.length == 0) {
            //没有角色限制，有权限访问
            return true;
        }

        Subject subject = getSubject(servletRequest, servletResponse);
        for (String role : arra) {
            if (subject.hasRole(role)) {
                //或
                return true;
            }
        }
        return false;
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
