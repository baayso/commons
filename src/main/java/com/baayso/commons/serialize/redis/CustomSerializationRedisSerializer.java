package com.baayso.commons.serialize.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.baayso.commons.serialize.FSTSerializer;
import com.baayso.commons.serialize.Serializer;

/**
 * Custom Serialization Redis serializer.
 *
 * @author ChenFangjie (2017/2/26 11:12)
 * @since 1.0.0
 */
public class CustomSerializationRedisSerializer implements RedisSerializer<Object> {

    private final Serializer serializer;

    /**
     * Creates a new {@link CustomSerializationRedisSerializer} using the default serializer.
     *
     * @since 1.0.0
     */
    public CustomSerializationRedisSerializer() {
        this(new FSTSerializer());
    }

    /**
     * Creates a new {@link CustomSerializationRedisSerializer} using a {@link Serializer}.
     *
     * @param serializer {@link Serializer}
     *
     * @since 1.0.0
     */
    public CustomSerializationRedisSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        return this.serializer.serialize(o);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return this.serializer.deserialize(bytes);
    }

}
