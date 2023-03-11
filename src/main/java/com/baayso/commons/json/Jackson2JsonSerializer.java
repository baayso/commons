package com.baayso.commons.json;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.baayso.commons.log.Log;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * 使用Jackson2实现的JSON序列化和反序列化。
 *
 * @author ChenFangjie (2023/2/25 23:09)
 * @see com.vip.vjtools.vjkit.mapper.JsonMapper
 * @since 1.0.1
 */
public class Jackson2JsonSerializer implements JsonSerializer {

    private static final Logger log = Log.get();

    private final ObjectMapper objectMapper;

    public Jackson2JsonSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String toJson(Object object) {
        try {
            return this.objectMapper.writeValueAsString(object);
        }
        catch (IOException e) {
            log.warn("write to json string error:" + object, e);
            return null;
        }
    }

    @Override
    public <T> T toBean(String jsonString, Class<T> clazz) {
        return this.fromJson(jsonString, clazz);
    }

    @Override
    public <T> T toBean(String jsonString, Class<T> parametrized, Class<?>... parameterClasses) {
        JavaType type = this.getTypeFactory().constructParametricType(parametrized, parameterClasses);

        return this.fromJson(jsonString, type);
    }

    @Override
    public <T> List<T> toList(String jsonString, Class<T> clazz) {
        JavaType type = this.getTypeFactory().constructCollectionType(List.class, clazz);

        return this.fromJson(jsonString, type);
    }

    @Override
    public <K, V> Map<K, V> toMap(String jsonString, Class<K> keyClass, Class<V> valueClass) {
        JavaType type = this.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);

        return this.fromJson(jsonString, type);
    }

    /**
     * 反序列化POJO或简单集合，如：{@code List<String>}、{@code Map<String, String>}。
     * <p>
     * 如果JSON字符串为 {@code null}、{@code ""} 或者 {@code "null"} 字符串则返回 {@code null}。
     * 如果JSON字符串为 {@code "[]"} 则返回空集合。
     * <p>
     * 如需反序列化复杂的集合，如：{@code List<MyBean>}，请使用：{@link #fromJson(String, JavaType)}
     *
     * @param jsonString JSON字符串
     * @param clazz      反序列化后的结果类型
     * @param <T>        Bean类型
     *
     * @return 反序列化后的对象
     *
     * @see #fromJson(String, JavaType)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return this.objectMapper.readValue(jsonString, clazz);
        }
        catch (IOException e) {
            log.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 反序列化复杂集合，如：{@code List<MyBean>}。
     * 请先使用 {@link #constructCollectionType(Class, Class)} 或者
     * {@link #constructMapType(Class, Class, Class)} 构造类型，然后调用本函数。
     *
     * @param jsonString JSON字符串
     * @param valueType  反序列化后的结果类型
     * @param <T>        Bean类型
     *
     * @return 自定义实体类对象集合
     *
     * @see #constructCollectionType(Class, Class)
     * @see #constructMapType(Class, Class, Class)
     */
    public <T> T fromJson(String jsonString, JavaType valueType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return this.objectMapper.readValue(jsonString, valueType);
        }
        catch (IOException e) {
            log.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 构造Collection类型。
     *
     * @param collectionClass Collection的类型
     * @param elementClass    Collection中元素的类型
     *
     * @return 构造的类型
     *
     * @see TypeFactory#constructCollectionType(Class, Class)
     */
    public Type constructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return this.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    /**
     * 构造Map类型。
     *
     * @param mapClass   Map的类型
     * @param keyClass   Map中元素key的类型
     * @param valueClass Map中元素value的类型
     *
     * @return 构造的类型
     *
     * @see TypeFactory#constructMapType(Class, Class, Class)
     */
    public Type constructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return this.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    /**
     * JavaType javaType = this.objectMapper.getTypeFactory().constructParametricType(ResultVO.class, Map.class);
     * <p>
     * ResultVO<Map<String, Object>> result = this.fromJson(result, javaType);
     *
     * @see TypeFactory#constructParametricType(Class, Class[])
     */
    public Type constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return this.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    /**
     * Accessor for getting currently configured {@link TypeFactory} instance.
     * <p>
     * 用于获取当前配置的 {@link TypeFactory} 实例的访问器。
     *
     * @see ObjectMapper#getTypeFactory()
     */
    public TypeFactory getTypeFactory() {
        return this.objectMapper.getTypeFactory();
    }

}
