package com.zs.user01.param;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class BaseParam implements Serializable {
    private static final long serialVersionUID = -932578518089898952L;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 每次请求的唯一标示
     */
    private String uuid;

    /**
     * key
     */
    private String key;

    /**
     * 账号
     */
    private String accountId;

    /**
     * 请求的一些参数
     */
    private String params;

    //---异步回调相关
    /**
     * apiCallbackTargetType
     */
    private String apiCallbackTargetType;
    private Map<String, String> equipment = new HashMap<>();
    //是否回调? 异步请求：同步请求。
    private Boolean callBack;
    //---异步相关end

    /**
     * 重试次数
     */
    private Integer retryTimes;

    // 是否从AOP异步化调用而来
    Boolean fromAopAsync = false;

    String accessToken;
    String accessTokenSecret;
    String refreshToken;

    //api调用优先级
    Integer apiPriorityCode;
    //返回的对象类型
    String resultClassName;

    //---分页相关
    Integer startIndex; // 开始索引 >=0
    Integer pageSize;
    //---分页相关end

}
