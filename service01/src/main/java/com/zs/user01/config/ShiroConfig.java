package com.zs.user01.config;

import com.zs.user01.contant.WebConstant;
import com.zs.user01.service.WebService;
import com.zs.user01.shiro.*;
import com.zs.user01.util.PermissionUtil;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Resource
    private WebService webService;

    //记住我时长限制72小时
    private static final int REMEMBER_ME_MAX_AGE = 72 * 3600;

    /**
     * 一.请求拦截
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        /**
         * 线上出现长久不登录后，rediret的时候https变成http请求导致报错。 因为redirect是直接在tomcat层进行的，不经过ELB，配置tomcat比较麻烦，不是优雅的解决方案。
         * 解决思路是:判断用户未登录时，不重定向，直接给前端返回unauth的状态码。具体操作如下:
         * *注释掉shiroFilterFactoryBean.setLoginUrl("/api/web/unauth");
         * *注释掉shiroFilterFactoryBean.setLoginUrl的情况下，需要重写FormAuthenticationFilter
         * *不重写就会默认跳转到login.jsp页面去
         * *这里重写FormAuthenticationFilter的onAccessDenied方法，让其失败时直接给前端返回uauth的状态码
         */
        filtersMap.put("authc",new CustomerFormAuthenticationFilter());
        //限制同一帐号同时在线的个数。
        filtersMap.put("kickout", kickoutSessionControlFilter());
        //三次错后，使用验证码
        filtersMap.put("captcha", kaptchaFilter());
        filtersMap.put("user", customerUserFilter());
        filtersMap.put("roles", roleFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
//        shiroFilterFactoryBean.setLoginUrl("/api/web/unauth");
        Map<String, String> filterChainDefinitionMap = PermissionUtil.getPermission(webService.loadFilterChainDefinitions());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 1.自定义认证
     *
     * @return CustomerShiroRealm
     * @Title: myShiroRealm
     * @Description: ShiroRealm，这是个自定义的认证类，继承自AuthorizingRealm，负责用户的认证和权限的处理
     */
    @Bean
    public CustomerShiroRealm myShiroRealm() {
        CustomerShiroRealm customerShiroRealm = new CustomerShiroRealm();
        customerShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return customerShiroRealm;
    }


    /**
     * 二.权限管理
     *
     * @return SecurityManager
     * @Title: securityManager
     * @Description: SecurityManager，权限管理，这个类组合了登陆，登出，权限，session的处理
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //注入记住我管理器
        securityManager.setRememberMeManager(rememberMeManager());
        //securityManager.setCacheManager(redisCacheManager());
        securityManager.setSessionManager(sessionManager());
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    /**
     * 密码凭证匹配器，作为自定义认证的基础
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了 ）
     *
     * @return
     */
    @Bean
    public CustomerCredentialsMatcher hashedCredentialsMatcher() {
        CustomerCredentialsMatcher hashedCredentialsMatcher = new CustomerCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(WebConstant.SHIRO_HASH_ALGORITHM_NAME);//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(WebConstant.SHIRO_HASH_ITERATIONS);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    /**
     * 2.自定义sessionManager，用户的唯一标识，即Token或Authorization的认证
     */
    @Bean
    public SessionManager sessionManager() {
        CustomerSessionManager customerSessionManager = new CustomerSessionManager();
        customerSessionManager.setSessionDAO(customerRedisSessionDao());
        return customerSessionManager;
    }


    @Bean
    public CustomerRedisSessionDao customerRedisSessionDao() {
        CustomerRedisSessionDao redisSessionDAO = new CustomerRedisSessionDao();
        return redisSessionDAO;
    }


    /**
     * 3.此处对应前端“记住我”的功能，获取用户关联信息而无需登录
     *
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = remember
        SimpleCookie simpleCookie = new SimpleCookie("remember");
        simpleCookie.setMaxAge(REMEMBER_ME_MAX_AGE);
        simpleCookie.setHttpOnly(false);
        return simpleCookie;
    }


    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode( "kPH+bIxk5D2deZiIxcaaaA=="));
        return cookieRememberMeManager;
    }


    /**
     * 限制同一账号登录同时登录人数控制
     *
     * @return
     */
    @Bean
    public SessionControlFilter kickoutSessionControlFilter() {
        SessionControlFilter kickoutSessionControlFilter = new SessionControlFilter();
        kickoutSessionControlFilter.setSessionManager(sessionManager());
        kickoutSessionControlFilter.setKickoutAfter(false);
        kickoutSessionControlFilter.setMaxSession(1);
        kickoutSessionControlFilter.setKickoutUrl("/api/web/kickout");
        return kickoutSessionControlFilter;
    }

    /**
     * 登录验证码filter
     * @return
     */
    @Bean
    public KaptchaFilter kaptchaFilter(){
        KaptchaFilter kaptchaFilter=new KaptchaFilter();
        return kaptchaFilter;
    }


    @Bean
    public CustomerUserFilter customerUserFilter(){
        CustomerUserFilter customerUserFilter=new CustomerUserFilter();
        return customerUserFilter;
    }

    @Bean
    public RoleFilter roleFilter(){
        RoleFilter roleFilter = new RoleFilter();
        return roleFilter;
    }

    /***
     * 授权所用配置
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }


    /***
     * 使授权注解起作用不如不想配置可以在pom文件中加入
     * <dependency>
     *<groupId>org.springframework.boot</groupId>
     *<artifactId>spring-boot-starter-aop</artifactId>
     *</dependency>
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * Shiro生命周期处理器
     * 此方法需要用static作为修饰词，否则无法通过@Value()注解的方式获取配置文件的值
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


}
