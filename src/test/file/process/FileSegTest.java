package file.process;

import org.cai.file.processor.FilePath;
import org.cai.file.processor.FileSeg;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cai584770
 * @date 2024/10/15 20:23
 * @Version
 */
public class FileSegTest {

    @Test
    public void seg(){
        String inputFolders = "F:\\GeneData\\tentairs\\tair1000";
        String outputFolders = "F:\\GeneData\\tentairs\\tairseg";

        List<String> subDirectories =  FilePath.getAllFilePaths(inputFolders);
        for (String filePath : subDirectories) {
            FileSeg.seg(filePath,outputFolders);
        }

    }

    @Test
    public void getParents(){
        String filePath = "F:\\GeneData\\tentairs\\tair1000\\pseudo5993\\pseudo5993.fasta";
        String name = FilePath.extractNameFromFullPath(filePath);
        if (name!= null) {
            System.out.println(name);
        }

    }



}
