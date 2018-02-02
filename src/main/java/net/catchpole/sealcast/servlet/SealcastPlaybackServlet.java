package net.catchpole.sealcast.servlet;

import net.catchpole.sealcast.dispatch.*;
import net.catchpole.sealcast.recorder.PlaybackDispatcher;
import net.catchpole.sealcast.recorder.Recorder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SealcastPlaybackServlet extends HttpServlet {
    public static final List<Listener> listeners = new ArrayList<Listener>();

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        IcyHeaders icyHeaders = SealcastLiveServlet.feedDispatcher.getIcyHeaders();
        if (icyHeaders == null) {
            httpServletResponse.getWriter().print("off air");
            return;
        }
        icyHeaders.write(httpServletResponse);

        Listener listener = new Listener(httpServletRequest.getRemoteAddr(), false);
        synchronized (listeners) {
            listeners.add(listener);
        }
        try {
            Recorder recorder = SealcastLiveServlet.feedDispatcher.getRecorder();
            if (recorder == null) {
                httpServletResponse.setStatus(404);
            } else {
                PlaybackDispatcher playbackDispatcher = new PlaybackDispatcher(recorder.getName());
                playbackDispatcher.dispatch(new OutputStreamFrameDispatcher(httpServletResponse.getOutputStream()));
            }
        } finally {
            synchronized (listeners) {
                listeners.remove(listener);
            }
        }
    }
}
