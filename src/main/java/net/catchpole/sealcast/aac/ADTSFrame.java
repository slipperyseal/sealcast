package net.catchpole.sealcast.aac;

//    letter, pos, length, description
//    A 12   12 syncword 0xFFF, all bits must be 1
//    B 13   1  MPEG Version: 0 for MPEG-4, 1 for MPEG-2
//    C 15   2  Layer: always 0
//    D 16   1  protection absent, Warning, set to 1 if there is no CRC and 0 if there is CRC
//
//    E 18   2  profile, the MPEG-4 Audio Object Type minus 1
//    F 22   4  MPEG-4 Sampling Frequency Index (15 is forbidden)
//    G 23   1  private bit, guaranteed never to be used by MPEG, set to 0 when encoding, ignore when decoding
//    H 26   3  MPEG-4 Channel Configuration (in the case of 0, the channel configuration is sent via an inband PCE)
//    I 27   1  originality, set to 0 when encoding, ignore when decoding
//    J 28   1  home, set to 0 when encoding, ignore when decoding
//    K 29   1  copyrighted id bit, the next bit of a centrally registered copyright identifier, set to 0 when encoding, ignore when decoding
//    L 30   1  copyright id start, signals that this frame's copyright id bit is the first bit of the copyright id, set to 0 when encoding, ignore when decoding
//    M 43   13 frame length, this value must include 7 or 9 bytes of header length: FrameLength = (ProtectionAbsent == 1 ? 7 : 9) + size(AACFrame)
//
//    O 11 Buffer fullness
//    P 66 2  Number of AAC frames (RDBs) in ADTS frame minus 1, for maximum compatibility always use 1 AAC frame per ADTS frame
//    Q 16 CRC if protection absent is 0

import java.io.DataInputStream;
import java.io.IOException;

public class ADTSFrame {
    private byte[] data;

    public ADTSFrame(DataInputStream dataInputStream) throws IOException {
        long l = dataInputStream.readLong();
        int marker = (int)(l >> (64-12) & 0xFFF);
        if (marker != 0xfff) {
            throw new IOException("ADTS marker not found");
        }
        //boolean copy = (l >> (64-16) & 0x1) == 0L;
        int len = (int)(l >> (64-43) & 0x1FFF);

        this.data = new byte[len];
        data[0] = (byte)(l>>56);
        data[1] = (byte)(l>>48);
        data[2] = (byte)(l>>40);
        data[3] = (byte)(l>>32);
        data[4] = (byte)(l>>24);
        data[5] = (byte)(l>>16);
        data[6] = (byte)(l>>8);
        data[7] = (byte)l;
        dataInputStream.readFully(data, 8, data.length-8);
    }

    public byte[] getData() {
        return data;
    }

    public String toString() {
        return this.getClass().getSimpleName() + " " + data.length;
    }
}
