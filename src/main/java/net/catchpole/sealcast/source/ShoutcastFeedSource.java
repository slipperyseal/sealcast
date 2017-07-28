package net.catchpole.sealcast.source;

import net.catchpole.sealcast.servlet.IcyHeaders;

import java.io.DataInputStream;
import java.io.IOException;

public class ShoutcastFeedSource implements FeedSource {
    private final String url;
    private IcyHeaders icyHeaders;

    public ShoutcastFeedSource(String url) {
        this.url = url;
    }

    public DataInputStream getDataInputStream() throws IOException {
        ShoutcastReader shoutcastReader = new ShoutcastReader(url);
        DataInputStream dataInputStream = new DataInputStream(shoutcastReader.getInputStream());
        this.icyHeaders = shoutcastReader.getIcyHeaders();
        return dataInputStream;
    }

    public IcyHeaders getHeaders() {
        return this.icyHeaders;
    }
}
