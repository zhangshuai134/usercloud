package com.zs.user01.shiro;

import com.alibaba.fastjson.JSONObject;
import com.zs.user01.contant.HTTPState;
import com.zs.user01.dto.BaseResponseDTO;
import com.zs.user01.exception.ParamException;
import com.zs.user01.param.LoginParam;
import com.zs.user01.util.RedisUtil;
import com.zs.user01.util.Translator;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import static com.zs.user01.contant.WebConstant.CAPTCHA_PREFIX_KEY;
import static com.zs.user01.contant.WebConstant.LOGIN_ERROR_TIMES_PREFIX_KEY;


@Slf4j
public class KaptchaFilter extends AccessControlFilter {

    private  ServletRequest requestWrapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private RedisUtil getRedisUtilInstance(){
        return RedisUtil.getInstance(redisTemplate);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {
        return  false;
    }

    @Override
    public void doFilterInternal(ServletRequest request, ServletResponse    response, FilterChain chain) throws ServletException, IOException{
        requestWrapper= new RequestReaderHttpServletRequestWrapper((HttpServletRequest) request);
        super.doFilterInternal(requestWrapper,response,chain);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        boolean flag=false;
        BaseResponseDTO<Object> baseResponseDTO=new BaseResponseDTO<>(HTTPState.OK.getCode(),HTTPState.OK.getMsg(),null);
        String lang="en";
        try {
            requestWrapper =  new RequestReaderHttpServletRequestWrapper((HttpServletRequest) servletRequest);
            byte[] body= HttpHelper.getBodyString((HttpServletRequest) requestWrapper).getBytes(Charset.forName("UTF-8"));
            LoginParam loginParam=JSONObject.parseObject(new String(body,"UTF-8"),LoginParam.class);
            lang=loginParam.getLang();
            String key=LOGIN_ERROR_TIMES_PREFIX_KEY+loginParam.getLoginName();
            //错误登录3次必须验证码校验通过才能登录
            if(getRedisUtilInstance().get(key)!=null&&(Integer)getRedisUtilInstance().get(key)>2){
                String validateCode =  (String)getRedisUtilInstance().get(CAPTCHA_PREFIX_KEY+loginParam.getToken());
                String captchaCode = loginParam.getCaptchaCode();
                // 若验证码为空或匹配失败则返回false
                if (captchaCode == null ||StringUtils.isEmpty(validateCode)) {
                     throw new ParamException();
                }else {
                    captchaCode = captchaCode.toLowerCase();
                    validateCode = validateCode.toLowerCase();
                    if (!captchaCode.equals(validateCode)) {
                        throw new ParamException();
                    }
                }
            }
            flag=true;
            return true;
        } catch (ParamException patamException){
            baseResponseDTO=new BaseResponseDTO<>(HTTPState.CAPTCHA_ERROR.getCode(), Translator.toLocale(lang,HTTPState.CAPTCHA_ERROR.name()),new Object());
            return false;
        }catch (IOException e) {
            baseResponseDTO=new BaseResponseDTO<>(HTTPState.USERLOCKED.getCode(),Translator.toLocale(lang,HTTPState.USERLOCKED.name()),new Object());
            return false;
        }finally {
            if(!flag){
                servletResponse.setCharacterEncoding("UTF-8");
                servletResponse.setContentType("application/json; charset=utf-8");
                @Cleanup PrintWriter  out=servletResponse.getWriter();
                out.write(JSONObject.toJSONString(baseResponseDTO));
                log.warn("Warn! captcha input error!");
            }
        }
    }
}
