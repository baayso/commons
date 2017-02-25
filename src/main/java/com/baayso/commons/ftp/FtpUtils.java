package com.baayso.commons.ftp;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;

import com.baayso.commons.log.Log;

/**
 * FTP工具类。<br>
 * 源自：http://xianfengmc.blog.163.com/blog/static/82690025201161731615607/
 *
 * @author ChenFangjie (2016/5/31 16:32)
 * @since 1.0.0
 */
public class FtpUtils implements Closeable {

    private static final Logger log = Log.get();

    private FTPClient ftpClient;

    /**
     * 根路径为"/",如果需要链接服务器之后跳转到路径，则在path中定义
     *
     * @param config
     *
     * @throws IOException
     */
    public boolean connect(FtpConfig config) throws IOException {
        String server = config.getServer();
        int port = config.getPort();
        String user = config.getUsername();
        String password = config.getPassword();
        String path = config.getPath();

        return connect(server, port, user, password, path);

    }

    /**
     * 连接FTP服务器
     *
     * @param server   服务器ip
     * @param port     端口，通常为21
     * @param user     用户名
     * @param password 密码
     * @param path     进入服务器之后的默认路径
     *
     * @return 连接成功返回true，否则返回false
     *
     * @throws IOException
     * @since 1.0.0
     */
    public boolean connect(String server, int port, String user, String password, String path) throws IOException {

        this.ftpClient = new FTPClient();
        this.ftpClient.connect(server, port);
        this.ftpClient.setControlEncoding("GBK");

        log.info("Connected to " + server + ".");
        log.info("FTP server reply code:" + this.ftpClient.getReplyCode());

        if (FTPReply.isPositiveCompletion(this.ftpClient.getReplyCode())) {
            if (this.ftpClient.login(user, password)) {

                // Path is the sub-path of the FTP path
                if (path != null && path.length() != 0) {
                    this.ftpClient.changeWorkingDirectory(path); // 改变工作路径
                }

                return true;
            }
        }

        this.close();

        return false;
    }

    /**
     * 断开与远程服务器的连接
     *
     * @throws IOException
     * @since 1.0.0
     */
    @Deprecated
    public void disconnect() throws IOException {
        this.close();
    }

    /**
     * 断开与远程服务器的连接
     *
     * @throws IOException
     * @since 1.0.0
     */
    @Override
    public void close() throws IOException {
        if (this.ftpClient.isConnected()) {
            this.ftpClient.disconnect();
        }
    }

    /**
     * 从FTP服务器上下载文件,支持断点续传，下载百分比汇报
     *
     * @param remote 远程文件路径及名称
     * @param local  本地文件完整绝对路径
     *
     * @return 下载的状态
     *
     * @throws IOException
     * @since 1.0.0
     */
    public DownloadStatus download(String remote, String local) throws IOException {
        // 设置被动模式
        this.ftpClient.enterLocalPassiveMode();

        // 设置以二进制方式传输
        this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        DownloadStatus result;

        // 检查远程文件是否存在
        FTPFile[] files = this.ftpClient.listFiles(new String(remote.getBytes("GBK"), "iso-8859-1"));

        if (files.length != 1) {
            log.info("远程文件不存在");
            return DownloadStatus.REMOTE_FILE_NOT_EXIST;

        }

        long lRemoteSize = files[0].getSize();

        File f = new File(local);

        // 本地存在文件，进行断点下载
        if (f.exists()) {
            long localSize = f.length();
            // 判断本地文件大小是否大于远程文件大小
            if (localSize >= lRemoteSize) {
                log.info("本地文件大于远程文件，下载中止");
                return DownloadStatus.LOCAL_FILE_BIGGER_THAN_REMOTE_FILE;
            }

            // 进行断点续传，并记录状态
            FileOutputStream out = new FileOutputStream(f, true);
            this.ftpClient.setRestartOffset(localSize);
            InputStream in = this.ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"), "iso-8859-1"));

            byte[] bytes = new byte[1024];

            long step = lRemoteSize / 100;
            step = step == 0 ? 1 : step;// 文件过小，step可能为0
            long process = localSize / step;

            int c;

            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
                localSize += c;
                long nowProcess = localSize / step;

                if (nowProcess > process) {
                    process = nowProcess;
                    if (process % 10 == 0) {
                        log.info("下载进度：" + process);
                    }
                }
            }

            in.close();
            out.close();

            boolean isDo = this.ftpClient.completePendingCommand();

            if (isDo) {
                result = DownloadStatus.DOWNLOAD_FROM_BREAK_SUCCESS;
            }
            else {
                result = DownloadStatus.DOWNLOAD_FROM_BREAK_FAILED;
            }

        }
        else {
            OutputStream out = new FileOutputStream(f);
            InputStream in = this.ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"), "iso-8859-1"));

            byte[] bytes = new byte[1024];
            long step = lRemoteSize / 100;
            step = step == 0 ? 1 : step;// 文件过小，step可能为0
            long process = 0;
            long localSize = 0L;

            int c;

            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
                localSize += c;
                long nowProcess = localSize / step;

                if (nowProcess > process) {
                    process = nowProcess;
                    if (process % 10 == 0) {
                        log.info("下载进度：" + process);
                    }
                }
            }

