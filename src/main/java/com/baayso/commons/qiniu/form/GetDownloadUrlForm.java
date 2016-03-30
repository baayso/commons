package com.baayso.commons.qiniu.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 用于保存获取获取七牛下载URL时客户端提交的数据。
 *
 * @author ChenFangjie (2015年1月14日 下午3:51:50)
 * @version 1.0.0
 * @since 1.0.0
 */
public class GetDownloadUrlForm {

    private String expires; // 有效时长，单位：秒（s）
    private String key; // 文件名

    @Pattern(regexp = "^[1-9]\\d{1,4}$", message = "格式不正确")
    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9\u4e00-\u9fa5~!@#$%^&()-_=+/.]{1,255}$", message = "无效的文件名")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
