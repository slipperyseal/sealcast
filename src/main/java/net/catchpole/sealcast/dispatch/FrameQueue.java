package net.catchpole.sealcast.dispatch;

import net.catchpole.console.TimeTracker;
import net.catchpole.sealcast.aac.ADTSFrame;

import java.util.ArrayList;
import java.util.List;

public class FrameQueue {
    private final TimeTracker timeTracker = new TimeTracker();
    private final List<ADTSFrame> adtsFrames = new ArrayList<ADTSFrame>();
    private final int limit;
    private int length;
    private Listener listener;
    private int framesRead;

    public FrameQueue(int limit, Listener listener) {
        this.limit = limit;
        this.listener = listener;
    }

    public synchronized void addFrame(ADTSFrame adtsFrame) {
        if (this.length < this.limit) {
            this.length += adtsFrame.getData().length;
            this.adtsFrames.add(adtsFrame);
        }
        this.notifyAll();
    }

    public synchronized ADTSFrame getNextFrame() {
        while (adtsFrames.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException ie) {
                return null;
            }
        }
        this.framesRead++;
        ADTSFrame adtsFrame = adtsFrames.remove(0);
        this.length -= adtsFrame.getData().length;
        return adtsFrame;
    }

    public Listener getListener() {
        return listener;
    }

    public synchronized String toString() {
        return this.getClass().getSimpleName() + " " + listener +
                "\tSince " + timeTracker.getStartDate() +
                "\t" + timeTracker.getHoursMinsSeconds() +
                "\t" + this.framesRead + " frames read";
    }
}
