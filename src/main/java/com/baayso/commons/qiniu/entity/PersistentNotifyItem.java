package com.baayso.commons.qiniu.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 七牛云存储预转持久化结果通知中的云处理操作列表，包含每个云处理操作的状态信息。
 *
 * @author ChenFangjie (2015年4月16日 上午11:37:25)
 * @since 1.0.0
 */
public class PersistentNotifyItem implements Serializable {

    private static final long serialVersionUID = 7868966473034910467L;

    private String  cmd;   // 所执行的云处理操作命令（fopN）
    private Integer code;  // 状态码，0（成功），1（等待处理），2（正在处理），3（处理失败），4（通知提交失败）
    private String  desc;  // 与状态码相对应的详细描述
    private String  error; // 如果处理失败，该字段会给出失败的详细原因
    private String  hash;  // 云处理结果保存在服务端的唯一hash标识
    private String  key;   // 云处理结果的外链资源名（Key）

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
