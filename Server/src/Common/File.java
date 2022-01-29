package Common;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal
 */
public class File implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    private byte[] fileBytes;

    private int fileLen = 0;

    private String dest; // 文件目的路径

    private String src; // 源文件路径

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFileLen() {
        return fileLen;
    }

    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
