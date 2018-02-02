package net.catchpole.sealcast.recorder;

import net.catchpole.console.TimeTracker;
import net.catchpole.sealcast.aac.ADTSFrame;
import net.catchpole.sealcast.dispatch.FrameDispatcher;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class PlaybackDispatcher {
    private DataInputStream aacInputStream;
    private DataInputStream timingInputStream;

    public PlaybackDispatcher(String name) throws IOException {
        this.aacInputStream = new DataInputStream(new FileInputStream(name + ".aac"));
        this.timingInputStream = new DataInputStream(new FileInputStream(name + ".time"));
    }

    public void dispatch(FrameDispatcher frameDispatcher) {
        try {
            TimeTracker timeTracker = new TimeTracker();
            for (; ; ) {
                long delay = timingInputStream.readLong() - timeTracker.getElapsed();
                if (delay > 0) {
                    Thread.sleep(delay);
                }
                frameDispatcher.dispatch(new ADTSFrame(aacInputStream));
            }
        } catch (Throwable t) {
        } finally {
            try {
                aacInputStream.close();
                timingInputStream.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
