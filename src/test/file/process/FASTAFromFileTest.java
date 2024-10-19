package file.process;

import org.cai.file.processor.FilePath;
import org.cai.file.processor.FileProcessor;
import org.cai.file.type.FASTA;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author cai584770
 * @date 2024/10/15 18:43
 * @Version
 */
public class FASTAFromFileTest {

    @Test
    public void fromFile() {
        String file_path = "F:\\GeneData\\geseq\\ce\\ce11\\chrM.fa";
        FASTA fasta = FileProcessor.getFASTAFromFile(file_path);
        System.out.println(fasta.getInformation());
        System.out.println(fasta.getSequence());
    }

    @Test
    public void fromFile1() {
        String file_path = "F:\\GeneData\\geseq\\ce\\ce11\\chrM.fa";
        String[] informationAndSequence = FileProcessor.getInformationAndSequence(file_path);
        System.out.println(informationAndSequence[0]);
        System.out.println(informationAndSequence[1]);
    }


    @Test
    public void fromFile2() {
        String dir_path = "F:\\GeneData\\geseq\\ce\\ce11";
        List<String> allFilePaths = FilePath.getAllFilePaths(dir_path);

        for (String filePath : allFilePaths) {
            String[] informationAndSequence = FileProcessor.getInformationAndSequence(filePath);
            System.out.println(informationAndSequence[0]);
            System.out.println(informationAndSequence[1].length());
        }


    }

}
