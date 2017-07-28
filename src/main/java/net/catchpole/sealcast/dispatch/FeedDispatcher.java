package net.catchpole.sealcast.dispatch;

import net.catchpole.console.TimeTracker;
import net.catchpole.sealcast.ConsoleView;
import net.catchpole.sealcast.aac.ADTSFrame;
import net.catchpole.sealcast.recorder.Recorder;
import net.catchpole.sealcast.servlet.IcyHeaders;
import net.catchpole.sealcast.source.SocketFeedSource;

import java.io.DataInputStream;

public class FeedDispatcher {
    private FrameQueueDispatcher frameDispatcher;
    private IcyHeaders icyHeaders;
    private TimeTracker timeTracker;
    private Recorder recorder;

    public FeedDispatcher(FrameQueueDispatcher frameDispatcher) {
        this.frameDispatcher = frameDispatcher;
    }

    public IcyHeaders getIcyHeaders() {
        return icyHeaders;
    }

    public void listen() throws Exception {
        SocketFeedSource socketFeedSource = new SocketFeedSource(9999);
        for (; ; ) {
            try {
                DataInputStream dataInputStream = socketFeedSource.getDataInputStream();
                this.icyHeaders = socketFeedSource.getHeaders();

                this.timeTracker = new TimeTracker();

                this.recorder = new Recorder(Long.toString(System.currentTimeMillis()));
                ConsoleView consoleView = new ConsoleView(frameDispatcher, timeTracker);
                consoleView.start();
                try {
                    for (; ; ) {
                        ADTSFrame adtsFrame = new ADTSFrame(dataInputStream);
                        this.frameDispatcher.dispatch(adtsFrame);
                        this.recorder.add(adtsFrame, timeTracker.getElapsed());
                    }
                } finally {
                    this.recorder.close();
                    consoleView.stop();
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public Recorder getRecorder() {
        return recorder;
    }

    public void start() {
        new Thread() {
            public void run() {
                try {
                    listen();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }.start();
    }
}
