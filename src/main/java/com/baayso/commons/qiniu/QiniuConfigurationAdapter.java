package com.baayso.commons.qiniu;

/**
 * Abstract adapter class for the QiniuConfigurable interface.
 *
 * @author ChenFangjie (2016/4/14 19:54)
 * @since 1.0.0
 */
public abstract class QiniuConfigurationAdapter implements QiniuConfigurable {

    @Override
    public String accessKey() {
        return "";
    }

    @Override
    public String secretKey() {
        return "";
    }

    @Override
    public String publicBucketName() {
        return "";
    }

    @Override
    public String publicBucketDomain() {
        return "";
    }

    @Override
    public String publicBucketHttpsDomain() {
        return "";
    }

    @Override
    public String privateBucketName() {
        return "";
    }

    @Override
    public String privateBucketDomain() {
        return "";
    }

    @Override
    public String privateBucketHttpsDomain() {
        return "";
    }

}
