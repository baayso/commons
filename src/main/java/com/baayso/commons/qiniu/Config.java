package com.baayso.commons.qiniu;

/**
 * 七牛云配置。
 *
 * @author ChenFangjie
 * @since 1.0.0
 */
public final class Config {

    // 让工具类彻底不可以实例化
    private Config() {
        throw new Error("工具类不可以实例化！");
    }

    /*--------------------------------------------
    |      七      牛      云      存      储     |
    ============================================*/

    /** 七牛云存储公钥 */
    public static String QINIU_ACCESS_KEY = "Xr6OjHd4cV1G7wpKRDpfLZBJylffo8cn4H4QQg1w";

    /** 七牛云存储私钥 */
    public static String QINIU_SECRET_KEY = "Aehblb98gk5TTJ4ns8GRAwJHC3UPi99gDUuQnUih";

    /** 七牛云存储<b>公有空间</b>名称 */
    public static String QINIU_PUBLIC_BUCKET_NAME = "xiaoyao-public";

    /** 七牛云存储<b>公有空间</b>HTTPS域名 */
    public static String QINIU_PUBLIC_BUCKET_HTTPS_DOMAIN = "https://dn-baayso-xiaoyao-public.qbox.me";

    /** 七牛云存储<b>公有空间</b>推荐域名 */
    public static String QINIU_PUBLIC_BUCKET_DOMAIN = "http://7xiqdo.com1.z0.glb.clouddn.com";

    /** 七牛云存储<b>私有空间</b>名称 */
    public static String QINIU_PRIVATE_BUCKET_NAME = "xiaoyao-private";

    /** 七牛云存储<b>私有空间</b>HTTPS域名 */
    public static String QINIU_PRIVATE_BUCKET_HTTPS_DOMAIN = "https://dn-baayso-xiaoyao-private.qbox.me";

    /** 七牛云存储<b>私有空间</b>推荐域名 */
    public static String QINIU_PRIVATE_BUCKET_DOMAIN = "http://7xiqdp.com1.z0.glb.clouddn.com";

}
