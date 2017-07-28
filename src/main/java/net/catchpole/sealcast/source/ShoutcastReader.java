package net.catchpole.sealcast.source;

import net.catchpole.sealcast.servlet.IcyHeaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShoutcastReader {
    private String url;
    private IcyHeaders icyHeaders;

    public ShoutcastReader(String url) {
        this.url = url;
    }

    public InputStream getInputStream() throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(url).openConnection();
        this.icyHeaders = new IcyHeaders(httpURLConnection);
        return httpURLConnection.getInputStream();
    }

    public IcyHeaders getIcyHeaders() {
        return icyHeaders;
    }
}
