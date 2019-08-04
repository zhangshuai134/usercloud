package com.zs.user01.exception;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceMsgManager extends AbstractResourceManager {

    /**
     * 汉语资源包
     */
    public static ResourceBundle cnBundle = ResourceBundle.getBundle("msg", Locale.SIMPLIFIED_CHINESE);

    /**
     * 英文资源包
     */
    public static ResourceBundle usBundle = ResourceBundle.getBundle("msg", Locale.US);

    /**
     * 日文资源包
     */
    public static ResourceBundle jpBundle = ResourceBundle.getBundle("msg", Locale.JAPAN);


    /**
     * getResource
     *
     * @param key
     * @return
     */
    public static String getResource(String language, String key) {
        ResourceBundle resourceBundle = getResourceBundle(language);
        return getResource(resourceBundle, key);
    }

    /**
     * getResource
     *
     * @param key
     * @return
     */
    public static String getResource(String language, String key, Object... params) {
        ResourceBundle resourceBundle = getResourceBundle(language);
        return getResource(resourceBundle, key, params);
    }

    /**
     * 获取资源文件
     *
     * @param language
     * @return
     */
    private static ResourceBundle getResourceBundle(String language) {
        switch (getLocaleType(language)) {
            case CN:
                return cnBundle;
            case EN:
                return usBundle;
            case JP:
                return jpBundle;
            default:
                return usBundle;
        }
    }
}
