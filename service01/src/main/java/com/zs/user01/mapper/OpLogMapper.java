package com.zs.user01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.zs.user01.entity.Oplog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OpLogMapper extends BaseMapper<Oplog> {

    @Insert("insert into op_log(ip,user_name,method_name,class_name,param_names,param_values,description,op_time) " +
            " values(#{ip},#{userName},#{methodName},#{className},#{paramNames},#{paramValues},#{description},#{opTime})")
    void insertOpLog(Oplog oplog);


    @Select("<script>" +
            "select * from op_log " +
            "where 1=1 " +
            "<if test=\"userName!='' and userName!=null\">" +
            " and user_name LIKE CONCAT(CONCAT('%', #{userName}), '%')" +
            "</if>" +
            "<if test=\"startTime!='' and startTime!=null and endTime!='' and endTime!=null \">" +
            " and op_time &gt;= #{startTime} and op_time &lt;= #{endTime}" +
            "</if>" +
            " order by op_time desc " +
            "LIMIT #{offset}, #{limit}" +
            "</script>")
    List<Oplog> searchList(@Param("offset") Integer offset, @Param("limit") Integer limit
            , @Param("userName") String userName, @Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select("<script>" +
            "select count(*) from op_log " +
            "where 1=1 " +
            "<if test=\"userName!='' and userName!=null\">" +
            " and user_name LIKE CONCAT(CONCAT('%', #{userName}), '%')" +
            "</if>" +
            "<if test=\"startTime!='' and startTime!=null and endTime!='' and endTime!=null \">" +
            " and op_time &gt;= #{startTime} and op_time &lt;= #{endTime}" +
            "</if>" +
            "</script>")
    Long searchCount(@Param("userName") String userName, @Param("startTime") String startTime, @Param("endTime") String endTime);

}
