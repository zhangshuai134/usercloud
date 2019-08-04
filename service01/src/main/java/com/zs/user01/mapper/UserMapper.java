package com.zs.user01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.user01.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where id=#{id}")
    User getUserById(String id);
}
