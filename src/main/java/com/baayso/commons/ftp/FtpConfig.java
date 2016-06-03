package com.baayso.commons.ftp;

/**
 * FTP配置。
 *
 * @author ChenFangjie (2016/5/31 16:24)
 * @since 1.0.0
 */
public class FtpConfig {

    private String server;
    private int    port;
    private String username;
    private String password;
    private String path;


    public FtpConfig() {
    }

    public FtpConfig(String server, int port, String username, String password, String path) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
        this.path = path;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
