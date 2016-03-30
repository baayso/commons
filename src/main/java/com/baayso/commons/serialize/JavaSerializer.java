package com.baayso.commons.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;

import com.baayso.commons.log.Log;

/**
 * 使用标准Java API实现的序列化。
 *
 * @author ChenFangjie (2015年3月18日 下午2:47:27)
 * @since 1.0.0
 */
public class JavaSerializer implements Serializer {

    private static final Logger log = Log.get();

    public static final String JAVA_SERIALIZER = "java";

    @Override
    public String name() {
        return JAVA_SERIALIZER;
    }

    @Override
    public byte[] serialize(Object obj) {
        if (null == obj) {
            return new byte[0];
        }
        else if (!(obj instanceof Serializable)) {
            throw new IllegalArgumentException("[" + obj.getClass().getName() + "] does not implement java.io.Serializable interface.");
        }

        byte[] result = null;

        ByteArrayOutputStream bytesOutputStream = new ByteArrayOutputStream(128);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(bytesOutputStream)) {
            objectOutputStream.writeObject(obj);
            result = bytesOutputStream.toByteArray();
        }
        catch (IOException e) {
            log.error("Failed to serialize.", e);
        }
        catch (Exception e) {
            log.error("Failed to serialize.", e);
        }

        return result;
    }

    @Override
    public Object deserialize(byte[] bytes) {
        if (null == bytes || 0 == bytes.length) {
            return null;
        }

        Object result = null;

        try (ObjectInputStream objectOutputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            result = objectOutputStream.readObject();
        }
        catch (ClassNotFoundException e) {
            log.error("Class of a serialized object cannot be found.", e);
        }
        catch (IOException e) {
            log.error("Failed to deserialize.", e);
        }
        catch (Exception e) {
            log.error("Failed to deserialize.", e);
        }

        return result;
    }

}
