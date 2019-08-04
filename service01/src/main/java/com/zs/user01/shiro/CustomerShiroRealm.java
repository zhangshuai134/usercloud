package com.zs.user01.shiro;

import com.zs.user01.dto.SysUserDTO;
import com.zs.user01.entity.SysRole;
import com.zs.user01.service.WebService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerShiroRealm extends AuthorizingRealm {


    @Autowired
    private WebService webService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Object principal = principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (principal instanceof SysUserDTO) {
            SysUserDTO userLogin = (SysUserDTO) principal;
            List<SysRole> roleList = webService.findByUserid(Long.toString(userLogin.getId()));
            if (CollectionUtils.isNotEmpty(roleList)) {
                for (SysRole sysRole : roleList) {
                    info.addRole(sysRole.getEnname());
                }
            }
        }
        return info;
    }



    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户的输入的账号.
        String username = (String) token.getPrincipal();
        // 通过username从数据库中查找
        SysUserDTO user = webService.getUserByLoginName(username);
        if (user == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, // 用户名
                user.getPassword(), // 密码
                getName() // realm name
        );
        return authenticationInfo;
    }


}
