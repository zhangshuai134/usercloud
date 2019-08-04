package com.zs.user01.param;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@Setter
public class RedisParams implements Serializable {
    private static final long serialVersionUID = -932578518089898952L;
    private String url;
    private String uuid;
    private String methodName;
    private String accountId;
    private String params;
    private int retryTimes = 1;
    private boolean wait; //是否等待
    //回调包含apiCallbackTargetType和map参数
    private String targetAndEquipment;
    private String resultClassName;
    //post文件上传相关
    private String fileName;
    private byte[] context;
}
