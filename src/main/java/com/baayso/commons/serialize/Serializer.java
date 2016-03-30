package com.baayso.commons.serialize;

/**
 * 对象序列化接口。
 *
 * @author ChenFangjie (2015年3月18日 下午2:43:55)
 * @since 1.0.0
 */
public interface Serializer {

    /**
     * 序列化程序的名称。
     *
     * @return
     *
     * @since 1.0.0
     */
    public String name();

    /**
     * 序列化给定的对象。
     *
     * @param obj 给定的对象
     *
     * @return
     *
     * @since 1.0.0
     */
    public byte[] serialize(Object obj);

    /**
     * 反序列化。
     *
     * @param bytes
     *
     * @return
     *
     * @since 1.0.0
     */
    public Object deserialize(byte[] bytes);

}
