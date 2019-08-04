package com.zs.user01.exception;


import java.util.Locale;


public class CheckedException extends Exception  {

    private static final long serialVersionUID = -6658424340296267525L;

    private String defaultShowMsg =  Locale.US.toString();

    protected String errorCode;

    protected String[] msgParams;

    protected String errorMsg; // Technical error message

    protected ErrorType errorType;

    /**
     * 抛出异常时希望返回的一些数据
     */
    protected Object data;

    public CheckedException() {
        this(null, null, null, null);
    }

    public CheckedException(ErrorType errorType){
        this(null,errorType.getCode(),null,null);
        this.errorType=errorType;
        this.errorCode = errorType.getCode() + "";
    }

    public CheckedException(ErrorType errorType, Object data){
        this(null,errorType.getCode(),null,null);
        this.errorType=errorType;
        this.errorCode = errorType.getCode() + "";
        this.data = data;
    }


    public CheckedException(String errorMsg) {
        this(errorMsg, null, null, null);
    }

    /**
     * @param cause
     *  The cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public CheckedException(Throwable cause) {
        this(null, null, null, cause);
    }

    /**
     * @param errorMsg
     *            technical error message
     *
     * @param cause
     *            the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public CheckedException(String errorMsg, Throwable cause) {
        this(errorMsg, null, null, cause);
    }

    /**
     * @param errorCode
     * @param params1
     */
    public CheckedException(String errorCode, String... params1) {
        this(null, errorCode, params1, null);
    }
    /**
     *
     * @param errorCode
     * @param params
     * @param cause
     */
    public CheckedException(String errorCode, String[] params, Throwable cause) {
        this(null, errorCode, params, cause);
    }

    /**
     *
     * @param errorMsg
     *            technical error message
     * @param errorCode
     * @param params
     */
    public CheckedException(String errorMsg, String errorCode, String[] params) {
        this(errorMsg, errorCode, params, null);
    }

    /**
     *
     * @param errorCode
     * @param errorMsg
     * @param cause
     */
    public CheckedException(String errorCode, String errorMsg, Throwable cause) {
        this(errorMsg, errorCode, null, cause);
    }

    /**
     *
     * @param errorMsg
     *            error message in technical tongue
     * @param errorCode
     * @param params
     * @param cause
     */
    public CheckedException(String errorMsg, String errorCode, String[] params, Throwable cause) {
        super(errorMsg,cause);
        this.errorMsg = errorMsg;
        if (errorCode != null) {
            this.errorCode = errorCode;
            this.msgParams = params;
        }
    }

    /**
     * Get user friendly error message of this exception
     *
     * @return user friendly error message of this exception. (which may be <tt>null</tt>).
     */
    public String getDisplayMessage() {


        String dispMsg = ResourceMsgManager.getResource(defaultShowMsg, errorCode, msgParams);

        Throwable cause = this.getCause();

        String causedDispMsg = null;
        if (cause != null) if (cause instanceof UnCheckedException) {
            causedDispMsg = ((UnCheckedException) cause).getDisplayMessage();
        } else {
            if (cause instanceof CheckedException) {
                causedDispMsg = ((CheckedException) cause).getDisplayMessage();
            }
        }

        if (dispMsg != null) {
            if (causedDispMsg != null) {
                dispMsg += "\n  Caused by: " + causedDispMsg;
            }
        } else {
            if (causedDispMsg != null) {
                dispMsg = causedDispMsg;
            }
        }

        return dispMsg;
    }

    /**
     * Get debug error message of this exception. This method overrides the same method in the parent class.
     *
     * @return detail error message of this exception. (which may be <tt>null</tt>).
     */
    public String getMessage() {
        if(errorCode==null||errorCode.length()==0){
            return this.errorMsg;
        } else{
            return ResourceMsgManager.getResource(defaultShowMsg,errorCode,msgParams);
        }

    }

    /**
     * Get user friendly error message of this exception
     *
     * @return user friendly error message of this exception. (which may be <tt>null</tt>).
     */

    public String getErrorCode() {
        return errorCode;
    }

    public ErrorType getErrorType(){
        return this.errorType;
    }

    public String[] getMsgParams() {
        return msgParams;
    }

    public void setDefaultShowMsg(String defaultShowMsg) {
        this.defaultShowMsg = defaultShowMsg;
    }
}
