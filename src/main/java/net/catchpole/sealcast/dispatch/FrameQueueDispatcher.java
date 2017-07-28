package net.catchpole.sealcast.dispatch;

import net.catchpole.sealcast.aac.ADTSFrame;
import net.catchpole.sealcast.servlet.SealcastPlaybackServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FrameQueueDispatcher implements FrameDispatcher {
    private final List<FrameQueue> frameQueueList = new ArrayList<FrameQueue>();
    private final List<ADTSFrame> primingFrames = new ArrayList<ADTSFrame>();
    private final int primingLimit;
    private int primingSize;

    public FrameQueueDispatcher(int primingLimit) {
        this.primingLimit = primingLimit;
    }

    public synchronized void addFrameQueue(FrameQueue frameQueue) {
        this.frameQueueList.add(frameQueue);
        for (ADTSFrame adtsFrame : this.primingFrames) {
            frameQueue.addFrame(adtsFrame);
        }
        this.notifyAll();
    }

    public synchronized void removeFrameQueue(FrameQueue frameQueue) {
        this.frameQueueList.remove(frameQueue);
    }

    public synchronized void dispatch(ADTSFrame adtsFrame) throws IOException {
        primingFrames.add(adtsFrame);
        primingSize += adtsFrame.getData().length;

        while (primingSize > primingLimit) {
            primingSize -= primingFrames.remove(0).getData().length;
        }

        for (FrameQueue frameQueue : this.frameQueueList) {
            frameQueue.addFrame(adtsFrame);
        }
    }

    public synchronized List<Listener> getListeners() {
        List<Listener> listeners = new ArrayList<Listener>();
        for (FrameQueue frameQueue : frameQueueList) {
            listeners.add(frameQueue.getListener());
        }
        synchronized (SealcastPlaybackServlet.listeners) {
            listeners.addAll(SealcastPlaybackServlet.listeners);
        }
        return listeners;
    }

    public synchronized int getListenerCount() {
        return frameQueueList.size();
    }
}
