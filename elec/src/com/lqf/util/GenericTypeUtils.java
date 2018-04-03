package com.lqf.util;

import java.lang.reflect.ParameterizedType;

public class GenericTypeUtils {

    //泛型转换
    public static Class getGenericSuperClass(Class entity){
        //泛型转换，目的是将对应的泛型，转化成真实的对象类型
        //此时type表示：BaseAction<com.lqf.doamin.ElecText>
        ParameterizedType type= (ParameterizedType) entity.getGenericSuperclass();
        //entityClass:com.lqf.domain.ElecText
       Class entityClass= (Class) type.getActualTypeArguments()[0];
        return entityClass;
    }
}
