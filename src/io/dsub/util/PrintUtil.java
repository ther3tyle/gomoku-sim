package io.dsub.util;

import io.dsub.game.model.Player;

public class PrintUtil {
    private static int width;

    private PrintUtil() {
        width = 45;
    }

    public static void printUsers(String player1, String player2) {
        String usersStr = String.format("%s VS %s\n", player1, player2);
        printMiddle(usersStr);
    }

    public static void printUsersWithVersus(Player player1, Player player2) {
        String usersVs = String.format("%s %d - %d %s",
                player1.getName(),
                player1.getNumWin(),
                player2.getNumWin(),
                player2.getName());
        printMiddle(usersVs);
    }

    public static void printHR() {
        System.out.println("-".repeat(width));
    }

    public static void printDoubleHR() {
        System.out.println("=".repeat(width));
    }

    private static int getPrintMargin(String s) {
        int margin = (width - s.length()) / 2;
        if (margin < 0) margin = 0;
        return margin;
    }

    public static void printWinner(String winnerName) {
        String winStr = "~ " + winnerName + " ~";
        String deco = "*+* WINNER *+*";

        printMiddle(deco);
        System.out.println();
        printMiddle(winStr);
    }

    public static void printLogo() {
        System.out.println(Constants.LOGO);
        System.out.println("The Gomoku Game");
    }

    public static void clearLines() {
        System.out.println("\n".repeat(49));
    }

    public static void printMiddle(String s) {
        int margin = getPrintMargin(s);
        System.out.println(" ".repeat(margin) + s);
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        PrintUtil.width = width;
    }
}
