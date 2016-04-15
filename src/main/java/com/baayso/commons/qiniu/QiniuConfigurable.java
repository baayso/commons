package com.baayso.commons.qiniu;

/**
 * 七牛云存储配置。
 *
 * @author ChenFangjie (2016/4/14 19:39)
 * @since 1.0.0
 */
public interface QiniuConfigurable {

    /** 访问公钥 */
    String accessKey();

    /** 访问私钥 */
    String secretKey();

    /** <b>公有空间</b>名称 */
    String publicBucketName();

    /** <b>公有空间</b>推荐域名 */
    String publicBucketDomain();

    /** <b>公有空间</b>HTTPS域名 */
    String publicBucketHttpsDomain();

    /** <b>私有空间</b>名称 */
    String privateBucketName();

    /** <b>私有空间</b>推荐域名 */
    String privateBucketDomain();

    /** <b>私有空间</b>HTTPS域名 */
    String privateBucketHttpsDomain();

}
