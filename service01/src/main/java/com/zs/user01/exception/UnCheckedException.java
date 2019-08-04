package com.zs.user01.exception;


public class UnCheckedException extends RuntimeException {

    private static final long serialVersionUID = -5237567037176940822L;

    private String errorCode;

    private String[] msgParams;

    private String errorMsg; // Technical error message

    public UnCheckedException() {
        this(null, null, null, null);
    }

    /**
     * @param cause
     * The cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public UnCheckedException(Throwable cause) {
        this(null, null, null, cause);
    }

    /**
     * @param errorMsg
     *            technical error message
     */
    public UnCheckedException(String errorMsg) {
        this(errorMsg, null, null, null);
    }

    /**
     * @param errorMsg
     *            technical error message
     *
     * @param cause
     *            the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public UnCheckedException(String errorMsg, Throwable cause) {

        // String errcode=cause.getCause()
        this(errorMsg, null, null, cause);
    }

    /**
     *
     * @param errorCode
     * @param params
     */
    public UnCheckedException(String errorCode, String[] params) {
        this(null, errorCode, params, null);
    }

    /**
     *
     * @param errorCode
     * @param params
     * @param cause
     */
    public UnCheckedException(String errorCode, String[] params, Throwable cause) {
        this(null, errorCode, params, cause);
    }

    /**
     *
     * @param errorMsg
     *            technical error message
     * @param errorCode
     * @param params
     */
    public UnCheckedException(String errorMsg, String errorCode, String[] params) {
        this(errorMsg, errorCode, params, null);
    }

    /**
     *
     * @param errorMsg
     *            error message in technical tongue
     * @param errorCode
     * @param params
     * @param cause
     */
    public UnCheckedException(String errorMsg, String errorCode, String[] params, Throwable cause) {
        super(cause);
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

        String dispMsg = null;

        if (errorCode != null) {
            dispMsg = ResourceManager.getInstance("exception").getResource(errorCode, msgParams);
        }

        Throwable cause = this.getCause();

        String causedDispMsg = null;
        if (cause != null) {
            if (cause instanceof UnCheckedException) {
                causedDispMsg = ((UnCheckedException) cause).getDisplayMessage();
            } else {
                if (cause instanceof CheckedException) {
                    causedDispMsg = ((CheckedException) cause).getDisplayMessage();
                }
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
        String msg = "";

        if (errorMsg != null && errorMsg.length() > 0) {
            msg += " " + errorMsg;
        } else {
            String dispMsg = getDisplayMessage();
            if (dispMsg != null && dispMsg.length() > 0) {
                msg += "" + dispMsg;
            }
        }
        return msg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String[] getmsgParams() {
        return msgParams;
    }


}
