package io.dsub.game;

import io.dsub.game.model.Player;
import io.dsub.game.model.Position;
import io.dsub.util.PrintUtil;
import io.dsub.validator.GameValidator;
import io.dsub.validator.InputValidator;

import static io.dsub.util.InputUtil.splitToInteger;
import static io.dsub.validator.GameValidator.isThreeByThree;

public class Gomoku implements Simulatable, Winnable, Playable, Printable {

    // Fields
    private final Player player1;
    private final Player player2;
    private final int maxRound;
    private final Board board;

    private Player currentPlayer;
    private Player currentWinner;

    private boolean finished = false;
    private String messageString = "";

    /**
     * Constructors
     */

    public Gomoku(Player player1, Player player2) {
        this(player1, player2, 5);
    }

    public Gomoku(Player player1, Player player2, int maxRound) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.currentWinner = null;
        this.maxRound = maxRound;
        this.board = new Board(15);
    }

    @Override
    public void play(Player player, Position pos) {
        this.board.insert(pos, getTargetChar(player));

        if (isThreeByThree(this.board, pos, this.getTargetChar(currentPlayer))) {
            Player winner_ = currentPlayer == player1 ? player2 : player1;
            processWinner(winner_);
            return;
        }

        if (GameValidator.isWinner(this.board, pos)) {
            processWinner(player);
        }
    }

    @Override
    public void printStatus() {
        PrintUtil.printDoubleHR();
        System.out.println("STATS");
        PrintUtil.printUsersWithVersus(player1, player2);
        PrintUtil.printDoubleHR();

        board.print();
        PrintUtil.printDoubleHR();
        if (this.messageString.length() > 0) {
            System.out.printf("%s\n", this.messageString);
            this.messageString = "";
        }
    }

    @Override
    public void initialize() {
        this.reset();

        PrintUtil.clearLines();
        PrintUtil.printLogo();
        System.out.println();

        takeUserName(player1);
        takeUserName(player2);

        PrintUtil.printUsers(player1.getName(), player2.getName());
    }

    @Override
    public void isFinished() {
        this.finished = true;
    }

    @Override
    public void reset() {
        this.board.flush();
    }

    @Override
    public Player getCurrentWinner() {
        return this.currentWinner;
    }

    // todo: remove all breaks and continues
    public void run() {
        while (true) {
            PrintUtil.clearLines();
            PrintUtil.printLogo();
            printStatus();
            PrintUtil.printEnterPositionPrompt(currentPlayer);
            currentPlayer.getKeyboardInput();

            if (isQuit()) break;

            if (InputValidator.isInvalidPositionInput(currentPlayer.getLastInput())) {
                this.messageString = "Invalid Input Format. Try Again.";
                continue;
            }

            int[] posValues = splitToInteger(currentPlayer.getLastInput(), "[-_., ]");
            Position pos = new Position(posValues[0], posValues[1]);

            if (GameValidator.isInvalidPosition(pos)) {
                this.messageString = "Invalid Position. Try Again.";
                continue;
            }

            if (this.board.getValue(pos) != '+') {
                this.messageString = String.format("[%d %d] Already Occupied. Try Again.\n", pos.getX(), pos.getY());
                continue;
            }

            this.play(currentPlayer, pos);
            swapCurrentPlayer();

            if (this.finished) {
                if (isReachedMaxWinCount()) {
                    break;
                }

                // set loser to open next round
                if (currentPlayer == currentWinner) {
                    swapCurrentPlayer();
                }

                this.reset();
                this.finished = false;
            }
        }
        doExit();
    }

    public char getTargetChar(Player player) {
        return player == player1 ? 'X' : 'O';
    }

    private boolean isReachedMaxWinCount() {
        return player1.getWinCount() > this.maxRound || player2.getWinCount() > this.maxRound;
    }

    private void swapCurrentPlayer() {
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }

    // todo: be more clear about what it does.
    private void takeUserName(Player player) {
        PrintUtil.printEnterNamePrompt(player);
        player.getKeyboardInput();

        while (InputValidator.isNotUnique(player.getLastInput(), "")) {
            player.getKeyboardInput();
        }

        if (isQuit()) {
            doExit();
        }
        player.setName(player.getLastInput());
    }

    private void processWinner(Player player) {
        this.currentWinner = player;
        this.currentWinner.addWinCount();
        this.messageString = String.format("%s: %d번 이겼다!\n", this.currentWinner.getName(), this.currentWinner.getWinCount());
        this.isFinished();
    }

    private void doExit() {
        PrintUtil.clearLines();
        PrintUtil.printLogo();

        int p1WinCount, p2WinCount;
        p1WinCount = player1.getWinCount();
        p2WinCount = player2.getWinCount();

        if (p1WinCount != p2WinCount) {
            Player winner_ = p1WinCount > p2WinCount ? player2 : player1;
            PrintUtil.printWinner(winner_.getName());
        }

        if (!player1.getName().equals("1") && !player2.getName().equals("2")) {
            PrintUtil.printUsersWithVersus(player1, player2);
        }

        System.out.println("BYE");
        System.exit(1);
    }

    private boolean isQuit() {
        return player1.getLastInput().equals("Q") ||
                player2.getLastInput().equals("Q");
    }

    /**
     * GETTERS
     */
    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Board getBoard() {
        return board;
    }
}
