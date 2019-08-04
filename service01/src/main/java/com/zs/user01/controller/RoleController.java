package com.zs.user01.controller;

import com.zs.user01.arg.IdArg;
import com.zs.user01.arg.SysRoleArg;
import com.zs.user01.dto.BaseResponseDTO;
import com.zs.user01.dto.SysUserDTO;
import com.zs.user01.service.SysResourceService;
import com.zs.user01.service.SysRoleService;
import com.zs.user01.shiro.RedisPubMsgService;
import com.zs.user01.util.Pager;
import com.zs.user01.vo.SysRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/role")
@Slf4j
public class RoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private RedisPubMsgService redisPubMsgService;

    @Autowired
    private SysResourceService sysResourceService;


    @GetMapping("/search")
    public BaseResponseDTO searchRole(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                      @RequestParam(value = "name", required = false) String name) {

        List<SysRoleVo> roleVos = sysRoleService.searchList((pageNum-1) * pageSize, pageSize, name);
        Pager<SysRoleVo> sysRoleVoPager = new Pager<SysRoleVo>(pageSize, pageNum, sysRoleService.searchCount(name));
        sysRoleVoPager.setResult(roleVos);

        return  BaseResponseDTO.buildSuccess(sysRoleVoPager);
    }

    @PostMapping("/getResourceTree")
    public BaseResponseDTO getResourceTree(@RequestBody IdArg idArg) {
        return  BaseResponseDTO.buildSuccess(sysResourceService.getResourceTree(idArg.getId()));
    }

    @GetMapping("/getRoleAll")
    public BaseResponseDTO getRoleAll() {
        Subject subject = SecurityUtils.getSubject();
        SysUserDTO sysUser = (SysUserDTO) subject.getSession().getAttribute("user");
        return  BaseResponseDTO.buildSuccess(sysRoleService.selectSysRoleAll(sysUser.getId()));
    }

    @PostMapping("/getRoleResource")
    public BaseResponseDTO getRoleResource(@RequestBody IdArg idArg) {
        return  BaseResponseDTO.buildSuccess(sysRoleService.getRoleResource(idArg.getId()));
    }

    @PostMapping("/add")
    public BaseResponseDTO insertRole(@RequestBody SysRoleArg arg) {
        sysRoleService.insertRole(arg);
        //更新权限
        redisPubMsgService.pubMessage();
        return BaseResponseDTO.buildSuccess();

    }

    @PostMapping("/update")
    public BaseResponseDTO updateRole(@RequestBody SysRoleArg arg) {
        sysRoleService.updateRole(arg);
        //更新权限
        redisPubMsgService.pubMessage();
        return BaseResponseDTO.buildSuccess();
    }

    @PostMapping("/delete")
    public BaseResponseDTO deleteRole(@RequestBody IdArg idArg) {
        sysRoleService.deleteRole(idArg.getId());
        //更新权限
        redisPubMsgService.pubMessage();
        return BaseResponseDTO.buildSuccess();
    }

}
