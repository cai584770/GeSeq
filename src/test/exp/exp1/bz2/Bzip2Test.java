package exp.exp1.bz2;

import exp.mysql.connect.TestBase;
import org.cai.file.compress.Bzip2;
import org.cai.file.processor.FilePath;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author cai584770
 * @date 2024/10/16 13:06
 * @Version
 */
public class Bzip2Test {

    @Test
    public void bz2Test(){
        try {
            List<String> allFilePaths = FilePath.getAllFilePaths(TestBase.Path_tair1000);

            for (String inputFilePath : allFilePaths) {
//                String outputFilePath =  TestBase.Path_Compress_bz2+"\\"+FilePath.extractParentFolderNameFromFullPath(inputFilePath)+"\\"+FilePath.extractNameFromFullPath(inputFilePath)+".bz2";
//
//                System.out.println(inputFilePath);
//                System.out.println(outputFilePath);

//              --------------tair1000 start-----------------------
                String outputFolder = TestBase.Path_Compress_bz2+"\\tair1000\\"+FilePath.extractParentFolderNameFromFullPath(inputFilePath);
                FilePath.createFolder(outputFolder);
                String outputFilePath =  outputFolder +"\\"+FilePath.extractNameFromFullPath(inputFilePath)+".bz2";
//              --------------tair1000 end-----------------------

                Bzip2.bz2(inputFilePath,outputFilePath);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
