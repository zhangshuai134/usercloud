package com.zs.user01.dto;


import com.zs.user01.contant.HTTPState;

public class BaseResponseDTO<T> {
    private int code;
    private String msg;
    private T data;

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
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseResponseDTO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseResponseDTO buildSuccess(T data) {
        return  new BaseResponseDTO(HTTPState.OK.getCode(), HTTPState.OK.getMsg(), data);
    }

    public static BaseResponseDTO buildSuccess() {
        return  new BaseResponseDTO(HTTPState.OK.getCode(), HTTPState.OK.getMsg(), null);
    }

    public static BaseResponseDTO buildError(int code, String msg) {
        return  new BaseResponseDTO(code, msg, null);
    }

    public static BaseResponseDTO buildEexception() {
        return  new BaseResponseDTO(HTTPState.SERVER_ERROR.getCode(), HTTPState.SERVER_ERROR.getMsg(), null);
    }
}
