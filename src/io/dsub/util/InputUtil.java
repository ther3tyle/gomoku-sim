package io.dsub.util;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    private InputUtil() {}

    public static String takeInput() {
        return scanner.nextLine().trim().toUpperCase();
    }

    public static int[] splitToInteger(String s, String regex) {
        String[] strings = s.split(regex);
        int[] nums = new int[strings.length];
        int i = 0;
        for (String str : strings) {
            nums[i++] = Integer.parseInt(str);
        }
        return nums;
    }
}
