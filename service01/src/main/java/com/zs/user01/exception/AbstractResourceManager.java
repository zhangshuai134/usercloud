package com.zs.user01.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.ResourceBundle;


public abstract class AbstractResourceManager {


    /**
     * 从资源文件中获取对应的值
     *
     * @param key
     * @return
     */
    public static String getResource(ResourceBundle resourceBundle, String key) {
        if(key==null) return "";
        if (resourceBundle.containsKey(key)) {
            return resourceBundle.getString(key);
        } else {
            return "";
        }
    }

    /**
     * 从资源文件中获取对应的值
     *
     * @param key
     * @return
     */
    static String getResource(ResourceBundle resourceBundle, String key, Object... params) {
        String resource = getResource(resourceBundle, key);
        if (!StringUtils.isBlank(resource)) {
            try {
                return java.text.MessageFormat.format(resource, params);
            } catch (Exception e) {
                return resource + Arrays.toString(params);
            }
        } else {
            return resource;
        }

    }


    /**
     * 获取语言类型
     *
     * @param language
     * @return
     */
    public static LocaleType getLocaleType(String language) {
        if ("en-us".equals(language) || "en_US".equals(language) || "en".equals(language) || "US".equals(language)) {
            return LocaleType.EN;
        } else if ("zh-cn".equals(language) || "zh_CN".equals(language) || "zh".equals(language) || "CN".equals(language)) {
            return LocaleType.CN;
        } else if ("ja-jp".equals(language) || "ja_JP".equals(language) || "ja".equals(language) || "JP".equals(language)) {
            return LocaleType.JP;
        } else {
            return LocaleType.EN;
        }
    }

}