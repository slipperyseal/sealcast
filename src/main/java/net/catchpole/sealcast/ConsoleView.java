package net.catchpole.sealcast;

import net.catchpole.console.ConsolePanel;
import net.catchpole.console.DataUpdate;
import net.catchpole.console.TimeTracker;
import net.catchpole.sealcast.dispatch.FrameQueueDispatcher;
import net.catchpole.sealcast.dispatch.Listener;

import java.util.ArrayList;
import java.util.List;

public class ConsoleView {
    private ConsolePanel consolePanel = new ConsolePanel(System.out);
    private FrameQueueDispatcher frameDispatcher;
    private TimeTracker timeTracker;
    private Thread thread;

    public ConsoleView(FrameQueueDispatcher frameDispatcher, TimeTracker timeTracker) {
        this.frameDispatcher = frameDispatcher;
        this.timeTracker = timeTracker;
    }

    private void tracking() {
        this.consolePanel.getConsoleWriter().eraseScreen();
        this.consolePanel.addHeading(1).update("Sealcast Server");
        final DataUpdate<String> infoRow = consolePanel.addRow(2);
        this.consolePanel.addHeading(4).update("Listeners");
        final DataUpdate<List<String>> clientList = consolePanel.addList(5, 16);
        this.consolePanel.addHeading(5+16).update("");

        for (;;) {
            try {
                infoRow.update("Listeners: " + frameDispatcher.getListenerCount() +
                                "\tSince " + (timeTracker == null ? "" : timeTracker.getStartDate()) +
                                "\tBroadcasting " + (timeTracker == null ? "[OFF AIR]" : timeTracker.getHoursMinsSeconds()));
                List<String> listenerRows = new ArrayList<String>();
                int n=1;
                for (Listener listener : frameDispatcher.getListeners()) {
                    listenerRows.add("" + (n++) + '\t' + listener.getSerial() + '\t' +
                            (listener.isLive() ? "LIVE    " : "playback") + '\t'
                            + listener.getOrigin());
                }
                clientList.update(listenerRows);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        this.thread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(5000);
                    tracking();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }
}
