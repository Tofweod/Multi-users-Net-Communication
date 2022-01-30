package Utils;

import java.util.Scanner;

/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal
 */
public class Utility {
    private static Scanner scanner = new Scanner(System.in);

    private static String readKeyboard (int limit, boolean returnBlank) {
        String line = "";

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.length() == 0) {
                if (returnBlank) return line;
            }

            if (line.length() < 1 || line.length() > limit) {
                System.out.println("输入有误");
                continue;
            }
            break;
        }
        return line;
    }

    public static String readString(int limit) {
        return readKeyboard(limit,false);
    }
}
