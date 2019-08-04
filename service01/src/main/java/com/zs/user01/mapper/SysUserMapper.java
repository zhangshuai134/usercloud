package com.zs.user01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.user01.entity.SysUser;
import com.zs.user01.entity.SysUserBO;
import com.zs.user01.vo.SysUserVo;
import com.zs.user01.arg.UserRoleArg;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select user.*,role.name as roleName,role.enname as roleEnname from sys_user user left join sys_user_role usrole on user.id=usrole.user_id left join " +
            "sys_role role on usrole.role_id=role.id where  login_name=#{loginName}")
    List<SysUserBO> getUserByloginName(String loginName);


    @Select("select user.* from sys_user user left join sys_user_role usrole on user.id=usrole.user_id left join " +
            "sys_role role on usrole.role_id=role.id where  role.enname=#{roleName}")
    List<SysUser> getUserByRole(String roleName);

    @Select("select * from sys_user where email=#{email}")
    List<SysUser> getUserByEmail(String email);

    @Select("<script>" +
            "select m.*,n.role_id as roleId,t.enname from sys_user m left join sys_user_role n  on m.id = n.user_id left join sys_role t on n.role_id =t.id " +
            "where 1=1 " +
            "<if test=\"roleId!=4\">" +
            " and n.role_id != 4" +
            "</if>" +
            "<if test=\"loginName!='' and loginName!=null\">" +
            " and m.login_name LIKE CONCAT(CONCAT('%', #{loginName}), '%')" +
            "</if>" +
            " order by id desc " +
            "LIMIT #{offset}, #{limit}" +
            "</script>")
    List<SysUserVo> searchList(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("loginName") String loginName, @Param("roleId") Long roleId);

    @Select("<script>" +
            "select count(*) from sys_user m left join sys_user_role n  on m.id = n.user_id left join sys_role t on n.role_id =t.id " +
            "where 1=1 " +
            "<if test=\"roleId!=4\">" +
            " and n.role_id != 4" +
            "</if>" +
            "<if test=\"loginName!='' and loginName!=null\">" +
            " and m.login_name LIKE CONCAT(CONCAT('%', #{loginName}), '%')" +
            "</if>" +
            "</script>")
    Long searchCount(@Param("loginName") String loginName, @Param("roleId") Long roleId);

    @Select("select n.role_id from sys_user m inner join sys_user_role n on m.id = n.user_id and m.id = #{id}")
    Long getUserRoleId(Long id);

    @Select("select n.role_id from sys_user m inner join sys_user_role n on m.id = n.user_id and m.id = #{id}")
    List<Long> getRoleUserIds(Long id);

    @SelectKey(statement = "select last_insert_id()",keyProperty = "id",keyColumn = "id", before = false, resultType = Long.class)
    @Insert("insert into sys_user(login_name,password,name,email,phone,user_id) " +
            " values(#{loginName},#{password},#{name},#{email},#{phone},#{userId})")
    void insertSysUser(SysUser sysUser);

    @Insert("insert into sys_user_role(user_id,role_id) " +
            " values(#{userId},#{roleId})")
    void insertUserRoleRel(UserRoleArg arg);

    @Update("update sys_user set login_name = #{loginName},name = #{name},email = #{email},phone = #{phone} " +
            " where id = #{id}")
    void updateSysUser(SysUser sysUser);

    @Delete("delete from sys_user where id = #{id}")
    void deleteSysUser(Long id);

    @Delete("delete from sys_user_role where user_id = #{userId}")
    void deleteUserRoleRel(Long userId);

    @Select("select name from sys_user where user_id=#{userId}")
    String getUserByUserId(String userId);
}