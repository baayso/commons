package com.baayso.commons.security;

import com.vip.vjtools.vjkit.text.EncodeUtil;
import com.vip.vjtools.vjkit.text.HashUtil;

/**
 * 密码工具类。
 *
 * @author ChenFangjie (2014/12/30 9:44:08)
 * @see com.baayso.commons.security.password.BCryptPasswordEncoder
 * @since 1.0.0
 */
@Deprecated
public class PasswordUtils {

    private static final String HASH_ALGORITHM  = "SHA-1";
    private static final int    HASH_ITERATIONS = 1024;    // 迭代次数
    private static final int    SALT_SIZE       = 8;       // 盐长度

    // 让工具类彻底不可以实例化
    private PasswordUtils() {
        throw new Error("工具类不可以实例化！");
    }

    /**
     * 验证明文密码与正确的密文密码是否相等。
     *
     * @param password      正确的密文密码
     * @param salt          密码盐
     * @param enterPassword 明文密码
     *
     * @return 相等返回true，否则返回false
     *
     * @since 1.0.0
     */
    public static boolean matches(String password, String salt, String enterPassword) {

        if (password == null || salt == null || enterPassword == null) {
            return false;
        }

        return password.equals(encryptPassword(enterPassword, EncodeUtil.decodeHex(salt)));
    }

    /**
     * 给定明文密码生成安全密码，经过1024次 sha-1 hash。
     *
     * @param plainPassword 明文密码
     * @param salt          密码盐
     *
     * @return 安全密码
     *
     * @since 1.0.0
     */
    public static String encryptPassword(String plainPassword, byte[] salt) {
        byte[] hashPassword = HashUtil.sha1(plainPassword.getBytes(), salt, HASH_ITERATIONS);

        return EncodeUtil.encodeHex(hashPassword);
    }

    /**
     * 生成随机的8位Byte作为salt
     *
     * @since 1.0.0
     */
    public static byte[] generateSalt() {
        return HashUtil.generateSalt(SALT_SIZE);
    }

}
