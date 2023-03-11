package com.baayso.commons.security.password;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import cn.hutool.crypto.digest.BCrypt;

import com.baayso.commons.log.Log;

/**
 * 使用 BCrypt 实现的强哈希函数密码编码器。
 * 用户可以提供一个“强度”（在 BCrypt 中也称为 a.k.a. log rounds）和一个 SecureRandom 实例。
 * 强度参数越大，需要完成的工作（指数级）才能对密码进行哈希处理。
 * 默认值为 10。
 *
 * @author ChenFangjie (2023/02/28 10:37)
 * @see org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
 * @since 1.0.1
 */
public class HuToolBCryptPasswordEncoder implements PasswordEncoder {

    private static final Logger log = Log.get();

    // private final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
    private final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");

    private final int strength;

    private final SecureRandom random;

    static final int MIN_LOG_ROUNDS = 4;
    static final int MAX_LOG_ROUNDS = 31;

    public HuToolBCryptPasswordEncoder() {
        this(-1);
    }

    /**
     * @param strength the log rounds to use, between 4 and 31
     */
    public HuToolBCryptPasswordEncoder(int strength) {
        this(strength, null);
    }

    /**
     * @param strength the log rounds to use, between 4 and 31
     * @param random   the secure random instance to use
     */
    public HuToolBCryptPasswordEncoder(int strength, SecureRandom random) {
        if (strength != -1 && (strength < MIN_LOG_ROUNDS || strength > MAX_LOG_ROUNDS)) {
            throw new IllegalArgumentException("Bad strength");
        }

        this.strength = strength;
        this.random = random;
    }

    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }

        String salt;

        if (strength > 0) {
            if (random != null) {
                salt = BCrypt.gensalt(strength, random);
            }
            else {
                salt = BCrypt.gensalt(strength);
            }
        }
        else {
            salt = BCrypt.gensalt();
        }

        return BCrypt.hashpw(rawPassword.toString(), salt);
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }

        if (encodedPassword == null || encodedPassword.length() == 0) {
            log.warn("Empty encoded password");
            return false;
        }

        Matcher matcher = this.BCRYPT_PATTERN.matcher(encodedPassword);

        if (!matcher.matches()) {
            log.warn("Encoded password does not look like BCrypt");
            return false;
        }

        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        if (encodedPassword == null || encodedPassword.length() == 0) {
            log.warn("Empty encoded password");
            return false;
        }

        Matcher matcher = this.BCRYPT_PATTERN.matcher(encodedPassword);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Encoded password does not look like BCrypt: " + encodedPassword);
        }

        int strength = Integer.parseInt(matcher.group(2));

        return strength < this.strength;
    }

}
