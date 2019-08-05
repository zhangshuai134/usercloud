package com.zs.user01.oplog;

import com.alibaba.fastjson.JSON;
import com.zs.user01.dto.OpLogDto;
import com.zs.user01.dto.SysUserDTO;
import com.zs.user01.service.OpLogService;
import com.zs.user01.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: aop
 */
@Aspect
@Component
@Slf4j
@SuppressWarnings({"unchecked", "unused", "rawtypes"})
public class LogAspect {

    @Resource
    private OpLogService opLogService;

    @Pointcut("@annotation(com.zs.user01.oplog.OpTrack)")
    public void log() {
    }

    @AfterReturning("log()&&@annotation(opTrack)")
    public void after(JoinPoint joinPoint, OpTrack opTrack) {
        try {
            OpLogDto opLogDto = new OpLogDto();
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if(requestAttributes != null){
                HttpServletRequest request = requestAttributes.getRequest();
                opLogDto.setIp(IpUtil.getIpAdrress(request));
            }
//            MethodSignature signature = (MethodSignature)joinPoint.getSignature();
//            OpTrack annotation = signature.getMethod().getAnnotation(OpTrack.class);

            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String methodName = methodSignature.getName();
            opLogDto.setMethodName(methodName);

            String[] parameterNames = methodSignature.getParameterNames();
            opLogDto.setParamNames(JSON.toJSONString(parameterNames));

            Object[] args = joinPoint.getArgs();
            List<Object> newargLs=new ArrayList<>();
            for(int i=0;i<args.length;i++){
                Object arg=args[i];
                if (arg instanceof HttpServletResponse || arg instanceof  HttpServletRequest){
                    continue;
                }else{
                    newargLs.add(arg);
                }
            }
            opLogDto.setParamValues(JSON.toJSONString(newargLs));
            String className = methodSignature.getMethod().getDeclaringClass().toString();
            opLogDto.setClassName(className);

            String description = opTrack.description();
            opLogDto.setDescription(description);

            SysUserDTO sysUserDTO = (SysUserDTO) SecurityUtils.getSubject().getPrincipal();
            opLogDto.setUserName(sysUserDTO.getLoginName());

            opLogService.saveOpLog(opLogDto);

        } catch (Exception e) {
            log.error("用户操作追踪报错  Exception:", e);
        }
    }
}
