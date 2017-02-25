package com.baayso.commons.ftp;

/**
 * 下载状态。
 *
 * @author ChenFangjie (2016/5/31 16:27)
 * @since 1.0.0
 */
public enum DownloadStatus {

    /** 远程文件不存在 */
    REMOTE_FILE_NOT_EXIST,

    /** 下载文件成功 */
    DOWNLOAD_NEW_SUCCESS,

    /** 下载文件失败 */
    DOWNLOAD_NEW_FAILED,

    /** 本地文件大于远程文件 */
    LOCAL_FILE_BIGGER_THAN_REMOTE_FILE,

    /** 断点续传成功 */
    DOWNLOAD_FROM_BREAK_SUCCESS,

    /** 断点续传失败 */
    DOWNLOAD_FROM_BREAK_FAILED

}
