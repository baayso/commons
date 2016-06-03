package com.baayso.commons.utils;

/**
 * 文件相关的工具类。
 *
 * @author ChenFangjie (2016/6/1 14:43)
 * @since 1.0.0
 */
public final class FileUtils {

    // 让工具类彻底不可以实例化
    private FileUtils() {
        throw new Error("工具类不可以实例化！");
    }

    /**
     * 获取文件扩展名，返回如 .png 这种模式的文件扩展名。
     *
     * @param fileName 文件名
     *
     * @return 文件的扩展名，如果没有扩展名将返回零长度字符串
     *
     * @since 1.0.0
     */
    public static String getExtensionName(final String fileName) {
        if (null != fileName && !fileName.isEmpty()) {
            int index = fileName.lastIndexOf('.');

            if (-1 < index && (fileName.length() - 1) > index) {
                return fileName.substring(index);
            }
        }

        return "";
    }

    /**
     * 去掉文件扩展名。
     *
     * @param fileName 文件名
     *
     * @return 去掉扩展名后的文件名
     *
     * @since 1.0.0
     */
    public static String trimExtensionName(final String fileName) {
        if (null != fileName && !fileName.isEmpty()) {
            int index = fileName.lastIndexOf('.');

            if (-1 < index && (fileName.length() - 1) > index) {
                return fileName.substring(0, index);
            }
        }

        return "";
    }

}
