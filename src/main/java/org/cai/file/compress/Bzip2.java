package org.cai.file.compress;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

import java.io.*;

/**
 * @author cai584770
 * @date 2024/10/16 13:05
 * @Version
 */
public class Bzip2 {

    public static void bz2(String inputFilePath, String outputFilePath) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFilePath));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFilePath));
             BZip2CompressorOutputStream bzip2Out = new BZip2CompressorOutputStream(bos)) {

            byte[] buffer = new byte[65536];
            int len;

            while ((len = bis.read(buffer))!= -1) {
                bzip2Out.write(buffer, 0, len);
            }

            System.out.println(inputFilePath + " compress success!");
        }
    }

}
