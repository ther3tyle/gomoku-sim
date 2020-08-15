package io.dsub.validator;

import java.util.regex.Pattern;

import static io.dsub.util.InputUtil.splitToInteger;

public class InputValidator {

    private InputValidator() {
    }

    private static boolean inRange(int num) {
        return num > 0 && num < 16;
    }

    public static boolean isInvalidPosFormat(String input) {
        boolean match = Pattern.matches("^[0-9]{1,2}[-_., ][0-9]{1,2}$", input);
        if (!match) return true;

        int[] ints = splitToInteger(input, "[-_., ]");
        if(ints.length < 2) return true;

        for (int value : ints) {
            if (!inRange(value)) return true;
        }

        return false;
    }

    public static boolean isNotUnique(String input, String... list) {
        input = input.trim();
        for (String s : list) {
            if (s.equalsIgnoreCase(input)) {
                String val = s.equalsIgnoreCase("") ? "EMPTY" : s;
                System.out.printf("[ERROR] INPUT MUST NOT BE %s. TRY AGAIN!\n", val);
                return true;
            }
        }
        return false;
    }
}
