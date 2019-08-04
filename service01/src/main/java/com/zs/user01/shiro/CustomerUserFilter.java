package com.zs.user01.shiro;

import com.alibaba.fastjson.JSON;
import com.zs.user01.contant.HTTPState;
import com.zs.user01.dto.BaseResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Slf4j
public class CustomerUserFilter extends UserFilter {

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {

        this.out(response,new BaseResponseDTO(HTTPState.UNAUTH.getCode(),HTTPState.UNAUTH.getMsg(),null));
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
