package com.socialmarketing.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

public class ExtBeanUtils extends BeanUtils {  
    static {  
        ConvertUtils.register(new DateConvert(), java.util.Date.class);  
        ConvertUtils.register(new DateConvert(), java.sql.Date.class);  
        ConvertUtils.register(new DateConvert(), java.sql.Timestamp.class);  
    }  
  
    public static void copyProperties(Object dest, Object orig) {  
        try {  
            BeanUtils.copyProperties(dest, orig);  
        } catch (IllegalAccessException ex) {  
            ex.printStackTrace();  
        } catch (InvocationTargetException ex) {  
            ex.printStackTrace();  
        }  
    }  
}  
