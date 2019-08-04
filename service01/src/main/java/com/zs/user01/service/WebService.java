package com.zs.user01.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zs.user01.dto.RoleMenuDTO;
import com.zs.user01.dto.SysUserDTO;
import com.zs.user01.entity.SysRole;
import com.zs.user01.entity.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Service
public class WebService {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysResourceService sysResourceService;

    public SysUserDTO getUserByLoginName(String loginName) {
        return sysUserService.getUserByLoginName(loginName);
    }

    public Map<String, String> loadFilterChainDefinitions() {
        return sysRoleService.loadFilterChainDefinitions();
    }

    public List<SysRole> findByUserid(@RequestParam String userId) {
        List<SysRole> sysRoles = sysRoleService.findByUserid(userId);
        return JSONArray.parseArray(JSONArray.toJSONString(sysRoles), SysRole.class);
    }

    public void updateSysuser(@RequestBody SysUserDTO sysUserDTO) throws Exception {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDTO, sysUser);
        LambdaUpdateWrapper<SysUser> userUpdateWrapper = new UpdateWrapper().lambda();
        if (StringUtils.isNotEmpty(sysUser.getEmail())) {
            userUpdateWrapper.set(SysUser::getEmail, sysUser.getEmail());
        }
        if (StringUtils.isNotEmpty(sysUser.getLang())) {
            userUpdateWrapper.set(SysUser::getLang, sysUser.getLang());
        }
        if (StringUtils.isNotEmpty(sysUser.getPassword())) {
            userUpdateWrapper.set(SysUser::getPassword, sysUser.getPassword());
        }
        if (StringUtils.isNotEmpty(sysUser.getName())) {
            userUpdateWrapper.set(SysUser::getName, sysUser.getName());
        }
        userUpdateWrapper.eq(SysUser::getLoginName, sysUserDTO.getLoginName());
        sysUserService.update(sysUser, userUpdateWrapper);

    }

    public List<RoleMenuDTO> getRoleMenuByloginName(@RequestParam String loginName) {
        return sysResourceService.getRoleMenuByloginName(loginName);
    }

    public SysUserDTO getUserByEmail(@RequestParam String email) {
        return sysUserService.getUserByEmail(email);
    }


}
