package net.catchpole.sealcast.aac;

import org.junit.Ignore;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ADTSFrameTest {
    @Ignore
    @Test
    public void test() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("test-out.aac");
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream("test.aac"));
            try {
                int x = 0;
                for (; ; ) {
                    ADTSFrame adtsWholeFrame = new ADTSFrame(dataInputStream);
                    System.out.println(" " + x + " " + adtsWholeFrame.getData().length);
                    // drop every second frame
                    if ((x&1) == 0) {
                        fileOutputStream.write(adtsWholeFrame.getData());
                    }
                    x++;
                }
            } finally {
                dataInputStream.close();
            }
        } finally {
            fileOutputStream.close();
        }
    }
}
