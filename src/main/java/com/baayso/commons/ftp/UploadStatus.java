package com.baayso.commons.ftp;

/**
 * 上传状态。
 *
 * @author ChenFangjie (2016/5/31 16:28)
 * @since 1.0.0
 */
public enum UploadStatus {

    /** 远程服务器相应目录创建失败 */
    CreateDirectoryFailed,

    /** 远程服务器创建目录成功 */
    CreateDirectorySuccess,

    /** 上传新文件成功 */
    UploadNewFileSuccess,

    /** 上传新文件失败 */
    UploadNewFileFailed,

    /** 文件已经存在 */
    FileExits,

    /** 远程文件大于本地文件 */
    RemoteFileBiggerThanLocalFile,

    /** 断点续传成功 */
    UploadFromBreakSuccess,

    /** 断点续传失败 */
    UploadFromBreakFailed,

    /** 删除远程文件失败 */
    DeleteRemoteFailed

}
