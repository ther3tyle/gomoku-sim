package io.dsub.util;

import io.dsub.game.model.Player;

public class PrintUtil {


    private static int width;

    private PrintUtil() {
        width = 45;
    }

    public static void printUsers(String player1, String player2) {
        String usersStr = String.format("%s VS %s\n", player1, player2);
        printAtMiddle(usersStr);
    }

    public static void printUsersWithVersus(Player player1, Player player2) {
        String usersVs = String.format("%s %d - %d %s",
                player1.getName(),
                player1.getWinCount(),
                player2.getWinCount(),
                player2.getName());
        printAtMiddle(usersVs);
    }

    public static void printHR() {
        System.out.println("-".repeat(width));
    }

    public static void printDoubleHR() {
        System.out.println("=".repeat(width));
    }

    private static int getPrintLeftMarginString(String s) {
        int margin = (width - s.length()) / 2;
        if (margin < 0) margin = 0;
        return margin;
    }

    public static void printWinner(String winnerName) {
        String winStr = "~ " + winnerName + " ~";
        String deco = "*+* WINNER *+*";

        printAtMiddle(deco);
        System.out.println();
        printAtMiddle(winStr);
    }

    public static void printLogo() {
        System.out.println(Constants.LOGO);
        System.out.println("The Gomoku Game");
    }

    public static void clearLines() {
        System.out.println("\n".repeat(49));
    }

    public static void printEnterNamePrompt(Player player) {
        System.out.printf("ENTER PLAYER %s NAME > ", player.getName());
    }

    public static void printEnterPositionPrompt(Player player) {
        System.out.printf("%s > ", player.getName());
    }

    public static void printAtMiddle(String s) {
        int margin = getPrintLeftMarginString(s);
        System.out.println(" ".repeat(margin) + s);
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        PrintUtil.width = width;
    }
}
