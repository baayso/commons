package com.baayso.commons.security.password;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 使用 BCrypt 实现的强哈希函数密码编码器。
 * 用户可以选择提供一个“版本”（$2a， $2b， $2y）和一个“强度”（在 BCrypt 中也称为 a.k.a. log rounds）和一个 SecureRandom 实例。
 * 强度参数越大，需要完成的工作（指数级）才能对密码进行哈希处理。
 * 默认值为 10。
 *
 * @author ChenFangjie (2023/02/28 09:23)
 * @see org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
 * @since 1.0.1
 */
public class SpringSecurityBCryptPasswordEncoder implements PasswordEncoder {

    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public SpringSecurityBCryptPasswordEncoder() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * @param strength the log rounds to use, between 4 and 31
     */
    public SpringSecurityBCryptPasswordEncoder(int strength) {
        this.passwordEncoder = new BCryptPasswordEncoder(strength);
    }

    /**
     * @param strength the log rounds to use, between 4 and 31
     * @param random   the secure random instance to use
     */
    public SpringSecurityBCryptPasswordEncoder(int strength, SecureRandom random) {
        this.passwordEncoder = new BCryptPasswordEncoder(strength, random);
    }

    public SpringSecurityBCryptPasswordEncoder(org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return this.passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return this.passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return this.passwordEncoder.upgradeEncoding(encodedPassword);
    }

}
