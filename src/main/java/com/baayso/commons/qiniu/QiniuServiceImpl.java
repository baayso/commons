package com.baayso.commons.qiniu;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.baayso.commons.log.Log;
import com.baayso.commons.qiniu.form.GetUptokenForm;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 七牛云存储业务逻辑实现。
 *
 * @author ChenFangjie (2014/12/16 16:30:41)
 * @since 1.0.0
 */
public class QiniuServiceImpl implements IQiniuService {

    private static final Logger log = Log.get();

    private static final String RETURN_BODY = "{\"key\": $(key), \"path\": $(key), \"oname\": $(fname), \"size\": $(fsize), \"mime\":$(mimeType), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height)}";

    private static final String PERSISTENT_PIPELINE = "xiaoyao";

    @Override
    public String getPrivateUptoken() {
        return this.getPrivateUptoken(StringUtils.EMPTY);
    }

    @Override
    public String getPrivateUptoken(String expires) {
        return this.getUptoken(expires, Config.QINIU_PRIVATE_BUCKET_NAME, null, null, null);
    }

    @Override
    public String getPrivateUptoken(GetUptokenForm form) {
        return this.getUptoken(form.getExpires(), Config.QINIU_PRIVATE_BUCKET_NAME, form.getPersistentOps(), form.getPersistentNotifyUrl(), form.getPersistentPipeline());
    }

    @Override
    public String getPrivateDownloadToken(String fileName) {
        return this.getPrivateDownloadToken(fileName, null);
    }

    @Override
    public String getPrivateDownloadToken(String fileName, String expires) {
        long expiresTime = 3600L; // 有效时长，单位秒。默认3600s
        if (StringUtils.isNotEmpty(expires) && StringUtils.isNumeric(expires)) {
            expiresTime = Long.parseUnsignedLong(expires);
        }

        StringBuilder url = new StringBuilder(Config.QINIU_PRIVATE_BUCKET_DOMAIN);
        url.append("/");
        url.append(fileName);

        // String url2 = "http://abd.resdet.com/dfe/hg.jpg?imageView2/1/w/100";

        Auth auth = Auth.create(Config.QINIU_ACCESS_KEY, Config.QINIU_SECRET_KEY);

        // urlSigned
        String downloadUrl = auth.privateDownloadUrl(url.toString(), expiresTime);

        return downloadUrl;
    }

    // ================================================================================================================== //
    // ================================================================================================================== //

    @Override
    public String getPublicUptoken() {
        return this.getPublicUptoken(StringUtils.EMPTY);
    }

    @Override
    public String getPublicUptoken(String expires) {
        return this.getUptoken(expires, Config.QINIU_PUBLIC_BUCKET_NAME, null, null, null);
    }

    @Override
    public String getPublicUptoken(GetUptokenForm form) {
        return this.getUptoken(form.getExpires(), Config.QINIU_PUBLIC_BUCKET_NAME, form.getPersistentOps(), form.getPersistentNotifyUrl(), form.getPersistentPipeline());
    }

    @Override
    public String getPublicDownloadToken(String fileName) {
        StringBuilder url = new StringBuilder(Config.QINIU_PUBLIC_BUCKET_DOMAIN);
        url.append("/");
        url.append(fileName);

        return url.toString();
    }

    // ================================================================================================================== //
    // ================================================================================================================== //

    @Override
    public boolean renamePrivateFile(String oldKey, String newKey) {
        return this.renameFile(Config.QINIU_PRIVATE_BUCKET_NAME, oldKey, newKey);
    }

    @Override
    public boolean renamePublicFile(String oldKey, String newKey) {
        return this.renameFile(Config.QINIU_PUBLIC_BUCKET_NAME, oldKey, newKey);
    }

    // ================================================================================================================== //
    // ================================================================================================================== //

    @Override
    public boolean deletePrivateFile(String key) {
        return this.deleteFile(Config.QINIU_PRIVATE_BUCKET_NAME, key);
    }

    @Override
    public boolean deletePrivateFiles(List<String> keys) {
        return this.deleteFiles(Config.QINIU_PRIVATE_BUCKET_NAME, keys);
    }

    @Override
    public boolean deletePublicFile(String key) {
        return this.deleteFile(Config.QINIU_PUBLIC_BUCKET_NAME, key);
    }

    @Override
    public boolean deletePublicFiles(List<String> keys) {
        return this.deleteFiles(Config.QINIU_PUBLIC_BUCKET_NAME, keys);
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
     * 
     * @version 1.0.0
     */
    private String getUptoken(String expires, String bucketName, String persistentOps, String persistentNotifyUrl, String persistentPipeline) {
        long expiresTime = 3600L;
        if (StringUtils.isNotBlank(expires) && StringUtils.isNumeric(expires)) {
            expiresTime = Long.parseUnsignedLong(expires);
        }

        StringMap putPolicy = new StringMap();

        // 上传成功后，自定义七牛云最终返回給上传端（在指定returnUrl时是携带在跳转路径参数中）的数据支持魔法变量和自定义变量。returnBody要求是合法的 JSON文本
        putPolicy.putNotEmpty("returnBody", RETURN_BODY);

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
                putPolicy.putNotEmpty("persistentPipeline", PERSISTENT_PIPELINE);
            }
        }

        Auth auth = Auth.create(Config.QINIU_ACCESS_KEY, Config.QINIU_SECRET_KEY);

        // 请确保bucket已经存在
        String uptoken = auth.uploadToken(bucketName, null, expiresTime, putPolicy);

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
     * 
     * @version 1.0.0
     */
    private boolean renameFile(String bucket, String oldKey, String newKey) {
        Auth dummyAuth = Auth.create(Config.QINIU_ACCESS_KEY, Config.QINIU_SECRET_KEY);
        BucketManager bucketManager = new BucketManager(dummyAuth);

        boolean isSuccess = false;
        try {
            bucketManager.rename(bucket, oldKey, newKey);
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
     * 
     * @version 1.0.0
     */
    private boolean deleteFile(String bucket, String key) {
        Auth dummyAuth = Auth.create(Config.QINIU_ACCESS_KEY, Config.QINIU_SECRET_KEY);
        BucketManager bucketManager = new BucketManager(dummyAuth);

        boolean isSuccess = false;
        try {
            bucketManager.delete(bucket, key);
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
     * 
     * @version 1.0.0
     */
    private boolean deleteFiles(String bucket, List<String> keys) {
        Auth dummyAuth = Auth.create(Config.QINIU_ACCESS_KEY, Config.QINIU_SECRET_KEY);
        BucketManager bucketManager = new BucketManager(dummyAuth);

        BucketManager.Batch ops = new BucketManager.Batch();
        for (String key : keys) {
            ops.delete(bucket, key);
        }

        int i = 1;
        try {
            Response r = bucketManager.batch(ops);
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

}