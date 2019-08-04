package com.zs.user01.util;

import com.zs.user01.annotation.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

@Slf4j
public class ParamsValidateUtil {
    public static boolean validate(Object param) {
        Class paramClass = param.getClass();
        Field[] fields = paramClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.getAnnotation(NotNull.class) != null) {
                    Field dtoField = FieldUtils.getDeclaredField(paramClass, field.getName(), true);
                    Object value = dtoField.get(param);
                    if (value == null) {
                        return false;
                    }
                }

            } catch (Exception e) {
                log.error("Error! ParamsValidateUtil error>>" + e.getMessage());
                return false;
            }
        }
        return true;
    }
}
