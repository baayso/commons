package com.baayso.commons.qiniu;

import java.util.List;

import com.baayso.commons.qiniu.form.GetUptokenForm;

/**
 * 七牛云存储业务逻辑接口。
 *
 * @author ChenFangjie (2014/12/16 16:25:22)
 * @since 1.0.0
 */
public interface IQiniuService {

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
    public String getPrivateUptoken();

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
    public String getPrivateUptoken(String expires);

    /**
     * 获取七牛云存储<b>私有空间</b>上传授权凭证（uptoken）。
     * <p/>
     * uptoken是一个字符串，作为http协议Header的一部分（Authorization字段）发送到七牛服务端，表示这个http是经过用户授权的。
     *
     * @param form 获取七牛上传授权凭证时客户端提交的数据
     *
     * @return 私有空间上传授权凭证（uptoken）
     *
     * @since 1.0.0
     */
    public String getPrivateUptoken(GetUptokenForm form);

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
    public String getPrivateDownloadToken(String fileName);

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
    public String getPrivateDownloadToken(String fileName, String expires);

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
    public String getPublicUptoken();

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
    public String getPublicUptoken(String expires);

    /**
     * 获取七牛云存储<b>公有空间</b>上传授权凭证（uptoken）。
     * <p/>
     * uptoken是一个字符串，作为http协议Header的一部分（Authorization字段）发送到七牛服务端，表示这个http是经过用户授权的。
     *
     * @param form 获取七牛上传授权凭证时客户端提交的数据
     *
     * @return 公有空间上传授权凭证（uptoken）
     *
     * @since 1.0.0
     */
    public String getPublicUptoken(GetUptokenForm form);

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
    public String getPublicDownloadToken(String fileName);

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
    public boolean renamePrivateFile(String oldKey, String newKey);

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
    public boolean renamePublicFile(String oldKey, String newKey);

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
    public boolean deletePrivateFile(String key);

    /**
     * 删除七牛云存储<b>私有空间</b>指定文件名列表里的文件。
     *
     * @param keys 文件名列表
     *
     * @return 删除成功与否
     *
     * @since 1.0.0
     */
    public boolean deletePrivateFiles(List<String> keys);

    /**
     * 删除七牛云存储<b>公有空间</b>指定文件名的文件。
     *
     * @param key 文件名
     *
     * @return 删除成功与否
     *
     * @since 1.0.0
     */
    public boolean deletePublicFile(String key);

    /**
     * 删除七牛云存储<b>公有空间</b>指定文件名列表里的文件。
     *
     * @param keys 文件名列表
     *
     * @return 删除成功与否
     *
     * @since 1.0.0
     */
    public boolean deletePublicFiles(List<String> keys);

}
