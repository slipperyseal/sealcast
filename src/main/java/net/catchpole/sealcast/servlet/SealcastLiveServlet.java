package net.catchpole.sealcast.servlet;

import net.catchpole.sealcast.EventLog;
import net.catchpole.sealcast.dispatch.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SealcastLiveServlet extends HttpServlet {
    private static final FrameQueueDispatcher frameDispatcher = new FrameQueueDispatcher(100000);
    public static final FeedDispatcher feedDispatcher = new FeedDispatcher(frameDispatcher);
    public static final EventLog eventLog = new EventLog(new File("events" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()) + ".log"));

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        feedDispatcher.start();
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        IcyHeaders icyHeaders = feedDispatcher.getIcyHeaders();
        if (icyHeaders == null) {
            httpServletResponse.getWriter().print("off air");
            return;
        }

        icyHeaders.write(httpServletResponse);
        OutputStream outputStream = httpServletResponse.getOutputStream();

        FrameQueue frameQueue = new FrameQueue(200000, new Listener(httpServletRequest.getRemoteAddr(), true));
        frameDispatcher.addFrameQueue(frameQueue);
        try {
            for (;;) {
                outputStream.write(frameQueue.getNextFrame().getData());
            }
        } finally {
            frameDispatcher.removeFrameQueue(frameQueue);
            eventLog.addEvent(frameQueue.toString());
        }
    }
}
