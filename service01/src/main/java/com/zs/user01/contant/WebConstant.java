package com.zs.user01.contant;

public class WebConstant {
    public final static String  CAPTCHA_PREFIX_KEY="captchaCode_";
    public final static String  LOGIN_ERROR_TIMES_PREFIX_KEY="login_error_times_user_";
    public final static Integer LOGIN_ERROR_TIMES=5;
    public final static long LOGIN_ERROR_LIMIT_SECOND=60*30;
    public final static int SHIRO_HASH_ITERATIONS=54;
    public final static String SHIRO_HASH_ALGORITHM_NAME="MD5";
    public final static String SHIRO_SESSION_USER = "user";
    public final static String BOSS_GET_CONTRACT_URL_KEY="api.boss.getContractUrl";
    public final static String BOSS_GET_UPLOAD_DATA_URL_KEY="api.boss.getUploadDataUrl";
    public final static String WEB_FRONT_URI="web.front.uri";
    final public static String BYTEDANCE_APP_ID = "bytedance.app.id";
    final public static String BYTEDANCE_APP_SECRET = "bytedance.app.secret";
    final public static String BYTEDANCE_APP_CALLBACK = "bytedance.app.callback";
    public final static String TRANS_ROLE_PREFIX="ROLE.";

    /**
     * 数字500
     */
    public static final int NUM_INT_500 = 500;

    /**
     * 添加fbuser账号标识
     */
    public static final String USER_ADD_FLAG = "USER_ADD_FLAG";

    /**
     * 添加fbuser账号标识
     */
    public static final String FB_USER_ADD_FLAG = "FB_USER_ADD_FLAG";

    // 保存businessId时的分布式锁
    public static final String BUSINESS_MANAGE_LOCK = "business_manage_lock";

    //v2_4接口业务数据同步
    public static final String SYNC_BUSINESS_UPDATEDTIME = "sync_business_updatetime_";

    public  enum Lang{
        en,jp,cn;
        public String getName(){
            return  this.name();
        }
    }

}
