package com.zs.user01.exception;

import lombok.Getter;


@Getter
public enum ErrorType {
    service01,
    service02,
    mq;
    private String code;
}
