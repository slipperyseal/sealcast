package net.catchpole.sealcast.servlet;

import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;

public class IcyHeaders {
    private static final String ICY_NOTICE1_NAME = "icy-notice1";
    private static final String ICY_GENRE_NAME = "icy-genre";
    private static final String ICY_BR_NAME = "icy-br";
    private static final String ICY_PUB_NAME = "icy-pub";
    private static final String ICY_NAME_NAME = "icy-name";
    private static final String ICY_URL_NAME = "icy-url";
    private static final String CONTENT_TYPE_NAME = "Content-Type";

    private String icyNotice1;
    private String icyGenre;
    private String icyBr;
    private String icyPub;
    private String icyName;
    private String icyUrl;
    private String contentType;

    public IcyHeaders() {
    }

    public IcyHeaders(HttpURLConnection httpURLConnection) {
        this.icyNotice1 = httpURLConnection.getHeaderField(ICY_NOTICE1_NAME);
        this.icyGenre = httpURLConnection.getHeaderField(ICY_GENRE_NAME);
        this.icyBr = httpURLConnection.getHeaderField(ICY_BR_NAME);
        this.icyPub = httpURLConnection.getHeaderField(ICY_PUB_NAME);
        this.icyUrl = httpURLConnection.getHeaderField(ICY_URL_NAME);
        this.contentType = httpURLConnection.getHeaderField(CONTENT_TYPE_NAME);
    }

    public void write(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader(ICY_NOTICE1_NAME, this.icyNotice1);
        httpServletResponse.setHeader(ICY_GENRE_NAME, this.icyGenre);
        httpServletResponse.setHeader(ICY_BR_NAME, this.icyBr);
        httpServletResponse.setHeader(ICY_NAME_NAME, this.icyName);
        httpServletResponse.setHeader(ICY_URL_NAME, this.icyUrl);
        httpServletResponse.setHeader(CONTENT_TYPE_NAME, this.contentType);
    }

    public String getIcyNotice1() {
        return icyNotice1;
    }

    public void setIcyNotice1(String icyNotice1) {
        this.icyNotice1 = icyNotice1;
    }

    public String getIcyGenre() {
        return icyGenre;
    }

    public void setIcyGenre(String icyGenre) {
        this.icyGenre = icyGenre;
    }

    public String getIcyBr() {
        return icyBr;
    }

    public void setIcyBr(String icyBr) {
        this.icyBr = icyBr;
    }

    public String getIcyPub() {
        return icyPub;
    }

    public void setIcyPub(String icyPub) {
        this.icyPub = icyPub;
    }

    public String getIcyName() {
        return icyName;
    }

    public void setIcyName(String icyName) {
        this.icyName = icyName;
    }

    public String getIcyUrl() {
        return icyUrl;
    }

    public void setIcyUrl(String icyUrl) {
        this.icyUrl = icyUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "IcyHeaders{" +
                "icyNotice1='" + icyNotice1 + '\'' +
                ", icyGenre='" + icyGenre + '\'' +
                ", icyBr='" + icyBr + '\'' +
                ", icyPub='" + icyPub + '\'' +
                ", icyName='" + icyName + '\'' +
                ", icyUrl='" + icyUrl + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
