package Utils;

import java.io.*;

/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal
 */
public class StreamUtils {
  /**
   * 将InputStream转换为byte数组
   */
  public static byte[] inputStreamToByteArray(InputStream is) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    byte[] buf = new byte[1024];
    int readLen = 0;
    while ((readLen = is.read(buf)) != -1) {
      bos.write(buf,0,readLen);
    }
    return bos.toByteArray();
  }

  /**
   * 将InputStream转换为String
   */
  public static String inputStreamToString(InputStream is) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    StringBuilder data = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      data.append(line).append("\r\n");
    }
    return data.toString();
  }
}
