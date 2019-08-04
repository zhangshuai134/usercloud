package com.zs.user01.shiro;

import com.alibaba.fastjson.JSON;
import com.zs.user01.contant.HTTPState;
import com.zs.user01.dto.BaseResponseDTO;
import com.zs.user01.dto.SysUserDTO;
import com.zs.user01.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

@Slf4j
@Component
public class SessionControlFilter extends AccessControlFilter {
    private String kickoutUrl; //踢出后到的地址
    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数 默认1
    private final  String keyPrefix="new_kickout_session_key:";
    private SessionManager sessionManager;

    @Autowired
    private RedisTemplate redisTemplate;

    private RedisUtil getRedisUtilInstance() {
        return RedisUtil.getInstance(redisTemplate);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }
        Session session = subject.getSession();
        SysUserDTO user = (SysUserDTO) subject.getPrincipal();
        String username = user.getLoginName();
        String key=keyPrefix+username;
        Serializable sessionId = session.getId();

        Deque<Serializable> deque=(Deque<Serializable>)getRedisUtilInstance().get(key);
//        //读取缓存   没有就存入
        //如果此用户没有session队列，也就是还没有登录过，缓存中没有
        //就new一个空队列，不然deque对象为空，会报空指针
        if (deque == null) {
            deque = new LinkedList<Serializable>();
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId) && ((Session) session).getAttribute("kickout") == null) {
            //将sessionId存入队列
            deque.add(sessionId.toString());
            //将用户的sessionId队列缓存
//            cache.put(username, deque);
            getRedisUtilInstance().set(key,deque);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if (kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeLast();
                //踢出后再更新下缓存队列
                getRedisUtilInstance().set(key,deque);
            } else { //否则踢出前者
                int size=deque.size();
                kickoutSessionId = deque.removeFirst();
                //踢出后再更新下缓存队列
                getRedisUtilInstance().set(key,deque);
            }

            try {
                //获取被踢出的sessionId的session对象
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {//ignore exception
                log.error("Error!kickout Exception >>"+e.getMessage(),e);
            }
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute("kickout") != null) {
            //会话被踢出了
            try {
                //退出登录
                subject.logout();
            } catch (Exception e) { //ignore
            }
            saveRequest(request);

            //判断是不是Ajax请求
            if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
                BaseResponseDTO baseResponseDTO=new BaseResponseDTO(HTTPState.UNAUTH.getCode(),HTTPState.UNAUTH.getMsg(),"");
                //输出json串
                out(response, baseResponseDTO);
            } else {
                //重定向
                WebUtils.issueRedirect(request, response, kickoutUrl);
            }
            return false;
        }
        return true;
    }

    private void out(ServletResponse hresponse, BaseResponseDTO baseResponseDTO)
            throws IOException {
        try {
            hresponse.setCharacterEncoding("UTF-8");
            PrintWriter out = hresponse.getWriter();
            out.println(JSON.toJSONString(baseResponseDTO));
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("Error!kickout Exception >>"+e.getMessage(),e);
        }
    }

    public String getKickoutUrl() {
        return kickoutUrl;
    }

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public boolean isKickoutAfter() {
        return kickoutAfter;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public int getMaxSession() {
        return maxSession;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

}

