package net.catchpole.sealcast.recorder;

import net.catchpole.sealcast.aac.ADTSFrame;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Recorder {
    private String name;
    private DataOutputStream aacOutputStream;
    private DataOutputStream timingOutputStream;

    public Recorder(String name) throws IOException {
        this.name = name;
        this.aacOutputStream = new DataOutputStream(new FileOutputStream(name + ".aac"));
        this.timingOutputStream = new DataOutputStream(new FileOutputStream(name + ".time"));
    }

    public void add(ADTSFrame adtsFrame, long offset) throws IOException {
        aacOutputStream.write(adtsFrame.getData());
        timingOutputStream.writeLong(offset);
    }

    public String getName() {
        return name;
    }

    public void close() throws IOException {
        this.aacOutputStream.close();
        this.timingOutputStream.close();
    }
}
