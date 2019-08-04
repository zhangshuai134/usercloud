package com.zs.user01.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.api.client.util.Sets;
import com.zs.user01.entity.RoleMenu;
import com.zs.user01.arg.RoleResourceArg;
import com.zs.user01.entity.SysRole;
import com.zs.user01.arg.SysRoleArg;
import com.zs.user01.mapper.RoleResourceRelMapper;
import com.zs.user01.mapper.SysResourceMapper;
import com.zs.user01.mapper.SysRoleMapper;
import com.zs.user01.mapper.SysUserMapper;
import com.zs.user01.vo.SysResourceVo;
import com.zs.user01.vo.SysRoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysResourceMapper sysResourceMapper;

    @Resource
    private RoleResourceRelMapper roleResourceRelMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    public List<SysRole> findByUserid(String userId) {
        return sysRoleMapper.findByUserid(userId);
    }


    /**
     * 初始化权限
     */
    public Map<String, String> loadFilterChainDefinitions() {
        // 权限控制map.从数据库获取
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //动态授权
        List<RoleMenu> roleMenuList = sysRoleMapper.findHrefRole();
        if (!CollectionUtils.isEmpty(roleMenuList)) {
            for (RoleMenu menu : roleMenuList) {
                    filterChainDefinitionMap.put(menu.getMenuUrl(), menu.getRoleEnName());
            }
        }
        return filterChainDefinitionMap;
    }

    public List<SysRoleVo> searchList(Integer pageNum, Integer pageSize, String name) {
        return sysRoleMapper.searchList(pageNum,pageSize,name);
    }

    public Long searchCount(String name) {
        return sysRoleMapper.searchCount(name);
    }

    public List<SysRoleVo> selectSysRoleAll(Long id) {
        Long roleId = sysUserMapper.getUserRoleId(id);
        return sysRoleMapper.selectSysRoleAll(roleId);
    }

    public Set<Long> getRoleResource(long roleId){
        Set<Long> res = Sets.newHashSet();
        List<SysResourceVo> sysResourceVos = sysResourceMapper.selectAll();
        Set<Long> collect = sysResourceVos.stream().map(SysResourceVo::getParentId).collect(Collectors.toSet());
        List<Long> resourceIds = sysRoleMapper.getRoleResource(roleId);
        //有叶子节点剔除该父节点
        resourceIds.forEach(x->{
            if(!collect.contains(x)){
                res.add(x);
            }
        });
        return  res;
    }

    public void insertRole(SysRoleArg arg) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(arg,sysRole);
        sysRoleMapper.insertSysRole(sysRole);
        //add role and resource relation
        List<Long> resourceIds = arg.getResourceIds();
        resourceIds.forEach(x->{
            RoleResourceArg roleResourceArg = new RoleResourceArg();
            roleResourceArg.setRoleId(sysRole.getId());
            roleResourceArg.setResourceId(x);
            roleResourceRelMapper.insertRoleResourceRel(roleResourceArg);
        });
    }

    public void updateRole(SysRoleArg arg) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(arg,sysRole);
        sysRoleMapper.updateSysRole(sysRole);
        //delete role and resource relation
        roleResourceRelMapper.deleteRoleResourceRel(arg.getId());
        //add role and resource relation
        List<Long> resourceIds = arg.getResourceIds();
        resourceIds.forEach(x->{
            RoleResourceArg roleResourceArg = new RoleResourceArg();
            roleResourceArg.setRoleId(sysRole.getId());
            roleResourceArg.setResourceId(x);
            roleResourceRelMapper.insertRoleResourceRel(roleResourceArg);
        });
    }

    public void deleteRole(Long id) {
        //用户绑定的角色不可以删除，需要校验
        if(sysRoleMapper.checkRoleUser(id)>0){
            System.out.println("说明存在用户绑定该角色，不能删除");
            return;
        }
        sysRoleMapper.deleteSysRole(id);
        sysRoleMapper.deleteRoleResourceRel(id);
        sysRoleMapper.deleteRoleUserRel(id);
    }

}
