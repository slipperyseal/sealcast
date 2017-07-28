package net.catchpole.sealcast.source;

import net.catchpole.sealcast.servlet.IcyHeaders;

import java.io.DataInputStream;
import java.io.IOException;

public interface FeedSource {
    DataInputStream getDataInputStream() throws IOException;

    IcyHeaders getHeaders();
}
