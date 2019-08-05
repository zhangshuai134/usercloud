package com.zs.user01.controller;

import com.zs.user01.dto.BaseResponseDTO;
import com.zs.user01.service.OpLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/oplog")
@Slf4j
public class OplogController {

    private final static String REDIS_KEY_FOR_SQL_EXECUTE = "redis_key_for_sql_execute";

    @Resource
    private OpLogService opLogService;


    @GetMapping("/search")
    public BaseResponseDTO searchOplog(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                       @RequestParam(value = "userName", required = false) String userName,
                                       @RequestParam(value = "startTime", required = false) String startTime,
                                       @RequestParam(value = "endTime", required = false) String endTime) {
        return BaseResponseDTO.buildSuccess(opLogService.searchOpLog(pageNum, pageSize, userName, startTime, endTime));
    }
}
