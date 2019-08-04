package com.zs.user01.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zs.user01.dto.RoleMenuDTO;
import com.zs.user01.entity.ResourceTree;
import com.zs.user01.entity.SysResource;
import com.zs.user01.arg.SysResourceArg;
import com.zs.user01.mapper.SysResourceMapper;
import com.zs.user01.vo.SysResourceVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysResourceService extends ServiceImpl<SysResourceMapper, SysResource> {

    @Resource
    private SysResourceMapper sysResourceMapper;

    public List<RoleMenuDTO> getRoleMenuByloginName(String loginName) {
        List<RoleMenuDTO> roleMenus = new ArrayList<>();
        List<SysResource> sysResources = sysResourceMapper.getMenuListByLoginName(loginName);
        if (sysResources != null && sysResources.size() > 0) {
            for (SysResource sysResource : sysResources) {
                RoleMenuDTO roleMenuDTO = new RoleMenuDTO();
                roleMenuDTO.setMenu(sysResource.getName());
                roleMenuDTO.setMenuId(sysResource.getId());
                roleMenuDTO.setParentId(sysResource.getParentId());
                List<String> buttonLs = sysResourceMapper.getButtonByMenuIdAndLoginName(roleMenuDTO.getMenuId(), loginName);
                roleMenuDTO.setButton(buttonLs);
                roleMenus.add(roleMenuDTO);
            }
        }
        return roleMenus;
    }

    public List<ResourceTree> getResourceTree(long parentId) {
        List<ResourceTree> trees = Lists.newArrayList();
        List<SysResourceVo> sysResourceVos = sysResourceMapper.selectAll();
        Set<Long> pids = sysResourceVos.stream().map(SysResourceVo::getParentId).collect(Collectors.toSet());
        treeList(sysResourceVos, 0, trees, pids);
        return trees;
    }

    public List<ResourceTree> treeList(List<SysResourceVo> sysResourceVos, long pid, List<ResourceTree> trees, Set<Long> pids) {
        for (SysResourceVo mu : sysResourceVos) {
            //有pid节点
            if (mu.getParentId() == pid) {
                ResourceTree resourceTree = new ResourceTree();
                BeanUtils.copyProperties(mu, resourceTree);
                //判断该节点有没有子节点
                if (pids.contains(mu.getId())) {
                    List<ResourceTree> children = treeList(sysResourceVos, mu.getId(), Lists.newArrayList(), pids);
                    resourceTree.setChildren(children);
                }
                trees.add(resourceTree);
            }
        }
        return trees;
    }

    public void insertResource(SysResourceArg arg) {
        sysResourceMapper.insertSysResource(arg);
    }

    public void updateResource(SysResourceArg arg) {
        SysResource sysResource = new SysResource();
        BeanUtils.copyProperties(arg, sysResource);
        sysResourceMapper.updateSysResource(sysResource);
    }

    public void deleteResource(Long id) {
        //delete resource
        sysResourceMapper.deleteSysResource(id);
        sysResourceMapper.deleteRoleResourceRel(id);
    }

}
