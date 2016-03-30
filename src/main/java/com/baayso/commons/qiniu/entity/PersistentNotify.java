package com.baayso.commons.qiniu.entity;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 七牛云存储预转持久化结果通知。
 *
 * @author ChenFangjie (2015年4月16日 上午11:32:51)
 * @since 1.0.0
 */
public class PersistentNotify implements Serializable {

    private static final long serialVersionUID = -4441001491785952504L;

    private String  id;          // 持久化处理的进程ID，即前文中的<persistentId>
    private Integer code;        // 状态码，0（成功），1（等待处理），2（正在处理），3（处理失败），4（通知提交失败）
    private String  desc;        // 与状态码相对应的详细描述
    private String  inputKey;    // 处理源文件的文件名。
    private String  inputBucket; // 处理源文件所在的空间名
    private String  pipeline;    // 云处理操作的处理队列，默认使用队列为共享队列0.default
    private String  reqid;       // 云处理请求的请求id，主要用于七牛技术人员的问题排查

    private List<PersistentNotifyItem> items; // 云处理操作列表，包含每个云处理操作的状态信息

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getInputKey() {
        return inputKey;
    }

    public void setInputKey(String inputKey) {
        this.inputKey = inputKey;
    }

    public String getInputBucket() {
        return inputBucket;
    }

    public void setInputBucket(String inputBucket) {
        this.inputBucket = inputBucket;
    }

    public String getPipeline() {
        return pipeline;
    }

    public void setPipeline(String pipeline) {
        this.pipeline = pipeline;
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public List<PersistentNotifyItem> getItems() {
        return items;
    }

    public void setItems(List<PersistentNotifyItem> items) {
        this.items = items;
    }

}
