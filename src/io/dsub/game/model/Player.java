package io.dsub.game.model;


public abstract class Player implements Inputtable {

    private static int PLAYER_ID = 1;

    private final int id;
    private String name;
    private int winCount;
    private String lastInput;

    public Player() {
        this("PLAYER" + " " + PLAYER_ID);
    }

    public Player(String name) {
        this.name = name;
        this.id = PLAYER_ID;
        this.winCount = 0;
        this.lastInput = "";
        PLAYER_ID++;
    }

    /**
     * An abstraction of method to update lastInput parameter.
     */
    @Override
    public abstract void getKeyboardInput();

    public void addWinCount() {
        this.winCount++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public String getLastInput() {
        return lastInput;
    }

    public void setLastInput(String lastInput) {
        this.lastInput = lastInput;
    }
}
