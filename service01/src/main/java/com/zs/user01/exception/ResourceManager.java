package com.zs.user01.exception;

import java.util.*;


public class ResourceManager {

    private static ResourceManager instance;
    private static HashMap<String, HashMap<String, String>> resources = new HashMap<String, HashMap<String, String>>();
    private static HashMap<String, String> resource = new HashMap<String, String>();
    private static Locale locale = new Locale("en", "US");

    private ResourceManager(String baseName) {
        init(baseName);
    }

    private void init(String baseName) {
        try{
            //可以读取环境变量
            ResourceBundle exceptions = ResourceBundle.getBundle(baseName, locale);
            resource = new HashMap<>();
            Set<String> keys = exceptions.keySet();
            for (String key : keys) {
                resource.put(key, exceptions.getString(key));
            }
            resources.put(baseName, resource);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale locale) {
        ResourceManager.locale = locale;
    }

    public synchronized static ResourceManager getInstance(String baseName) {
        resource = resources.get(baseName);
        if (resource == null) {
            instance = new ResourceManager(baseName);
        }
        return instance;
    }

    public void setResource(ResourceBundle resourcebundle) {
        Set<String> keys = resourcebundle.keySet();
        for (String key : keys) {
            resource.put(key, resourcebundle.getString(key));
        }
    }

    /**
     * getResource
     *
     * @param key
     * @return
     */
    public String getResource(String key) {
        return resource.get(key);
    }

    /**
     * getResource
     *
     * @param key
     * @param params
     * @return
     */
    public String getResource(String key, Object... params) {
        String resStr = getResource(key);
        if (resStr == null) {
            return null;
        } else {
            try {
                return java.text.MessageFormat.format(resStr, params);
            }catch (Exception e){
                return  resStr+ Arrays.toString(params);
            }
        }
    }
}
