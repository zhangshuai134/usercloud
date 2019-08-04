package com.zs.user01.controller;

import com.alibaba.fastjson.JSON;
import com.zs.user01.arg.IdArg;
import com.zs.user01.arg.SysUserArg;
import com.zs.user01.dto.BaseResponseDTO;
import com.zs.user01.dto.SysUserDTO;
import com.zs.user01.service.SysUserService;
import com.zs.user01.util.Pager;
import com.zs.user01.util.ShiroHashUtil;
import com.zs.user01.vo.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据登录名获取用户信息
     * @return
     */
    @GetMapping("/getUserByRoleName")
    public BaseResponseDTO getAccountInfo(@RequestParam("role") String roleName){
        return  BaseResponseDTO.buildSuccess(sysUserService.getUsersByRole(roleName));

    }

    @GetMapping("/search")
    public BaseResponseDTO searchUser(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                      @RequestParam(value = "loginName", required = false) String loginName) {
        Subject subject = SecurityUtils.getSubject();
        SysUserDTO sysUser = (SysUserDTO) subject.getSession().getAttribute("user");

        //根据用户ID获取角色ID，区分开发者与非开发者
        Long roleId = sysUserService.getUserRoleId(sysUser.getId());
        List<SysUserVo> sysUserVos = sysUserService.searchList((pageNum-1) * pageSize, pageSize, loginName,roleId);
        Pager<SysUserVo> userDTOPager = new Pager<SysUserVo>(pageSize, pageNum, sysUserService.searchCount(loginName,roleId));
        userDTOPager.setResult(sysUserVos);

        return BaseResponseDTO.buildSuccess(userDTOPager);
    }


    @PostMapping("/add")
    public BaseResponseDTO insertUser(@RequestBody SysUserArg arg) {
        arg.setPassword(ShiroHashUtil.hash(arg.getPassword()));
        sysUserService.insertUser(arg);
        return BaseResponseDTO.buildSuccess();
    }

    public static void main(String[] args) {
        SysUserArg sysUserArg = new SysUserArg();
        sysUserArg.setLoginName("zszs");
        sysUserArg.setEmail("zhangs9092@163.com");
        sysUserArg.setName("zszs");
        sysUserArg.setPhone("123");
        sysUserArg.setRoleId(1L);
        sysUserArg.setPassword("123");
        System.out.println(JSON.toJSONString(sysUserArg));
    }

    @PostMapping("/update")
    public BaseResponseDTO updateUser(@RequestBody SysUserArg arg) {
        sysUserService.updateUser(arg);
        return BaseResponseDTO.buildSuccess();
    }

    @PostMapping("/delete")
    public BaseResponseDTO deleteUser(@RequestBody IdArg idArg) {
        sysUserService.deleteUser(idArg.getId());
        return BaseResponseDTO.buildSuccess();
    }

}
