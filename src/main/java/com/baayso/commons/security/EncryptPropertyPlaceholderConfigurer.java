package com.baayso.commons.security;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 继承 {@linkplain PropertyPlaceholderConfigurer} 的定义支持密文属性的属性配置器。
 *
 * @author ChenFangjie (2014/12/11 18:33:39)
 * @since 1.0.0
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private String[] encryptPropNames = {"jdbc.username", "jdbc.password", "redis.password", "mongodb.password", "email.password"};

    private AESCoder aesCoder = new AESCoder();

    // 对特定属性的属性值进行转换
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (this.isEncryptProp(propertyName)) {
            return this.aesCoder.decryptStr(propertyValue);
        }
        else {
            return propertyValue;
        }
    }

    /*
     * 判断是否是加密的属性。
     * 
     * @param propertyName
     * 
     * @return
     * 
     * @since 1.0.0
     */
    private boolean isEncryptProp(String propertyName) {
        for (String encryptPropName : this.encryptPropNames) {
            if (encryptPropName.equals(propertyName)) {
                return true;
            }
        }

        return false;
    }

    public String[] getEncryptPropNames() {
        return encryptPropNames;
    }

    public void setEncryptPropNames(String[] encryptPropNames) {
        this.encryptPropNames = encryptPropNames;
    }

    public AESCoder getAesCoder() {
        return aesCoder;
    }

    public void setAesCoder(AESCoder aesCoder) {
        this.aesCoder = aesCoder;
    }

}
