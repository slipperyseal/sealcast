package net.catchpole.sealcast.dispatch;

import junit.framework.TestCase;
import net.catchpole.sealcast.aac.ADTSFrame;
import net.catchpole.sealcast.source.ShoutcastReader;
import org.junit.Ignore;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;

public class ShoutcastReaderTest {
    @Ignore
    @Test
    public void tripleJTest() throws IOException {
        ShoutcastReader shoutcastReader = new ShoutcastReader("http://live-radio01.mediahubaustralia.com/2TJW/aac/");
        DataInputStream inputStream = new DataInputStream(shoutcastReader.getInputStream());
        ADTSFrame adtsFrame = null;
        try {
            TestCase.assertNotNull(inputStream);
            System.out.println(shoutcastReader.getIcyHeaders());

            for (; ; ) {
                adtsFrame = new ADTSFrame(inputStream);
                System.out.println(adtsFrame);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            int l=0;
            for (int d : adtsFrame.getData()) {
                System.out.print((char) d);
                if (l++ > 70) {
                    System.out.println();
                    l=0;
                }
            }
        } finally {
            inputStream.close();
        }
    }
}
