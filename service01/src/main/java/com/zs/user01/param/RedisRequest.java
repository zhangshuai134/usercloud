package com.zs.user01.param;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public abstract class RedisRequest implements Serializable {
    String uuid;
    String accountId;
    BaseParam baseParam;
    String beanClassSimpleName;
    String methodName;
    List<String> parameterTypeNames;
    Object[] args;
    Double score;
    public abstract String getScoreRedisPrefix();
    public abstract String getIncreaseRedisPrefix();
    public abstract String getResponseRedisPrefix();
}
