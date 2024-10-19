package org.cai.file.processor;

import java.io.*;

/**
 * @author cai584770
 * @date 2024/10/15 20:22
 * @Version
 */
public class FileSeg {
    public static void seg(String inputFilePath, String outputFolders) {
        String outputFolderPath =  outputFolders+"\\"+FilePath.extractNameFromFullPath(inputFilePath);
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            for (int i = 1; i < 6; i++) {
                String outputFilePath = outputFolderPath+"\\"+"chr"+i+".fa";
                String line1 = br.readLine();
                String line2 = br.readLine();
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath, true))) {
                    bw.write(line1);
                    bw.newLine();
                    bw.write(line2);
                    bw.newLine();
                }
            }
            System.out.println(inputFilePath+": process!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
