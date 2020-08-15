package io.dsub.game.model;

import io.dsub.util.InputUtil;

public class Player implements Inputtable {
    private static int userId;

    private String name;
    private String prefix;
    private int numWin;
    private String input;

    public Player() {
        this("");
    }

    public Player(String name) {
        userId++;
        this.name = name;
        this.numWin = 0;
        this.input = "";
        this.name = String.valueOf(userId);
    }

    @Override
    public void getKeyboardInput() {
        System.out.printf("%s > ", this.prefix);
        this.input = InputUtil.takeInput();
    }

    public void win() {
        this.numWin++;
    }

    /**
     * Getters and setters
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumWin() {
        return numWin;
    }

    public void setNumWin(int numWin) {
        this.numWin = numWin;
    }

    public String getInput() {
        return input;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
