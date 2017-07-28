package net.catchpole.sealcast.dispatch;

public class Listener {
    private static int serialNumber;
    private final int serial = serialNumber++;
    private final String origin;
    private final boolean live;

    public Listener(String origin, boolean live) {
        this.origin = origin;
        this.live = live;
    }

    public int getSerial() {
        return serial;
    }

    public String getOrigin() {
        return origin;
    }

    public boolean isLive() {
        return live;
    }
}
