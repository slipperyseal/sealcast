package net.catchpole.sealcast.recorder;

import net.catchpole.sealcast.aac.ADTSFrame;
import net.catchpole.sealcast.dispatch.FrameDispatcher;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class PlaybackDispatcher {
    private DataInputStream aacInputStream;
    private DataInputStream timingInputStream;
    private long primingMillis;

    public PlaybackDispatcher(String name, long primingMillis) throws IOException {
        this.aacInputStream = new DataInputStream(new FileInputStream(name + ".aac"));
        this.timingInputStream = new DataInputStream(new FileInputStream(name + ".time"));
        this.primingMillis = primingMillis;
    }

    public void dispatch(FrameDispatcher frameDispatcher) {
        try {
            long primeTime;
            while ((primeTime = timingInputStream.readLong()) < primingMillis) {
                frameDispatcher.dispatch(new ADTSFrame(aacInputStream));
            }

            long startTime = System.currentTimeMillis() + primeTime;
            for (; ; ) {
                long delay = startTime + timingInputStream.readLong() - System.currentTimeMillis();
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
