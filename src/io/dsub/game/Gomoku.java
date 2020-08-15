package io.dsub.game;

import io.dsub.game.model.Player;
import io.dsub.game.model.Position;
import io.dsub.util.PrintUtil;
import io.dsub.validator.GameValidator;
import io.dsub.validator.InputValidator;

import static io.dsub.util.InputUtil.splitToInteger;
import static io.dsub.validator.GameValidator.chkThreeByThree;

public class Gomoku implements Simulatable, Winnable, Playable, Printable {

    // Fields
    private final Player player1;
    private final Player player2;
    private final int maxRound;
    private final Board board;

    private Player curPlayer;
    private Player winner;

    private boolean finished = false;
    private String messageString = "";

    /**
     * Constructors
     */
    public Gomoku() {
        this(new Player("PLAYER 1"), new Player("PLAYER 2"));
    }

    public Gomoku(Player player1, Player player2) {
        this(player1, player2, 5);
    }

    public Gomoku(Player player1, Player player2, int maxRound) {
        this.player1 = player1;
        this.player2 = player2;
        this.curPlayer = player1;
        this.winner = null;
        this.maxRound = maxRound;
        this.board = new Board(15);
    }

    @Override
    public void play(Player player, Position pos) {
        this.board.insert(pos, getTargetChar(player));

        if (chkThreeByThree(this.board, pos, this.getTargetChar(curPlayer))) {
            Player winner_ = curPlayer == player1 ? player2 : player1;
            processWinner(winner_);
            return;
        }

        if (GameValidator.findWinner(this.board, pos)) {
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
    public Player getWinner() {
        return this.winner;
    }

    public void run() {
        while (true) {
            PrintUtil.clearLines();
            PrintUtil.printLogo();
            printStatus();
            curPlayer.getKeyboardInput();

            if (isQuit()) break;

            if (InputValidator.isInvalidPosFormat(curPlayer.getInput())) {
                this.messageString = "Invalid Input Format. Try Again.";
                continue;
            }

            int[] posValues = splitToInteger(curPlayer.getInput(), "[-_., ]");
            Position pos = new Position(posValues[0], posValues[1]);


            if (GameValidator.isInvalidPosition(pos, this.board.getN())) {
                this.messageString = "Invalid Position. Try Again.";
                continue;
            }

            if (this.board.getValue(pos) != '+') {
                this.messageString = String.format("[%d %d] Already Occupied. Try Again.\n", pos.getX(), pos.getY());
                continue;
            }

            this.play(curPlayer, pos);
            curPlayer = curPlayer == player1 ? player2 : player1;

            if (this.finished) {
                this.reset();
                if (player1.getNumWin() > this.maxRound || player2.getNumWin() > this.maxRound) {
                    break;
                }
                this.finished = false;
            }
        }
        processExit();
    }

    public char getTargetChar(Player player) {
        return player == player1 ? 'X' : 'O';
    }

    private void takeUserName(Player player) {
        player.setPrefix(String.format("ENTER PLAYER %s NAME", player.getName()));
        player.getKeyboardInput();

        while (InputValidator.isNotUnique(player.getInput(), "")) {
            player.getKeyboardInput();
        }

        if (isQuit()) processExit();
        player.setName(player.getInput());
        player.setPrefix(String.format("%s", player.getName()));
    }

    private void processWinner(Player player) {
        this.winner = player;
        this.winner.win();
        this.messageString = String.format("%s: %d번 이겼다!)\n", this.winner.getName(), this.winner.getNumWin());
        this.isFinished();
    }

    private void processExit() {
        PrintUtil.clearLines();
        PrintUtil.printLogo();

        int p1WinCount, p2WinCount;
        p1WinCount = player1.getNumWin();
        p2WinCount = player2.getNumWin();

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
        return player1.getInput().equals("Q") ||
                player2.getInput().equals("Q");
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
