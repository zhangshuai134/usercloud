package com.zs.user01.controller;

import com.zs.user01.contant.HTTPState;
import com.zs.user01.dto.BaseResponseDTO;
import com.zs.user01.dto.SysUserDTO;
import com.zs.user01.oplog.OpTrack;
import com.zs.user01.param.UpdateAccountParam;
import com.zs.user01.service.WebService;
import com.zs.user01.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.zs.user01.contant.WebConstant.TRANS_ROLE_PREFIX;

@RestController
@RequestMapping("/api/account")
@Slf4j
public class AccountController {


    @Autowired
    private WebService webService;


    /**
     * 根据登录名获取用户信息
     * @return
     */
    @GetMapping("/getAccountInfo")
    public BaseResponseDTO getAccountInfo(){
        Subject subject = SecurityUtils.getSubject();
        SysUserDTO loginSysUser=(SysUserDTO) subject.getPrincipal();
        try{
            Map<String,Object> resultMap=new HashMap<>();
            SysUserDTO userInfo=webService.getUserByLoginName(loginSysUser.getLoginName());
            String roleName=Translator.toLocale(userInfo.getLang(),TRANS_ROLE_PREFIX+userInfo.getRoleEnName());
            userInfo.setRoleName(roleName);
            resultMap.put("user",userInfo);
            resultMap.put("menuList",webService.getRoleMenuByloginName(loginSysUser.getLoginName()));
            return new BaseResponseDTO<>(HTTPState.OK.getCode(), Translator.toLocale(loginSysUser.getLang(),HTTPState.OK.name()),resultMap);
        }catch (Exception e){
            log.error("Error!get accountinfo error >>"+e.getMessage(),e);
            return new BaseResponseDTO<>(HTTPState.SYSTEMERROR.getCode(), Translator.toLocale(loginSysUser.getLang(),HTTPState.SYSTEMERROR.name()),null);
        }
    }

    /**
     * 更新用户信息
     * @param updateAccountParam 用户模型
     * @return
     */
    @PostMapping("/updateAccount")
    @OpTrack(description = "更新用户信息")
    public BaseResponseDTO<String> updateAccount(@Validated  @RequestBody UpdateAccountParam updateAccountParam){
        Subject subject = SecurityUtils.getSubject();
        SysUserDTO loginSysUser=(SysUserDTO) subject.getPrincipal();
        try{
            if(ParamsValidateUtil.validate(updateAccountParam)){
                String oldpwd=updateAccountParam.getOldPwd();
                SysUserDTO sysUserDTO =new SysUserDTO();
                if(StringUtils.isNotEmpty(oldpwd)){
                    if(!PasswordRuleUtil.validate(updateAccountParam.getNewPwd())){
                        return new BaseResponseDTO<>(HTTPState.PARAM_ERROR.getCode(),Translator.toLocale(updateAccountParam.getLang(),HTTPState.PARAM_ERROR.name()),null);
                    }
                    sysUserDTO= webService.getUserByLoginName(loginSysUser.getLoginName());
                    String hashOldPwd=ShiroHashUtil.hash(oldpwd);
                    if(!StringUtils.equals(sysUserDTO.getPassword(),hashOldPwd)){
                        return new BaseResponseDTO<>(HTTPState.PARAM_ERROR.getCode(),Translator.toLocale(updateAccountParam.getLang(),"PASSWORDERROR"),null);
                    }
                    sysUserDTO.setPassword(ShiroHashUtil.hash(updateAccountParam.getNewPwd()));
                }
                sysUserDTO.setLoginName(loginSysUser.getLoginName());
                sysUserDTO.setName(updateAccountParam.getUserName());
                sysUserDTO.setEmail(updateAccountParam.getEmail());
                sysUserDTO.setLang(updateAccountParam.getLang());
                webService.updateSysuser(sysUserDTO);
                subject.getSession().setAttribute("lang",updateAccountParam.getLang());
                return new BaseResponseDTO<>(HTTPState.OK.getCode(),Translator.toLocale(updateAccountParam.getLang(),HTTPState.OK.name()),null);
            }else {
                return new BaseResponseDTO<>(HTTPState.PARAM_ERROR.getCode(),Translator.toLocale(updateAccountParam.getLang(),HTTPState.PARAM_ERROR.name()),null);
            }
        }catch (Exception e){
            log.error("Error!update account error >>"+e.getMessage(),e);
            return new BaseResponseDTO<>(HTTPState.SYSTEMERROR.getCode(),Translator.toLocale(updateAccountParam.getLang(),HTTPState.SYSTEMERROR.name()),null);
        }
    }



    /**
     * 找回密码
     * @return
     */
    @GetMapping("/forgetPassword")
    @OpTrack(description = "找回密码")
    public BaseResponseDTO forgetPassword(String email,String lang){
        SysUserDTO userInfo=webService.getUserByEmail(email);
        try{
            if(userInfo!=null){
                String newpassword=RandomPassworUtil.generated(6);
                SysUserDTO sysUserDTO=new SysUserDTO();
                sysUserDTO.setLoginName(userInfo.getLoginName());
                sysUserDTO.setPassword(ShiroHashUtil.hash(newpassword));
                webService.updateSysuser(sysUserDTO);

                return new BaseResponseDTO<>(HTTPState.OK.getCode(),Translator.toLocale(lang,HTTPState.OK.name()),newpassword);
            }else{
                return new BaseResponseDTO<>(HTTPState.USERNOTEXIST.getCode(),Translator.toLocale(lang,"EMAILERROR"),null);
            }

        }catch (Exception e){
            log.error("Error!get accountinfo error >>"+e.getMessage(),e);
            return new BaseResponseDTO<>(HTTPState.SYSTEMERROR.getCode(),Translator.toLocale(lang,HTTPState.SYSTEMERROR.name()),null);
        }
    }




}
