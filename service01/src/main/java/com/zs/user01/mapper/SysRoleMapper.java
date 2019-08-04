package com.zs.user01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.user01.entity.RoleMenu;
import com.zs.user01.entity.SysRole;
import com.zs.user01.vo.SysRoleVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("select a.* from sys_role a left join sys_user_role b on a.id=b.role_id where  b.user_id=#{id}")
    List<SysRole> findByUserid(String id);


    @Select("select c.id as menuId,c.name as menuName,c.href as menuUrl,a.id as roleId,a.enname as roleEnName from sys_role a " +
            " left join sys_role_resource b on a.id=b.role_id left join sys_resource c on c.id=b.resource_id ")
    List<RoleMenu> findRoleMenu();

    @Select("select m.href as menuUrl,CONCAT('roles[',GROUP_CONCAT(t.enname),'],user,kickout') as roleEnName  from sys_resource m " +
            "left join sys_role_resource n on m.id = n.resource_id " +
            "left join sys_role t on n.role_id = t.id " +
            "where LENGTH(m.href)>0  GROUP BY m.id")
    List<RoleMenu> findHrefRole();

    @Select("<script>" +
            "select * from sys_role " +
            "where 1=1 " +
            "<if test=\"name!='' and name!=null\">" +
            " and name LIKE CONCAT(CONCAT('%', #{name}), '%')" +
            "</if>" +
            " order by id asc " +
            "LIMIT #{offset}, #{limit}" +
            "</script>")
    List<SysRoleVo> searchList(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("name") String name);

    @Select("<script>" +
            "select count(*) from sys_role " +
            "where 1=1 " +
            "<if test=\"name!='' and name!=null\">" +
            " and name LIKE CONCAT(CONCAT('%', #{name}), '%')" +
            "</if>" +
            "</script>")
    Long searchCount(@Param("name") String name);

    @Select("<script>" +
            "select * from sys_role " +
            "where 1=1 " +
            "<if test=\"roleId!=4\">" +
            " and id!=4 " +
            "</if>" +
            " order by id asc " +
            "</script>")
    List<SysRoleVo> selectSysRoleAll(@Param("roleId") long roleId);

    @Select("select distinct resource_id from sys_role_resource where role_id = #{roleId}")
    List<Long> getRoleResource(@Param("roleId") long roleId);

    @SelectKey(statement = "select last_insert_id()",keyProperty = "id",keyColumn = "id", before = false, resultType = Long.class)
    @Insert("insert into sys_role(name,enname) " +
            " values(#{name},#{enname})")
    void insertSysRole(SysRole sysRole);

    @Update("update sys_role set name = #{name},enname = #{enname} where id = #{id}")
    void updateSysRole(SysRole sysRole);

    @Delete("delete from sys_role where id = #{roleId}")
    void deleteSysRole(@Param("roleId") long roleId);

    @Delete("delete from sys_role_resource where role_id = #{roleId}")
    void deleteRoleResourceRel(@Param("roleId") long roleId);

    @Delete("delete from sys_user_role where role_id = #{roleId}")
    void deleteRoleUserRel(@Param("roleId") long roleId);

    @Select("select count(*) from sys_user_role where role_id = #{roleId}")
    Long checkRoleUser(@Param("roleId") long roleId);

}
