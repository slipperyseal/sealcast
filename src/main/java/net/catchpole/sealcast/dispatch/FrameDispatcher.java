package net.catchpole.sealcast.dispatch;

import net.catchpole.sealcast.aac.ADTSFrame;

import java.io.IOException;

public interface FrameDispatcher {
    void dispatch(ADTSFrame adtsFrame) throws IOException;

    int getListenerCount();
}
