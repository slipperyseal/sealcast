package net.catchpole.sealcast.source;

import net.catchpole.sealcast.servlet.IcyHeaders;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SocketFeedSource implements FeedSource {
    private ServerSocket serverSocket;

    public SocketFeedSource(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public DataInputStream getDataInputStream() throws IOException {
        Socket socket = serverSocket.accept();
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        System.out.println(socket + " " + dataInputStream);
        return dataInputStream;
    }

    public IcyHeaders getHeaders() {
        IcyHeaders icyHeaders = new IcyHeaders();
        icyHeaders.setContentType("audio/aac");
        icyHeaders.setIcyBr("96");
        icyHeaders.setIcyNotice1("Sealcast Station time " + new Date());
        icyHeaders.setIcyName("Sealcast");
        return icyHeaders;
    }
}
