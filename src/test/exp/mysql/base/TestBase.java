package exp.mysql.base;

/**
 * @author cai584770
 * @date 2024/10/15 15:48
 * @Version
 */
public class TestBase {
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    public static final String USER = "root";
    public static final String PASS = "cai584770";

    public static final String Path_CE11 = "F:\\GeneData\\geseq\\orignal\\ce11";
    public static final String Path_HG38 = "F:\\GeneData\\geseq\\orignal\\HG38";
    public static final String Path_sacCer3 = "F:\\GeneData\\geseq\\orignal\\sacCer3";
    public static final String Path_tair1000 = "F:\\GeneData\\tentairs\\tairseg";


    // insert sql
    public static final String Insert_hg38_str = "INSERT INTO hg38_str (information, sequence) VALUES (?, ?)";
    public static final String Insert_hg38_blob = "INSERT INTO hg38_blob (information, sequence) VALUES (?, ?)";
    public static final String Insert_ce11_str = "INSERT INTO ce11_str (information, sequence) VALUES (?, ?)";
    public static final String Insert_ce11_blob = "INSERT INTO ce11_blob (information, sequence) VALUES (?, ?)";
    public static final String Insert_sacCer3_str = "INSERT INTO sacCer3_str (information, sequence) VALUES (?, ?)";
    public static final String Insert_sacCer3_blob = "INSERT INTO sacCer3_blob (information, sequence) VALUES (?, ?)";
    public static final String Insert_tair1000_str = "INSERT INTO tair1000_str (information, sequence) VALUES (?, ?)";
    public static final String Insert_tair1000_blob = "INSERT INTO tair1000_blob (information, sequence) VALUES (?, ?)";

    public static final String Path_Compress_bsc = "F:\\GeneData\\geseq\\result-bsc";
    public static final String Path_Compress_bz2 = "F:\\GeneData\\geseq\\result-bz2";
    public static final String Path_Compress_Genozip = "F:\\GeneData\\geseq\\result-Genozip";
    public static final String Path_Compress_lzma = "F:\\GeneData\\geseq\\result-lzma";
    public static final String Path_Compress_xz = "F:\\GeneData\\geseq\\result-xz";


}
