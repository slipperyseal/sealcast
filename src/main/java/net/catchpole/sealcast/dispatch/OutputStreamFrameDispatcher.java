package net.catchpole.sealcast.dispatch;

import net.catchpole.sealcast.aac.ADTSFrame;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamFrameDispatcher implements FrameDispatcher {
    private OutputStream outputStream;

    public OutputStreamFrameDispatcher(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void dispatch(ADTSFrame adtsFrame) throws IOException {
        outputStream.write(adtsFrame.getData());
    }

    public int getListenerCount() {
        return 1;
    }
}
