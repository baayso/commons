package com.baayso.commons.ftp;

/**
 * 下载状态。
 *
 * @author ChenFangjie (2016/5/31 16:27)
 * @since 1.0.0
 */
public enum DownloadStatus {

    /** 远程文件不存在 */
    RemoteFileNotExist,

    /** 下载文件成功 */
    DownloadNewSuccess,

    /** 下载文件失败 */
    DownloadNewFailed,

    /** 本地文件大于远程文件 */
    LocalFileBiggerThanRemoteFile,

    /** 断点续传成功 */
    DownloadFromBreakSuccess,

    /** 断点续传失败 */
    DownloadFromBreakFailed

}
