package com.baayso.commons.json;

import java.util.List;
import java.util.Map;

/**
 * JSON序列化和反序列化。
 *
 * @author ChenFangjie (2023/2/25 22:21)
 * @since 1.0.1
 */
public interface JsonSerializer {

    /**
     * 将 POJO、Collection、数组、对象 转换为JSON字符串。
     * <p>
     * 如果对象为null则返回"null"。如果为空集合则返回"[]"。
     *
     * @param object POJO、Collection、数组、对象
     *
     * @return JSON字符串
     */
    String toJson(Object object);

    /**
     * 将JSON字符串反序列化为对象（POJO）。
     * <p>
     * 如果JSON字符串为 {@code null}、{@code ""} 或者 {@code "null"} 字符串则返回 {@code null}。
     * <p>
     * 如需反序列化复杂的对象，如：{@code ResultVO<TestUser>}，请使用：{@link #toBean(String, Class, Class[])}
     *
     * @param jsonString JSON字符串
     * @param clazz      Bean类型
     * @param <T>        Bean类型
     *
     * @return 反序列化后得到的对象
     *
     * @see #toBean(String, Class, Class[])
     */
    <T> T toBean(String jsonString, Class<T> clazz);

    /**
     * 反序列化复杂的对象，如：{@code ResultVO<TestUser>}。
     * <p>
     * 如果JSON字符串为 {@code null}、{@code ""} 或者 {@code "null"} 字符串则返回 {@code null}。
     *
     * @param jsonString       JSON字符串
     * @param parametrized     实际完整类型
     * @param parameterClasses 要应用的参数类型
     * @param <T>              Bean类型
     *
     * @return 反序列化后得到的对象
     */
    <T> T toBean(String jsonString, Class<T> parametrized, Class<?>... parameterClasses);

    /**
     * 将JSON字符串反序列化为List，如：{@code List<String>}、{@code List<MyBean>}。
     * <p>
     * 如果JSON字符串为 {@code null}、{@code ""} 或者 {@code "null"} 字符串则返回 {@code null}。
     * 如果JSON字符串为 {@code "[]"} 则返回空List。
     *
     * @param jsonString JSON字符串
     * @param clazz      Bean类型
     * @param <T>        Bean类型
     *
     * @return 反序列化后得到的List
     */
    <T> List<T> toList(String jsonString, Class<T> clazz);

    /**
     * 将JSON字符串反序列化为Map，如：{@code Map<String, String>}、{@code Map<String, Object>}。
     * <p>
     * 如果JSON字符串为 {@code null}、{@code ""} 或者 {@code "null"} 字符串则返回 {@code null}。
     *
     * @param jsonString JSON字符串
     * @param keyClass   Map的key类型
     * @param valueClass Map的value类型
     * @param <K>        Map的key类型
     * @param <V>        Map的value类型
     *
     * @return 反序列化后得到的Map
     */
    <K, V> Map<K, V> toMap(String jsonString, Class<K> keyClass, Class<V> valueClass);

}
