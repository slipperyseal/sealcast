package net.catchpole.sealcast;

import java.io.File;
import java.io.FileOutputStream;

public class EventLog {
    private File file;

    public EventLog(File file) {
        this.file = file;
    }

    public void addEvent(String string) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            try {
                fileOutputStream.write(string.getBytes());
                fileOutputStream.write('\n');
            } finally {
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
