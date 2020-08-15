package io.dsub.game;

import io.dsub.game.model.Position;

import java.util.Arrays;

public class Board {
    private final char[][] data;
    private final int n;
    private final char EMPTY_POS = '+';

    public Board(int n) {
        this.data = new char[n][n];
        this.n = n;
        this.flush();
    }

    public void insert(Position position, char value) {
        insert(position.getY() - 1, position.getX() - 1, value);
    }

    private void insert(int row, int col, char value) {
        this.data[row][col] = value;
    }

    public char getValue(Position position) {
        return getValue(position.getY(), position.getX());
    }

    private char getValue(int row, int col) {
        return this.data[row - 1][col - 1];
    }

    public int getN() {
        return this.n;
    }

    public void flush() {
        for (char[] ch : this.data) {
            Arrays.fill(ch, EMPTY_POS);
        }
    }

    public void print() {
        for (char[] ch : this.data) {
            for (char c : ch) {
                if (c == EMPTY_POS)
                    System.out.print(" + ");
                else
                    System.out.printf("[%c]", c);
            }
            System.out.println();
        }
    }
}
