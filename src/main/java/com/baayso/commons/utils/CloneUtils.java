package com.baayso.commons.utils;

import java.io.Serializable;

import com.baayso.commons.serialize.SerializationUtils;

/**
 * 使用序列化实现对象的（深）拷贝。
 *
 * @author ChenFangjie (2017/6/26 16:11)
 * @since 1.0.0
 */
public class CloneUtils {

    /**
     * 使用序列化实现对象的（深）拷贝。
     *
     * @param obj 需要拷贝的对象
     * @param <T> 对象的类型
     *
     * @return 拷贝产生的新对象
     *
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj) {
        T newObj; // 拷贝产生的新对象

        byte[] bytes = SerializationUtils.serialize(obj);
        newObj = (T) SerializationUtils.deserialize(bytes);

        return newObj;
    }

}
