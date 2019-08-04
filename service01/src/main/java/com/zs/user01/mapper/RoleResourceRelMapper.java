package com.zs.user01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.user01.arg.RoleResourceArg;
import com.zs.user01.entity.RoleResourceRel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface RoleResourceRelMapper extends BaseMapper<RoleResourceRel> {

    @Insert("insert into sys_role_resource(role_id,resource_id) " +
            " values(#{roleId},#{resourceId})")
    void insertRoleResourceRel(RoleResourceArg arg);


    @Delete("delete from sys_role_resource where role_id = #{roleId}")
    void deleteRoleResourceRel(@Param("roleId") Long roleId);
}
