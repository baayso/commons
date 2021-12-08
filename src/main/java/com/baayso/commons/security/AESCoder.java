package com.baayso.commons.security;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;

import com.baayso.commons.log.Log;

/**
 * AES安全编码组件。
 *
 * @author ChenFangjie (2015/1/10 13:33:37)
 * @since 1.0.0
 */
public final class AESCoder {

    private static final Logger log = Log.get();

    /** 密钥算法 */
    public static final String KEY_ALGORITHM = "AES";

    /**
     * 加密解密算法/工作模式/填充方式(补码方式)，<br>
     * Java 6支持PKCS5Padding填充方式，<br>
     * Bouncy Castle支持PKCS7Padding填充方式。
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private static final String DEFAULT_KEY_SEED = "G7jX/RpqbqzbvUoxJ2fEaVdhk8e/axGXbhEXli2dR0TI=";

    /* 密钥长度为128位、192位或256位 */
    private static final int DEFAULT_KEY_SIZE = 256;

    private static byte[] keyBytes;

    public AESCoder() {
        this(DEFAULT_KEY_SIZE, DEFAULT_KEY_SEED);
    }

    public AESCoder(String keySeed) {
        this(DEFAULT_KEY_SIZE, keySeed);
    }

    public AESCoder(int keySize, String keySeed) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(keySeed.getBytes());
            generator.init(keySize, secureRandom);
            keyBytes = generator.generateKey().getEncoded();
            generator = null;
        }
        catch (Exception e) {
            log.error("初始化密钥出错！", e);
        }
    }

    /**
     * 对给定字符串进行加密。
     * <p>
     * 与 {@link AESCoder#decryptStr(String)} 配对使用。
     *
     * @param str 待加密的字符串
     *
     * @return 加密数据
     *
     * @see AESCoder#decryptStr(String)
     * @since 1.0.0
     */
    public String encryptStr(String str) {
        byte[] data = encrypt(StringUtils.getBytesUtf8(str), keyBytes);
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 对给定字符串进行解密。
     * <p>
     * 与 {@link AESCoder#encryptStr(String)} 配对使用。
     *
     * @param str 待解密的字符串
     *
     * @return 解密数据
     *
     * @see AESCoder#encryptStr(String)
     * @since 1.0.0
     */
    public String decryptStr(String str) {
        byte[] data = decrypt(Base64.getDecoder().decode(str), keyBytes);
        return StringUtils.newStringUtf8(data);
    }

    /**
     * 使用指定密钥对给定字符串进行加密。
     * <p>
     * 与 {@link AESCoder#decryptStr(String, String)} 配对使用。
     *
     * @param str       待加密的字符串
     * @param secretKey 秘钥，必须为16位字符
     *
     * @return 加密数据
     *
     * @see AESCoder#decryptStr(String, String)
     * @since 1.0.1
     */
    public String encryptStr(String str, String secretKey) {
        byte[] data = encrypt(StringUtils.getBytesUtf8(str), StringUtils.getBytesUtf8(secretKey));
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 使用指定密钥对给定字符串进行解密。
     * <p>
     * 与 {@link AESCoder#encryptStr(String, String)} 配对使用。
     *
     * @param str       待解密的字符串
     * @param secretKey 秘钥，必须为16位字符
     *
     * @return 解密数据
     *
     * @see AESCoder#encryptStr(String, String)
     * @since 1.0.1
     */
    public String decryptStr(String str, String secretKey) {
        byte[] data = decrypt(Base64.getDecoder().decode(str), StringUtils.getBytesUtf8(secretKey));
        return StringUtils.newStringUtf8(data);
    }

    /**
     * 生成密钥，使用默认长度。
     *
     * @return 二进制密钥
     *
     * @since 1.0.0
     */
    public static byte[] initKey() {
        return initKey(DEFAULT_KEY_SIZE);
    }

    /**
     * 生成指定长度的密钥。
     *
     * @return 二进制密钥
     *
     * @since 1.0.0
     */
    public static byte[] initKey(int keySize) {
        byte[] key = null;
        try {
            KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            kg.init(keySize); /* AES要求密钥长度为128位、192位或256位 */
            SecretKey secretKey = kg.generateKey(); // 生成秘密密钥
            key = secretKey.getEncoded(); // 获得密钥的二进制编码形式
        }
        catch (Exception e) {
            log.error("生成密钥出错！", e);
        }

        return key;
    }

    /**
     * 转换密钥。
     *
     * @param key 二进制密钥
     *
     * @return 密钥
     *
     * @since 1.0.0
     */
    public Key toKey(byte[] key) {
        // 实例化AES密钥材料
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 加密。
     * <p>
     * 与 {@link AESCoder#decrypt(byte[], byte[])} 配对使用。
     *
     * @param data 待加密数据
     * @param key  密钥
     *
     * @return 加密数据
     *
     * @see AESCoder#decrypt(byte[], byte[])
     * @since 1.0.0
     */
    public byte[] encrypt(byte[] data, byte[] key) {
        // 还原密钥
        Key k = toKey(key);

        byte[] encryptStrBytes = null;
        try {
            // 实例化 使用PKCS7Padding填充方式，按如下方式实现 Cipher.getInstance(CIPHER_ALGORITHM, "BC");
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, k);
            // 执行操作
            encryptStrBytes = cipher.doFinal(data);
        }
        catch (Exception e) {
            log.error("加密出错！", e);
        }

        return encryptStrBytes;
    }

    /**
     * 解密。
     * <p>
     * 与 {@link AESCoder#encrypt(byte[], byte[])} 配对使用。
     *
     * @param data 待解密数据
     * @param key  密钥
     *
     * @return 解密数据
     *
     * @see AESCoder#encrypt(byte[], byte[])
     * @since 1.0.0
     */
    public byte[] decrypt(byte[] data, byte[] key) {
        // 还原密钥
        Key k = toKey(key);

        byte[] decryptStrBytes = null;
        try {
            // 实例化使用PKCS7Padding填充方式，按如下方式实现 Cipher.getInstance(CIPHER_ALGORITHM, "BC");
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, k);
            // 执行操作
            decryptStrBytes = cipher.doFinal(data);
        }
        catch (Exception e) {
            log.error("解密出错！", e);
        }

        return decryptStrBytes;
    }

}
