package org.cai.file.type;

/**
 * @author cai584770
 * @date 2024/10/15 17:10
 * @Version
 */
public class FASTA {
    private final String information;
    private final String sequence;

    public FASTA(String information, String sequence) {
        this.information = information;
        this.sequence = sequence;
    }

    public String getInformation() {
        return information;
    }

    public String getSequence() {
        return sequence;
    }


}
