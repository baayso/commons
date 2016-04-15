package com.baayso.commons.qiniu;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.baayso.commons.log.Log;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 七牛云存储操作封装。
 *
 * @author ChenFangjie (2014/12/16 16:30:41)
 * @since 1.0.0
 */
public class QiniuProvider {

    private static final Logger log = Log.get();

    private static final String DEFAULT_RETURN_BODY = "{\"key\": $(key), \"path\": $(key), \"oname\": $(fname), \"size\": $(fsize), \"mime\":$(mimeType), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height)}";

    private String accessKey; // 访问公钥
    private String secretKey; // 访问私钥

    private String publicBucketName; // 公有空间名称
    private String publicBucketDomain; // 公有空间推荐域名
    private String publicBucketHttpsDomain; // 公有空间HTTPS域名

    private String privateBucketName; // 私有空间名称
    private String privateBucketDomain; // 私有空间推荐域名
    private String privateBucketHttpsDomain; // 私有空间HTTPS域名

    private String returnBody; // 上传成功后，自定义七牛云最终返回給上传端（在指定returnUrl时是携带在跳转路径参数中）的数据
    private String persistentPipeline; // 转码队列名，资源上传成功后，触发转码时指定独立的队列进行转码。为空则表示使用公用队列，处理速度比较慢。建议使用专用队列

    private Auth             auth;             // 密钥配置
    private UploadManager    uploadManager;    // 上传对象
    private BucketManager    bucketManager;    // 空间资源管理对象
    private OperationManager operationManager; // 触发持久化处理对象

    public QiniuProvider() {
        this.auth = Auth.create(this.accessKey, this.secretKey);
        this.uploadManager = new UploadManager();
        this.bucketManager = new BucketManager(this.auth);
        this.operationManager = new OperationManager(this.auth);
    }

    public QiniuProvider(QiniuConfigurable config) {
        this.accessKey = config.accessKey();
        this.secretKey = config.secretKey();

        this.publicBucketName = config.publicBucketName();
        this.publicBucketDomain = config.publicBucketDomain();
        this.publicBucketHttpsDomain = config.publicBucketHttpsDomain();

        this.privateBucketName = config.privateBucketName();
        this.privateBucketDomain = config.privateBucketDomain();
        this.privateBucketHttpsDomain = config.privateBucketHttpsDomain();

        this.auth = Auth.create(this.accessKey, this.secretKey);
        this.uploadManager = new UploadManager();
        this.bucketManager = new BucketManager(this.auth);
        this.operationManager = new OperationManager(this.auth);
    }

    /**
     * 获取七牛云存储<b>私有空间</b>上传授权凭证（uptoken）。
     * <p/>
     * 有效时长为1小时即3600秒。一个 uptoken 可以被用于多次上传（只要它还没有过期）。
     * <p/>
     * uptoken是一个字符串，作为http协议Header的一部分（Authorization字段）发送到七牛服务端，表示这个http是经过用户授权的。
     *
     * @return 私有空间上传授权凭证（uptoken）
     *
     * @since 1.0.0
     */
    public String getPrivateUptoken() {
        return this.getPrivateUptoken(StringUtils.EMPTY);
    }

    /**
     * 获取七牛云存储<b>私有空间</b>上传授权凭证（uptoken）。
     * <p/>
     * uptoken是一个字符串，作为http协议Header的一部分（Authorization字段）发送到七牛服务端，表示这个http是经过用户授权的。
     *
     * @param expires 指定 uptoken 有效时长。单位：秒（s），默认1小时即3600秒。一个 uptoken 可以被用于多次上传（只要它还没有过期）。
     *
     * @return 私有空间上传授权凭证（uptoken）
     *
     * @since 1.0.0
     */
    public String getPrivateUptoken(String expires) {
        return this.getPrivateUptoken(expires, null, null, null);
    }

