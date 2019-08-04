package com.zs.user01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.user01.entity.SysResource;
import com.zs.user01.arg.SysResourceArg;
import com.zs.user01.vo.SysResourceVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SysResourceMapper extends BaseMapper<SysResource> {

    @Select("select sr.* from  sys_resource  sr left join sys_role_resource rsr on  sr.id=rsr.resource_id left join sys_user_role  ur " +
            "  on rsr.role_id=ur.role_id left join sys_user usr on usr.id=ur.user_id where usr.login_name=#{loginName} and sr.type=1")
    List<SysResource> getMenuListByLoginName(@Param("loginName") String loginName);


    @Select("select sr.name from  sys_resource  sr left join sys_role_resource rsr on  sr.id=rsr.resource_id left join sys_user_role  ur " +
            "  on rsr.role_id=ur.role_id left join sys_user usr on usr.id=ur.user_id where  sr.parent_id=#{menuId} and usr.login_name=#{loginName} and sr.type=2")
    List<String> getButtonByMenuIdAndLoginName(@Param("menuId") long menuId, @Param("loginName") String loginName);

    @Select("select * from sys_resource order by sort")
    List<SysResourceVo> selectAll();

    @Select("select * from sys_resource where parent_id = #{parentId} order by sort asc")
    List<SysResourceVo> selectByParentId(@Param("parentId") long parentId);

    @Insert("insert into sys_resource(name,sort,href,parent_id,type,remark) " +
            " values(#{name},#{sort},#{href},#{parentId},#{type},#{remark})")
    void insertSysResource(SysResourceArg arg);

    @Update("update sys_resource set name = #{name},sort = #{sort},href = #{href},parent_id = #{parentId},type = #{type}, " +
            "remark = #{remark} where id = #{id}")
    void updateSysResource(SysResource sysResource);

    @Delete("delete from sys_resource where id = #{id}")
    void deleteSysResource(@Param("id") Long id);

    @Delete("delete from sys_role_resource where resource_id = #{id}")
    void deleteRoleResourceRel(@Param("id") Long id);
}
