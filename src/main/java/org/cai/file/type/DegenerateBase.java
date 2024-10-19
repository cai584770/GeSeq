package org.cai.file.type;

/**
 * @author cai584770
 * @date 2024/10/16 20:46
 * @Version
 */
public class DegenerateBase {

    private int position;
    private String bases;

    public DegenerateBase(int position, String bases) {
        this.position = position;
        this.bases = bases;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getBases() {
        return bases;
    }

    public void setBases(String bases) {
        this.bases = bases;
    }
}