    /**
     * 获取七牛云存储<b>私有空间</b>上传授权凭证（uptoken）。
     * <p/>
     * uptoken是一个字符串，作为http协议Header的一部分（Authorization字段）发送到七牛服务端，表示这个http是经过用户授权的。
     * <p/>
     * <pre>
     * 七牛上传策略（PutPolicy）文档
     * http://developer.qiniu.com/docs/v6/api/reference/security/put-policy.html
     * </pre>
     *
     * @param expires             有效时长，单位：秒（s）
     * @param persistentOps       资源上传成功后触发执行的预转持久化处理指令列表。每个指令是一个API规格字符串，多个指令用英文分号（;）分隔
     * @param persistentNotifyUrl 接收预转持久化结果通知的URL。必须是公网上可以正常进行POST请求并能响应 HTTP/1.1 200 OK 的有效URL
     * @param persistentPipeline  转码队列名，资源上传成功后，触发转码时指定独立的队列进行转码。为空则表示使用公用队列，处理速度比较慢。建议使用专用队列
     *
     * @return 私有空间上传授权凭证（uptoken）
     *
     * @since 1.0.0
     */
    public String getPrivateUptoken(String expires, String persistentOps, String persistentNotifyUrl, String persistentPipeline) {
        return this.getUptoken(expires, this.privateBucketName, persistentOps, persistentNotifyUrl, persistentPipeline);
    }

    /**
     * 获取七牛云存储<b>私有空间</b>下载授权凭证（downloadToken）。
     * <p/>
     * 有效时长为1小时即3600秒。
     * <p/>
     * 私有资源必须通过临时下载授权凭证(downloadToken)下载。
     *
     * @param fileName 文件名
     *
     * @return 私有空间下载授权凭证（downloadToken）
     *
     * @since 1.0.0
     */
    public String getPrivateDownloadToken(String fileName) {
        return this.getPrivateDownloadToken(fileName, null);
    }

    /**
     * 获取七牛云存储<b>私有空间</b>下载授权凭证（downloadToken）。
     * <p/>
     * 私有资源必须通过临时下载授权凭证(downloadToken)下载。
     *
     * @param fileName 文件名
     * @param expires  有效时长。单位：秒（s），默认1小时即3600秒。
     *
     * @return 私有空间下载授权凭证（downloadToken）
     *
     * @since 1.0.0
     */
    public String getPrivateDownloadToken(String fileName, String expires) {
        long expiresTime = 3600L; // 有效时长，单位秒。默认3600s
        if (StringUtils.isNotEmpty(expires) && StringUtils.isNumeric(expires)) {
            expiresTime = Long.parseUnsignedLong(expires);
        }

        StringBuilder url = new StringBuilder(this.privateBucketDomain);
        url.append("/");
        url.append(fileName);

        // String url2 = "http://abd.resdet.com/dfe/hg.jpg?imageView2/1/w/100";

        // urlSigned
        String downloadUrl = this.auth.privateDownloadUrl(url.toString(), expiresTime);

        return downloadUrl;
    }

    // ================================================================================================================== //
    // ================================================================================================================== //

    /**
     * 获取七牛云存储<b>公有空间</b>上传授权凭证（uptoken）。
     * <p/>
     * 有效时长为1小时即3600秒。一个 uptoken 可以被用于多次上传（只要它还没有过期）。
     * <p/>
     * uptoken是一个字符串，作为http协议Header的一部分（Authorization字段）发送到七牛服务端，表示这个http是经过用户授权的。
     *
     * @return 公有空间上传授权凭证（uptoken）
     *
     * @since 1.0.0
     */
    public String getPublicUptoken() {
        return this.getPublicUptoken(StringUtils.EMPTY);
    }

