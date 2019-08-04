package com.zs.user01.service;


import com.zs.user01.util.PermissionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class ShiroService {

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Autowired
    private WebService webService;


    /**
     * 重新加载权限
     */
    public void updatePermission() {
            synchronized (shiroFilterFactoryBean) {
            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
                if (shiroFilter != null) {
                    PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                            .getFilterChainResolver();
                    DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
                    // 清空老的权限控制
                    manager.getFilterChains().clear();
                    shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
                    Map<String, String> permission = PermissionUtil.getPermission(webService.loadFilterChainDefinitions());
                    shiroFilterFactoryBean.setFilterChainDefinitionMap(permission);
                    // 重新构建生成
                    Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
                    if(MapUtils.isNotEmpty(chains)){
                        for (Map.Entry<String, String> entry : chains.entrySet()) {
                            manager.createChain(entry.getKey(), entry.getValue().trim());
                        }
                    }
                    log.info("update authority success!");
                }

            } catch (Exception e) {
                    throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }

        }
    }


}
