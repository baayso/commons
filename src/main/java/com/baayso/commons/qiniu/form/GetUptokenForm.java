package com.baayso.commons.qiniu.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

/**
 * 用于保存获取七牛上传授权凭证时客户端提交的数据。
 *
 * @author ChenFangjie (2015年1月14日 下午3:49:50)
 * @since 1.0.0
 */
public class GetUptokenForm {

    /**
     * <pre>
     * 七牛上传策略（PutPolicy）文档
     * http://developer.qiniu.com/docs/v6/api/reference/security/put-policy.html
     * </pre>
     */

    private String expires; // 有效时长，单位：秒（s）
    private String persistentOps; // 资源上传成功后触发执行的预转持久化处理指令列表。每个指令是一个API规格字符串，多个指令用英文分号（;）分隔
    private String persistentNotifyUrl; // 接收预转持久化结果通知的URL。必须是公网上可以正常进行POST请求并能响应 HTTP/1.1 200 OK 的有效URL
    private String persistentPipeline; // 转码队列名，资源上传成功后，触发转码时指定独立的队列进行转码。为空则表示使用公用队列，处理速度比较慢。建议使用专用队列

    @Pattern(regexp = "^[1-9]\\d{1,4}$", message = "格式不正确")
    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    @Length(min = 0, max = 255)
    public String getPersistentOps() {
        return persistentOps;
    }

    public void setPersistentOps(String persistentOps) {
        this.persistentOps = persistentOps;
    }

    @Length(min = 0, max = 255)
    public String getPersistentNotifyUrl() {
        return persistentNotifyUrl;
    }

    public void setPersistentNotifyUrl(String persistentNotifyUrl) {
        this.persistentNotifyUrl = persistentNotifyUrl;
    }

    @Pattern(regexp = "^[a-zA-Z0-9]{0,64}$", message = "必须由字母或数字组成，大小写不敏感，最长64个字符")
    public String getPersistentPipeline() {
        return persistentPipeline;
    }

    public void setPersistentPipeline(String persistentPipeline) {
        this.persistentPipeline = persistentPipeline;
    }

}