    /**
     * 获取七牛云存储<b>公有空间</b>上传授权凭证（uptoken）。
     * <p/>
     * uptoken是一个字符串，作为http协议Header的一部分（Authorization字段）发送到七牛服务端，表示这个http是经过用户授权的。
     *
     * @param expires 指定 uptoken 有效时长。单位：秒（s），默认1小时即3600秒。一个 uptoken 可以被用于多次上传（只要它还没有过期）。
     *
     * @return 公有空间上传授权凭证（uptoken）
     *
     * @since 1.0.0
     */
    public String getPublicUptoken(String expires) {
        return this.getPublicUptoken(expires, null, null, null);
    }

    /**
     * 获取七牛云存储<b>公有空间</b>上传授权凭证（uptoken）。
     * <p/>
     * uptoken是一个字符串，作为http协议Header的一部分（Authorization字段）发送到七牛服务端，表示这个http是经过用户授权的。
     * <p/>
     * <pre>
     * 七牛上传策略（PutPolicy）文档
     * http://developer.qiniu.com/docs/v6/api/reference/security/put-policy.html
     * </pre>
     *
     * @param expires             有效时长，单位：秒（s）
     * @param persistentOps       资源上传成功后触发执行的预转持久化处理指令列表。每个指令是一个API规格字符串，多个指令用英文分号（;）分隔
     * @param persistentNotifyUrl 接收预转持久化结果通知的URL。必须是公网上可以正常进行POST请求并能响应 HTTP/1.1 200 OK 的有效URL
     * @param persistentPipeline  转码队列名，资源上传成功后，触发转码时指定独立的队列进行转码。为空则表示使用公用队列，处理速度比较慢。建议使用专用队列
     *
     * @return 公有空间上传授权凭证（uptoken）
     *
     * @since 1.0.0
     */
    public String getPublicUptoken(String expires, String persistentOps, String persistentNotifyUrl, String persistentPipeline) {
        return this.getUptoken(expires, this.publicBucketName, persistentOps, persistentNotifyUrl, persistentPipeline);
    }

    /**
     * 获取七牛云存储<b>公有空间</b>下载授权凭证（downloadToken）。
     * <p/>
     * 公有资源没有“有效时长”概念。
     * <p/>
     * 公有资源可以通过 [GET] http://domain/key 地址进行访问。
     *
     * @param fileName 文件名
     *
     * @return 公有空间下载授权凭证（downloadToken）
     *
     * @since 1.0.0
     */
    public String getPublicDownloadToken(String fileName) {
        StringBuilder url = new StringBuilder(this.publicBucketDomain);
        url.append("/");
        url.append(fileName);

        return url.toString();
    }

    // ================================================================================================================== //
    // ================================================================================================================== //

    /**
     * 重命名七牛云存储<b>私有空间</b>文件。
     *
     * @param oldKey 旧文件名
     * @param newKey 新文件名
     *
     * @return 重命名成功与否
     *
     * @since 1.0.0
     */
    public boolean renamePrivateFile(String oldKey, String newKey) {
        return this.renameFile(this.privateBucketName, oldKey, newKey);
    }

    /**
     * 重命名七牛云存储<b>公有空间</b>文件。
     *
     * @param oldKey 旧文件名
     * @param newKey 新文件名
     *
     * @return 重命名成功与否
     *
     * @since 1.0.0
     */
    public boolean renamePublicFile(String oldKey, String newKey) {
        return this.renameFile(this.publicBucketName, oldKey, newKey);
    }

    // ================================================================================================================== //
    // ================================================================================================================== //

    /**
     * 删除七牛云存储<b>私有空间</b>指定文件名的文件。
     *
     * @param key 文件名
     *
     * @return 删除成功与否
     *
     * @since 1.0.0
     */
    public boolean deletePrivateFile(String key) {
        return this.deleteFile(this.privateBucketName, key);
    }

    /**
     * 删除七牛云存储<b>私有空间</b>指定文件名列表里的文件。
     *
     * @param keys 文件名列表
     *
     * @return 删除成功与否
     *
     * @since 1.0.0
     */
    public boolean deletePrivateFiles(List<String> keys) {
        return this.deleteFiles(this.privateBucketName, keys);
    }

