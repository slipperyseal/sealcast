package net.catchpole.sealcast;

import java.util.Properties;

public class SealcastConfig {
    private String baseUrl;
    private String port;
    private String streamPort;

    private String name;
    private String notice;
    private String genre;
    private String bitrate;

    public SealcastConfig() {
    }

    public SealcastConfig(Properties properties) {
        this.baseUrl = properties.getProperty("baseUrl");
        this.port = properties.getProperty("port");
        this.streamPort = properties.getProperty("streamPort");
        this.name = properties.getProperty("name");
        this.notice = properties.getProperty("notice");
        this.genre = properties.getProperty("genre");
        this.bitrate = properties.getProperty("bitrate");
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getStreamPort() {
        return streamPort;
    }

    public void setStreamPort(String streamPort) {
        this.streamPort = streamPort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    @Override
    public String toString() {
        return "SealcastConfig{" +
                "baseUrl='" + baseUrl + '\'' +
                ", port='" + port + '\'' +
                ", streamPort='" + streamPort + '\'' +
                ", name='" + name + '\'' +
                ", notice='" + notice + '\'' +
                ", genre='" + genre + '\'' +
                ", bitrate='" + bitrate + '\'' +
                '}';
    }
}
