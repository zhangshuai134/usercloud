package com.zs.user01.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {

    private static ResourceBundleMessageSource messageSource;

    @Autowired
    Translator(ResourceBundleMessageSource messageSource) {
        setMessageSource(messageSource);
    }

    public synchronized static void setMessageSource(ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    public static String toLocale(String lang, String msgCode) {
        Locale locale = new Locale(lang);
        return messageSource.getMessage(msgCode, null, locale);
    }
}