    /**
     * 删除七牛云存储<b>公有空间</b>指定文件名的文件。
     *
     * @param key 文件名
     *
     * @return 删除成功与否
     *
     * @since 1.0.0
     */
    public boolean deletePublicFile(String key) {
        return this.deleteFile(this.publicBucketName, key);
    }

    /**
     * 删除七牛云存储<b>公有空间</b>指定文件名列表里的文件。
     *
     * @param keys 文件名列表
     *
     * @return 删除成功与否
     *
     * @since 1.0.0
     */
    public boolean deletePublicFiles(List<String> keys) {
        return this.deleteFiles(this.publicBucketName, keys);
    }

    // ================================================================================================================== //
    // ================================================================================================================== //

    /*
     * 获取七牛云存储<b>指定空间</b>的上传授权凭证（uptoken）。 <p> uptoken是一个字符串，作为http协议Header的一部分（Authorization字段）发送到七牛服务端，表示这个http是经过用户授权的。
     * 
     * @param expires 指定 uptoken 有效时长。单位：秒（s），默认1小时即3600秒。一个 uptoken 可以被用于多次上传（只要它还没有过期）。
     * 
     * @param bucketName 空间名称
     * 
     * @param persistentOps 资源上传成功后触发执行的预转持久化处理指令列表。每个指令是一个API规格字符串，多个指令用英文分号（;）分隔
     * 
     * @param persistentNotifyUrl 接收预转持久化结果通知的URL。必须是公网上可以正常进行POST请求并能响应 HTTP/1.1 200 OK 的有效URL
     * 
     * @param persistentPipeline 转码队列名，资源上传成功后，触发转码时指定独立的队列进行转码。为空则表示使用公用队列，处理速度比较慢。建议使用专用队列
     * 
     * @return 上传授权凭证（uptoken）
     * 
     * @since 1.0.0
     */
    private String getUptoken(String expires, String bucketName, String persistentOps, String persistentNotifyUrl, String persistentPipeline) {
        long expiresTime = 3600L;
        if (StringUtils.isNotBlank(expires) && StringUtils.isNumeric(expires)) {
            expiresTime = Long.parseUnsignedLong(expires);
        }

        StringMap putPolicy = new StringMap();

        // 上传成功后，自定义七牛云最终返回給上传端（在指定returnUrl时是携带在跳转路径参数中）的数据支持魔法变量和自定义变量。returnBody要求是合法的 JSON文本
        if (StringUtils.isNotBlank(this.returnBody)) {
            putPolicy.put("returnBody", this.returnBody);
        }
        else {
            putPolicy.putNotEmpty("returnBody", DEFAULT_RETURN_BODY);
        }

        // 资源上传成功后触发执行的预转持久化处理指令列表。每个指令是一个API规格字符串，多个指令用英文分号（;）分隔
        if (StringUtils.isNotBlank(persistentOps)) {
            putPolicy.put("persistentOps", persistentOps);

            // 接收预转持久化结果通知的URL。必须是公网上可以正常进行POST请求并能响应HTTP/1.1 200 OK 的有效URL
            if (StringUtils.isNotBlank(persistentNotifyUrl)) {
                putPolicy.put("persistentNotifyUrl", persistentNotifyUrl);
            }

            // 转码队列名，资源上传成功后，触发转码时指定独立的队列进行转码。为空则表示使用公用队列，处理速度比较慢。建议使用专用队列
            if (StringUtils.isNotBlank(persistentPipeline)) {
                putPolicy.put("persistentPipeline", persistentPipeline);
            }
            else {
                if (StringUtils.isNotBlank(this.persistentPipeline)) {
                    putPolicy.put("persistentPipeline", this.persistentPipeline);
                }
            }
        }

        // 请确保bucket已经存在
        String uptoken = this.auth.uploadToken(bucketName, null, expiresTime, putPolicy);

        return uptoken;
    }

