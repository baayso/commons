package com.baayso.commons.ftp;

/**
 * 上传状态。
 *
 * @author ChenFangjie (2016/5/31 16:28)
 * @since 1.0.0
 */
public enum UploadStatus {

    /** 远程服务器相应目录创建失败 */
    CREATE_DIRECTORY_FAILED,

    /** 远程服务器创建目录成功 */
    CREATE_DIRECTORY_SUCCESS,

    /** 上传新文件成功 */
    UPLOAD_NEW_FILE_SUCCESS,

    /** 上传新文件失败 */
    UPLOAD_NEW_FILE_FAILED,

    /** 文件已经存在 */
    FILE_EXITS,

    /** 远程文件大于本地文件 */
    REMOTE_FILE_BIGGER_THAN_LOCAL_FILE,

    /** 断点续传成功 */
    UPLOAD_FROM_BREAK_SUCCESS,

    /** 断点续传失败 */
    UPLOAD_FROM_BREAK_FAILED,

    /** 删除远程文件失败 */
    DELETE_REMOTE_FAILED

}
