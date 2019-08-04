package com.zs.user01.contant;


public enum HTTPState {
    CAPTCHA_ERROR(401,"captcha error"),//验证码错误
    PARAM_ERROR(400, "param error"),//参数错误
    UNAUTH(405, "unauth"),//没有权限
    KICKOUT(406, "kickout"),//踢出登录
    SHIROFAIL(200, "shirofail"),//验证失败
    USERNOTEXIST(404, "user not exist"),//用户不存在
    SYSTEMERROR(500, "system error"),//系统错误
    USERLOCKED(403, "user locked"),//账号被锁定
    OK(200, "success"),//成功
    SERVER_ERROR(500,"server error"),//服务器端异常
    BUSINESS_ERROR(501,"business error"),//业务异常

    ;

    private int code;
    private String msg;

    HTTPState(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }}