    /*
     * 重命名指定空间里的文件。
     * 
     * @param bucket 空间名称
     * 
     * @param oldKey 旧文件名
     * 
     * @param newKey 新文件名
     * 
     * @return 重命名成功与否
     * 
     * @since 1.0.0
     */
    private boolean renameFile(String bucket, String oldKey, String newKey) {
        boolean isSuccess = false;

        try {
            this.bucketManager.rename(bucket, oldKey, newKey);
            isSuccess = true;
        }
        catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return isSuccess;
    }

    /*
     * 删除指定空间里指定文件名的文件。
     * 
     * @param bucket 空间名称
     * 
     * @param key 文件名
     * 
     * @return 删除成功与否
     * 
     * @since 1.0.0
     */
    private boolean deleteFile(String bucket, String key) {
        boolean isSuccess = false;

        try {
            this.bucketManager.delete(bucket, key);
            isSuccess = true;
        }
        catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return isSuccess;
    }

    /*
     * 删除指定空间里指定文件名的文件。
     * 
     * @param bucket 空间名称
     * 
     * @param keys 文件名列表
     * 
     * @return 删除成功与否
     * 
     * @since 1.0.0
     */
    private boolean deleteFiles(String bucket, List<String> keys) {
        BucketManager.Batch ops = new BucketManager.Batch();

        for (String key : keys) {
            ops.delete(bucket, key);
        }

        int i = 1;
        try {
            Response r = this.bucketManager.batch(ops);
            BatchStatus[] bs = r.jsonToObject(BatchStatus[].class);
            for (BatchStatus b : bs) {
                if (b.code == 200) {
                    i++;
                }
            }
        }
        catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        boolean isSuccess = i > 1;

        return isSuccess;
    }

    // ================================================================================================================== //
    // ================================================================================================================== //

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getPublicBucketName() {
        return publicBucketName;
    }

    public void setPublicBucketName(String publicBucketName) {
        this.publicBucketName = publicBucketName;
    }

    public String getPublicBucketHttpsDomain() {
        return publicBucketHttpsDomain;
    }

    public void setPublicBucketHttpsDomain(String publicBucketHttpsDomain) {
        this.publicBucketHttpsDomain = publicBucketHttpsDomain;
    }

    public String getPublicBucketDomain() {
        return publicBucketDomain;
    }

    public void setPublicBucketDomain(String publicBucketDomain) {
        this.publicBucketDomain = publicBucketDomain;
    }

    public String getPrivateBucketName() {
        return privateBucketName;
    }

    public void setPrivateBucketName(String privateBucketName) {
        this.privateBucketName = privateBucketName;
    }

    public String getPrivateBucketHttpsDomain() {
        return privateBucketHttpsDomain;
    }

    public void setPrivateBucketHttpsDomain(String privateBucketHttpsDomain) {
        this.privateBucketHttpsDomain = privateBucketHttpsDomain;
    }

    public String getPrivateBucketDomain() {
        return privateBucketDomain;
    }

    public void setPrivateBucketDomain(String privateBucketDomain) {
        this.privateBucketDomain = privateBucketDomain;
    }

    public String getReturnBody() {
        return returnBody;
    }

    public void setReturnBody(String returnBody) {
        this.returnBody = returnBody;
    }

    public String getPersistentPipeline() {
        return persistentPipeline;
    }

    public void setPersistentPipeline(String persistentPipeline) {
        this.persistentPipeline = persistentPipeline;
    }

    // ================================================================================================================== //
    // ================================================================================================================== //

    public OperationManager getOperationManager() {
        return operationManager;
    }

    public BucketManager getBucketManager() {
        return bucketManager;
    }

    public UploadManager getUploadManager() {
        return uploadManager;
    }

}