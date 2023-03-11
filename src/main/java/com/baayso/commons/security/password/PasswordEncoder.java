/*
 * Copyright 2011-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.baayso.commons.security.password;

/**
 * 用于对密码进行编码的服务接口。
 * <p>
 * 首选实现是 {@code BCryptPasswordEncoder}。
 *
 * @author Keith Donald
 * @see org.springframework.security.crypto.password.PasswordEncoder
 * @since 1.0.0
 */
public interface PasswordEncoder {

    /**
     * 对原始密码进行编码。通常，良好的编码算法会应用 SHA-1 或更大的哈希值以及 8个字节 或更大的随机生成盐。
     */
    String encode(CharSequence rawPassword);

    /**
     * 验证从存储中获取的编码密码是否与提交的原始密码匹配。
     * 如果密码匹配，则返回 true；如果不匹配，则返回 false。
     * 存储的密码本身永远不会被解码。
     *
     * @param rawPassword     要编码和匹配的原始密码
     * @param encodedPassword 存储中要与之比较的编码密码
     *
     * @return 如果编码后的原始密码与存储中的编码密码匹配，则为 true。
     */
    boolean matches(CharSequence rawPassword, String encodedPassword);

    /**
     * 如果应再次对编码的密码以提高安全性，则返回 true，否则返回 false。
     * 默认实现始终返回 false。
     *
     * @param encodedPassword 要检查的编码密码
     *
     * @return 如果应再次对编码的密码进行编码以提高安全性，则为 true，否则为 false。
     */
    default boolean upgradeEncoding(String encodedPassword) {
        return false;
    }

}
