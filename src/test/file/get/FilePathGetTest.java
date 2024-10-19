package file.get;

import org.cai.file.processor.FilePath;
import org.cai.file.processor.FileProcessor;
import org.cai.file.type.FASTA;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author cai584770
 * @date 2024/10/15 19:07
 * @Version
 */
public class FilePathGetTest {

    @Test
    public void getFilePath(){
        String file_path = "F:\\GeneData\\geseq\\ce\\ce11";
        List<String> allFilePaths = FilePath.getAllFilePaths(file_path);

        for (String allFilePath : allFilePaths) {
            System.out.println(allFilePath);
        }

    }


}
