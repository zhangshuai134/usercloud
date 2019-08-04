package com.zs.user01.controller;


import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.zs.user01.contant.HTTPState;
import com.zs.user01.dto.BaseResponseDTO;
import com.zs.user01.dto.SysUserDTO;
import com.zs.user01.param.LoginParam;
import com.zs.user01.service.SysUserService;
import com.zs.user01.service.WebService;
import com.zs.user01.util.ParamsValidateUtil;
import com.zs.user01.util.RedisUtil;
import com.zs.user01.util.Translator;
import com.zs.user01.util.UuidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static com.zs.user01.contant.WebConstant.CAPTCHA_PREFIX_KEY;
import static com.zs.user01.contant.WebConstant.TRANS_ROLE_PREFIX;


@RestController
@RequestMapping("/api/web")
@Slf4j
public class WebController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DefaultKaptcha captchaProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WebService webService;



    private RedisUtil getRedisUtilInstance(){
        return RedisUtil.getInstance(redisTemplate);
    }



    /**
     * 未授权跳转方法
     *
     * @return
     */
    @GetMapping("/unauth")
    public BaseResponseDTO unauth() {
        return new BaseResponseDTO(HTTPState.UNAUTH.getCode(), HTTPState.UNAUTH.getMsg(), null);
    }

    /**
     * 被踢出后跳转方法
     *
     * @return
     */
    @GetMapping("/kickout")
    public BaseResponseDTO kickout() {
        return new BaseResponseDTO(HTTPState.KICKOUT.getCode(), HTTPState.KICKOUT.getMsg(), null);
    }


    /**
     * 获取验证码 的 请求路径
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @GetMapping("/defaultKaptcha")
    public BaseResponseDTO<Map<String,Object>> defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = captchaProducer.createText();
            log.info("captcha code:"+createText);
            httpServletRequest.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = captchaProducer.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
            //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
            captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
            Base64.Encoder encoder = Base64.getEncoder();
            String imageStr=encoder.encodeToString(captchaChallengeAsJpeg);
            String token= UuidGenerator.generateUuidStr();
            getRedisUtilInstance().set(CAPTCHA_PREFIX_KEY+token,createText,60, TimeUnit.SECONDS.name());
            Map<String,Object> resultMap=new HashMap<>();
            resultMap.put("token",token);
            resultMap.put("imgSrc",imageStr);
            return new BaseResponseDTO<Map<String,Object>>(HTTPState.OK.getCode(),HTTPState.OK.getMsg(),resultMap);
        } catch (IllegalArgumentException e) {
//            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            log.error("Error!get captcha code error! >>"+e.getMessage(),e);
            return new BaseResponseDTO<>(HTTPState.SYSTEMERROR.getCode(),HTTPState.SYSTEMERROR.getMsg(),null);
        }

    }

    public static void main(String[] args) {
        LoginParam loginParam = new LoginParam();
        loginParam.setLoginName("zs");
        loginParam.setPwd("123456");
        loginParam.setLang("zh");
        loginParam.setToken("123");
        loginParam.setRememberMe(true);
        System.out.println(JSON.toJSONString(loginParam));
    }
    /**
     * 登录接口
     * @param param 请求参数
     * @return
     */
    @PostMapping("/login")
    public BaseResponseDTO login(@RequestBody LoginParam param) {
        String lang=param.getLang();
        try {
            if(ParamsValidateUtil.validate(param)) {
                UsernamePasswordToken token = new UsernamePasswordToken(param.getLoginName(), param.getPwd(), param.getRememberMe());
                //登录不在该处处理，交由shiro处理
                Subject subject = SecurityUtils.getSubject();
                subject.login(token);
                if (subject.isAuthenticated()) {
                    Map<String, Object> resultMap = new HashMap<>();
                    String userName = (String) token.getPrincipal();
                    SysUserDTO sysUser = sysUserService.getUserByLoginName(userName);
                    subject.getSession().setAttribute("lang", lang);
                    subject.getSession().setAttribute("user", sysUser);
                    if (!lang.equals(sysUser.getLang())) {
                        SysUserDTO sysUserDTO = new SysUserDTO();
                        sysUserDTO.setLoginName(sysUser.getLoginName());
                        sysUserDTO.setLang(lang);
                        webService.updateSysuser(sysUserDTO);
                        sysUser.setLang(lang);
                    }
                    sysUser.setRoleName(Translator.toLocale(sysUser.getLang(),TRANS_ROLE_PREFIX+sysUser.getRoleEnName()));
                    resultMap.put("token", subject.getSession().getId());
                    resultMap.put("user", sysUser);
                    resultMap.put("menuList", webService.getRoleMenuByloginName(userName));
                    return new BaseResponseDTO(HTTPState.OK.getCode(), Translator.toLocale(lang,HTTPState.OK.name()), resultMap);
                } else {
                    return new BaseResponseDTO(HTTPState.SHIROFAIL.getCode(),Translator.toLocale(lang,HTTPState.SHIROFAIL.name()), null);
                }
            }else {
                return new BaseResponseDTO<>(HTTPState.PARAM_ERROR.getCode(),Translator.toLocale(lang,HTTPState.PARAM_ERROR.name()),null);
            }
        } catch (IncorrectCredentialsException | UnknownAccountException e) {
            log.error("Error!login error"+e.getMessage(),e);
            return new BaseResponseDTO(HTTPState.USERNOTEXIST.getCode(), Translator.toLocale(lang,HTTPState.USERNOTEXIST.name()), null);
        } catch (LockedAccountException e) {
            log.error("Error!login error"+e.getMessage(),e);
            return new BaseResponseDTO(HTTPState.USERLOCKED.getCode(), Translator.toLocale(lang,HTTPState.USERLOCKED.name()), null);
        } catch (Exception e) {
            log.error("Error!login error"+e.getMessage(),e);
            return new BaseResponseDTO(HTTPState.SYSTEMERROR.getCode(), Translator.toLocale(lang,HTTPState.SYSTEMERROR.name()), null);
        }
    }


    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("/logout")
    public BaseResponseDTO logout() {
        SecurityUtils.getSubject().logout();
        return new BaseResponseDTO(HTTPState.OK.getCode(), HTTPState.OK.getMsg(), null);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("/check")
    public BaseResponseDTO test() {
//        SecurityUtils.getSubject().logout();
        return new BaseResponseDTO(HTTPState.OK.getCode(), HTTPState.OK.getMsg(), null);
    }




}
