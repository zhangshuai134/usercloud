package com.zs.user01.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.user01.arg.SysUserArg;
import com.zs.user01.arg.UserRoleArg;
import com.zs.user01.dto.SysUserDTO;
import com.zs.user01.entity.SysUser;
import com.zs.user01.entity.SysUserBO;
import com.zs.user01.mapper.SysUserMapper;
import com.zs.user01.util.UUIDUtils;
import com.zs.user01.vo.SysUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {


    @Resource
    private SysUserMapper sysUserMapper;

    public SysUserDTO getUserByLoginName(String loginName) {
        List<SysUserBO> userList = sysUserMapper.getUserByloginName(loginName);
        if (userList != null && userList.size() > 0) {
            SysUserBO sysUser= userList.get(0);
            SysUserDTO sysUserDTO = new SysUserDTO();
            BeanUtils.copyProperties(sysUser,sysUserDTO);
            return sysUserDTO;
        } else {
            return null;
        }
    }


    public List<SysUserDTO> getUsersByRole(String roleName){

        List<SysUserDTO> sysUserDTOS=new ArrayList<>();
        List<SysUser> sysUsers= sysUserMapper.getUserByRole(roleName);
        if(sysUsers!=null&&sysUsers.size()>0){
            for(SysUser sysUser:sysUsers){
                SysUserDTO sysUserDTO = new SysUserDTO();
                BeanUtils.copyProperties(sysUser,sysUserDTO);
                sysUserDTOS.add(sysUserDTO);
            }
        }
        return  sysUserDTOS;
    }

    public SysUserDTO getUserByEmail(String email) {
        List<SysUser> userList = sysUserMapper.getUserByEmail(email);
        if (userList != null && userList.size() > 0) {
            SysUser sysUser= userList.get(0);
            SysUserDTO sysUserDTO = new SysUserDTO();
            BeanUtils.copyProperties(sysUser,sysUserDTO);
            return sysUserDTO;
        } else {
            return null;
        }
    }

    public List<SysUserVo> searchList(Integer pageNum, Integer pageSize, String loginName, Long roleId) {
        return sysUserMapper.searchList(pageNum,pageSize,loginName,roleId);
    }

    public Long searchCount(String loginName,Long roleId) {
        return sysUserMapper.searchCount(loginName,roleId);
    }

    public Long getUserRoleId(Long id){
        return sysUserMapper.getUserRoleId(id);
    }


    public void insertUser(SysUserArg arg) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(arg,sysUser);
        sysUser.setUserId(UUIDUtils.getUUID());
        sysUserMapper.insertSysUser(sysUser);
        //add user role relation
        if(null!=arg.getRoleId()){
            UserRoleArg userRoleArg = new UserRoleArg();
            userRoleArg.setUserId(sysUser.getId());
            userRoleArg.setRoleId(arg.getRoleId());
            sysUserMapper.insertUserRoleRel(userRoleArg);
        }

    }

    public void updateUser(SysUserArg arg) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(arg,sysUser);
        sysUserMapper.updateSysUser(sysUser);
        //delete user role relation
        sysUserMapper.deleteUserRoleRel(sysUser.getId());
        //add user role relation
        UserRoleArg userRoleArg = new UserRoleArg();
        userRoleArg.setUserId(sysUser.getId());
        userRoleArg.setRoleId(arg.getRoleId());
        sysUserMapper.insertUserRoleRel(userRoleArg);
    }

    public void deleteUser(Long id) {
        // delete user and sys_user_role
        sysUserMapper.deleteSysUser(id);
        sysUserMapper.deleteUserRoleRel(id);
    }
}
