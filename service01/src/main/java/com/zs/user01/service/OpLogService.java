package com.zs.user01.service;

import com.alibaba.fastjson.JSON;
import com.zs.user01.dto.OpLogDto;
import com.zs.user01.entity.Oplog;
import com.zs.user01.mapper.OpLogMapper;
import com.zs.user01.util.Pager;
import com.zs.user01.vo.OplogVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpLogService {

    @Resource
    private OpLogMapper opLogMapper;

    public void saveOpLog(OpLogDto opLogDto) {
        System.out.println("保存操作日志:" + JSON.toJSONString(opLogDto));
        Oplog oplog = new Oplog();
        BeanUtils.copyProperties(opLogDto, oplog);
        opLogMapper.insertOpLog(oplog);
    }

    public Pager<OplogVo> searchOpLog(int pageNum, int pageSize, String userName, String startTime, String endTime) {
        //startTime 和 endTime相同时，默认查寻当天
        endTime = endTime + "59:59:59";
        List<OplogVo> oplogVos = searchList((pageNum - 1) * pageSize, pageSize, userName, startTime, endTime);
        Pager<OplogVo> oplogVoVoPager = new Pager<>(pageSize, pageNum, opLogMapper.searchCount(userName, startTime, endTime));
        oplogVoVoPager.setResult(oplogVos);
        return oplogVoVoPager;
    }
    private List<OplogVo> searchList(int pageNum, int pageSize, String userName, String startTime, String endTime) {
        return convertOplogVos(opLogMapper.searchList(pageNum, pageSize, userName, startTime, endTime));
    }

    private List<OplogVo> convertOplogVos(List<Oplog> oplogs) {
        List<OplogVo> oplogVos = new ArrayList<>();
        oplogs.forEach(oplog -> {
            OplogVo oplogVo = new OplogVo();
            BeanUtils.copyProperties(oplog,oplogVo);
            oplogVo.setOpTime(new DateTime(oplog.getOpTime()).toString("yyyy-MM-dd HH:mm:ss"));
            oplogVos.add(oplogVo);
        });
        return oplogVos;
    }
}
