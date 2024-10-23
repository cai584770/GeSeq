package exp.exp1.bsc;

import exp.mysql.base.TestBase;
import org.cai.file.processor.FilePath;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author cai584770
 * @date 2024/10/16 10:24
 * @Version
 */
public class BSCTest {

    @Test
    public void bsc(){
        try {
            List<String> allFilePaths = FilePath.getAllFilePaths(TestBase.Path_tair1000);

            String bscPath = "D:\\迅雷下载\\bsc-3.3.4-x64\\bsc.exe";
            String parameter = "e";

            for (String inputFilePath : allFilePaths) {
//                String outputFilePath =  TestBase.Path_Compress_bsc+"\\"+FilePath.extractParentFolderNameFromFullPath(inputFilePath)+"\\"+FilePath.extractNameFromFullPath(inputFilePath)+".bsc";

//              --------------tair1000 start-----------------------
                String outputFolder = TestBase.Path_Compress_bsc+"\\tair1000\\"+FilePath.extractParentFolderNameFromFullPath(inputFilePath);
                FilePath.createFolder(outputFolder);
                String outputFilePath =  outputFolder +"\\"+FilePath.extractNameFromFullPath(inputFilePath)+".bsc";
//              --------------tair1000 end-----------------------

                String command = bscPath + " " + parameter + " " + inputFilePath + " " + outputFilePath;
                Process process = Runtime.getRuntime().exec(command);

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println(inputFilePath + " compress success!");
                } else {
                    System.out.println(inputFilePath + " compress failed!");
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void outputPathTest(){
        List<String> allFilePaths = FilePath.getAllFilePaths(TestBase.Path_CE11);

        for (String filePath : allFilePaths) {
            System.out.println(filePath);
            String outputFolderPath =  TestBase.Path_Compress_bsc+"\\"+FilePath.extractParentFolderNameFromFullPath(filePath)+"\\"+FilePath.extractNameFromFullPath(filePath)+".bsc";
            System.out.println(outputFolderPath);

        }

    }


}