            in.close();
            out.close();

            boolean upNewStatus = this.ftpClient.completePendingCommand();

            if (upNewStatus) {
                result = DownloadStatus.DOWNLOAD_NEW_SUCCESS;
            }
            else {
                result = DownloadStatus.DOWNLOAD_NEW_FAILED;
            }
        }

        return result;
    }

    public boolean changeDirectory(String path) throws IOException {
        return this.ftpClient.changeWorkingDirectory(path);

    }

    public boolean createDirectory(String pathName) throws IOException {
        return this.ftpClient.makeDirectory(pathName);

    }

    public boolean removeDirectory(String path) throws IOException {
        return this.ftpClient.removeDirectory(path);

    }

    public boolean removeDirectory(String path, boolean isAll) throws IOException {

        if (!isAll) {
            return removeDirectory(path);
        }

        FTPFile[] ftpFileArr = this.ftpClient.listFiles(path);

        if (ftpFileArr == null || ftpFileArr.length == 0) {
            return removeDirectory(path);
        }

        //

        for (FTPFile ftpFile : ftpFileArr) {
            String name = ftpFile.getName();

            if (ftpFile.isDirectory()) {
                log.info("* [sD]Delete subPath [" + path + "/" + name + "]");

                if (!ftpFile.getName().equals(".") && (!ftpFile.getName().equals(".."))) {
                    removeDirectory(path + "/" + name, true);
                }

            }
            else if (ftpFile.isFile()) {
                log.info("* [sF]Delete file [" + path + "/" + name + "]");

                deleteFile(path + "/" + name);
            }
            else if (ftpFile.isSymbolicLink()) {
            }
            else if (ftpFile.isUnknown()) {
            }

        }

        return this.ftpClient.removeDirectory(path);
    }

    /**
     * 查看目录是否存在
     *
     * @param path
     *
     * @return
     *
     * @throws IOException
     * @since 1.0.0
     */
    public boolean isDirectoryExists(String path) throws IOException {
        boolean flag = false;

        FTPFile[] ftpFileArr = this.ftpClient.listFiles(path);

        for (FTPFile ftpFile : ftpFileArr) {
            if (ftpFile.isDirectory() && ftpFile.getName().equalsIgnoreCase(path)) {
                flag = true;

                break;
            }
        }

        return flag;
    }

    /**
     * 得到某个目录下的文件名列表
     *
     * @param path
     *
     * @return
     *
     * @throws IOException
     * @since 1.0.0
     */
    public List<String> getFileList(String path) throws IOException {

        // listFiles return contains directory and file, it's FTPFile instance
        // listNames() contains directory, so using following to filer directory.

        // String[] fileNameArr = this.ftpClient.listNames(path);

        FTPFile[] ftpFiles = this.ftpClient.listFiles(path);

        List<String> retList = new ArrayList<>();

        if (ftpFiles == null || ftpFiles.length == 0) {
            return retList;
        }

        for (FTPFile ftpFile : ftpFiles) {
            if (ftpFile.isFile()) {
                retList.add(ftpFile.getName());
            }
        }

        return retList;
    }

    public boolean deleteFile(String pathName) throws IOException {
        return this.ftpClient.deleteFile(pathName);
    }

    /**
     * 上传文件到FTP服务器，不支持断点续传
     *
     * @param stream 文件流
     * @param remote 远程文件路径，按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
     *
     * @return 上传结果
     *
     * @throws IOException
     * @since 1.0.0
     */
    public UploadStatus upload(InputStream stream, String remote) throws IOException {
        // 设置PassiveMode传输
        this.ftpClient.enterLocalPassiveMode();

        // 设置以二进制流的方式传输
        this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        this.ftpClient.setControlEncoding("GBK");

        UploadStatus result = null;

        // 对远程目录的处理
        String remoteFileName = remote;

        if (remote.contains("/")) {
            remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
            // 创建服务器远程目录结构，创建失败直接返回
            if (this.createDirectory(remote, this.ftpClient) == UploadStatus.CreateDirectoryFailed) {
                return UploadStatus.CreateDirectoryFailed;
            }
        }

        BufferedInputStream bufferedInputStream = new BufferedInputStream(stream);

        if (this.ftpClient.storeFile(remoteFileName, bufferedInputStream)) {
            result = UploadStatus.UploadNewFileSuccess;
        }

        return result;
    }

    /**
     * 上传文件到FTP服务器，支持断点续传
     *
     * @param local  本地文件名称，绝对路径
     * @param remote 远程文件路径，按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
     *
     * @return 上传结果
     *
     * @throws IOException
     * @since 1.0.0
     */
    public UploadStatus upload(String local, String remote) throws IOException {
        // 设置PassiveMode传输
        this.ftpClient.enterLocalPassiveMode();

        // 设置以二进制流的方式传输
        this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        this.ftpClient.setControlEncoding("GBK");

        UploadStatus result;

        // 对远程目录的处理
        String remoteFileName = remote;

        if (remote.contains("/")) {
            remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
            // 创建服务器远程目录结构，创建失败直接返回
            if (createDirectory(remote, this.ftpClient) == UploadStatus.CreateDirectoryFailed) {
                return UploadStatus.CreateDirectoryFailed;
            }
        }

        // 检查远程是否存在文件
        FTPFile[] files = this.ftpClient.listFiles(new String(remoteFileName.getBytes("GBK"), "iso-8859-1"));

        if (files.length == 1) {
            long remoteSize = files[0].getSize();
            File f = new File(local);
            long localSize = f.length();
            if (remoteSize == localSize) { // 文件存在
                return UploadStatus.FileExits;
            }
            else if (remoteSize > localSize) {
                return UploadStatus.RemoteFileBiggerThanLocalFile;
            }

            // 尝试移动文件内读取指针,实现断点续传
            result = uploadFile(remoteFileName, f, this.ftpClient, remoteSize);

            // 如果断点续传没有成功，则删除服务器上文件，重新上传
            if (result == UploadStatus.UploadFromBreakFailed) {
                if (!this.ftpClient.deleteFile(remoteFileName)) {
                    return UploadStatus.DeleteRemoteFailed;
                }

                result = uploadFile(remoteFileName, f, this.ftpClient, 0);
            }
        }
        else {
            result = uploadFile(remoteFileName, new File(local), this.ftpClient, 0);
        }

        return result;
    }

    /**
     * 递归创建远程服务器目录
     *
     * @param remote    远程服务器文件绝对路径
     * @param ftpClient FTPClient对象
     *
     * @return 目录创建是否成功
     *
     * @throws IOException
     */

    public UploadStatus createDirectory(String remote, FTPClient ftpClient) throws IOException {
        UploadStatus status = UploadStatus.CreateDirectorySuccess;

        String directory = remote.substring(0, remote.lastIndexOf("/") + 1);

        if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(new String(directory.getBytes("GBK"), "iso-8859-1"))) {
            // 如果远程目录不存在，则递归创建远程服务器目录
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            }
            else {
                start = 0;
            }

            end = directory.indexOf("/", start);

            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");

                if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                    if (ftpClient.makeDirectory(subDirectory)) {
                        ftpClient.changeWorkingDirectory(subDirectory);
                    }
                    else {
                        log.info("创建目录失败");

                        return UploadStatus.CreateDirectoryFailed;
                    }
                }

                start = end + 1;

                end = directory.indexOf("/", start);

                // 检查所有目录是否创建完毕

                if (end <= start) {
                    break;
                }
            }
        }

        return status;
    }

    /**
     * 上传文件到服务器,新上传和断点续传
     *
     * @param remoteFile 远程文件名，在上传之前已经将服务器工作目录做了改变
     * @param localFile  本地文件File句柄，绝对路径
     * @param ftpClient  FTPClient
     * @param remoteSize 远程文件大小
     *
     * @return
     *
     * @throws IOException
     * @since 1.0.0
     */
    public UploadStatus uploadFile(String remoteFile, File localFile, FTPClient ftpClient, long remoteSize) throws IOException {
        UploadStatus status;

        // 显示进度的上传
        System.out.println("localFile.length():" + localFile.length());

        long step = localFile.length() / 100;
        step = step == 0 ? 1 : step;// 文件过小，step可能为0
        long process = 0;
        long localReadBytes = 0L;

        RandomAccessFile raf = new RandomAccessFile(localFile, "r");
        OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes("GBK"), "iso-8859-1"));

        // 断点续传
        if (remoteSize > 0) {
            ftpClient.setRestartOffset(remoteSize);
            process = remoteSize / step;
            raf.seek(remoteSize);
            localReadBytes = remoteSize;
        }

        byte[] bytes = new byte[1024];

        int c;

        while ((c = raf.read(bytes)) != -1) {
            out.write(bytes, 0, c);
            localReadBytes += c;
            if (localReadBytes / step != process) {
                process = localReadBytes / step;
                if (process % 10 == 0) {
                    log.info("上传进度：" + process);
                }
            }
        }

        out.flush();
        raf.close();
        out.close();

        boolean result = ftpClient.completePendingCommand();

        if (remoteSize > 0) {
            status = result ? UploadStatus.UploadFromBreakSuccess : UploadStatus.UploadFromBreakFailed;
        }
        else {
            status = result ? UploadStatus.UploadNewFileSuccess : UploadStatus.UploadNewFileFailed;
        }

        return status;
    }

    public InputStream downFile(String sourceFileName) throws IOException {
        return this.ftpClient.retrieveFileStream(sourceFileName);
    }

}
