package com.zs.user01.controller;

import com.zs.user01.arg.IdArg;
import com.zs.user01.arg.SysResourceArg;
import com.zs.user01.dto.BaseResponseDTO;
import com.zs.user01.service.SysResourceService;
import com.zs.user01.shiro.RedisPubMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/resource")
@Slf4j
public class ResourceController {

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private RedisPubMsgService redisPubMsgService;

    @PostMapping("/getResourceTable")
    public BaseResponseDTO getResourceTree(@RequestBody IdArg idArg) {
        return  BaseResponseDTO.buildSuccess(sysResourceService.getResourceTree(idArg.getId()));
    }

    @PostMapping("/add")
    public BaseResponseDTO insertResource(@RequestBody SysResourceArg arg) {
        if(arg.getParentId()==null){
            arg.setParentId(0l);
        }
        sysResourceService.insertResource(arg);
        //更新权限
        redisPubMsgService.pubMessage();
        return BaseResponseDTO.buildSuccess();

    }

    @PostMapping("/update")
    public BaseResponseDTO updateResource(@RequestBody SysResourceArg arg) {
        sysResourceService.updateResource(arg);
        //更新权限
        redisPubMsgService.pubMessage();
        return BaseResponseDTO.buildSuccess();
    }

    @PostMapping("/delete")
    public BaseResponseDTO deleteResource(@RequestBody IdArg idArg) {
        sysResourceService.deleteResource(idArg.getId());
        //更新权限
        redisPubMsgService.pubMessage();
        return BaseResponseDTO.buildSuccess();
    }



}
