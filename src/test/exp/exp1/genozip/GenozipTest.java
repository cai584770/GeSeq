package exp.exp1.genozip;

import exp.mysql.base.TestBase;
import org.cai.file.processor.FilePath;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author cai584770
 * @date 2024/10/16 16:19
 * @Version
 */
public class GenozipTest {

    @Test
    public void genozip(){
        try {
            List<String> allFilePaths = FilePath.getAllFilePaths(TestBase.Path_tair1000);

            String bscPath = "E:\\Genozip\\Genozip\\genozip.exe";
            String parameter = "-o";

            for (String inputFilePath : allFilePaths) {
//                String outputFilePath =  TestBase.Path_Compress_Genozip+"\\"+FilePath.extractParentFolderNameFromFullPath(inputFilePath)+"\\"+FilePath.extractNameFromFullPath(inputFilePath)+".genozip";

//              --------------tair1000 start-----------------------
                String outputFolder = TestBase.Path_Compress_Genozip+"\\tair1000\\"+FilePath.extractParentFolderNameFromFullPath(inputFilePath);
                FilePath.createFolder(outputFolder);
                String outputFilePath =  outputFolder +"\\"+FilePath.extractNameFromFullPath(inputFilePath)+".genozip";
//              --------------tair1000 end-----------------------

                String command = bscPath + " " + inputFilePath + " " + parameter + " " + outputFilePath;
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

}
