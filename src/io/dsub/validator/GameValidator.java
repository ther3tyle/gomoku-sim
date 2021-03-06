package io.dsub.validator;

import io.dsub.game.Board;
import io.dsub.game.model.Position;

public class GameValidator {
    private GameValidator() {
    }

    private static final int[][] DIRECTIONS =
            new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}};

    public static boolean isInvalidPosition(Position... positions) {
        for (Position position : positions) {
            if (isInvalidPosition(position))
            return true;
        }
        return false;
    }

    public static boolean isInvalidPosition(Position position) {
        return isInvalidPosition(position.getY(), position.getX());
    }

    public static boolean isInvalidPosition(int x, int y) {
        return !(x > 0 && y > 0 && x < 16 && y < 16);
    }

    // todo: be more precise what it means
    public static boolean isThreeByThree(Board board, Position pos, char target) {
        int result = 0;
        result += recursiveTBT(board, pos, target, 0, 0, 0);
        result += checkCenteredTBT(board, pos, target);
        return result > 1;
    }

    // todo: refactor
    public static int checkCenteredTBT(Board board, Position pos, char target) {
        int size = board.getN();
        int count = 0;
        for (int i = 0; i < DIRECTIONS.length; i += 2) {
            Position next = getNextPosition(DIRECTIONS[i], pos);
            Position prev = getNextPosition(DIRECTIONS[i + 1], pos);
            if (isInvalidPosition(next))
                continue;
            if (isInvalidPosition(prev))
                continue;
            char nextVal = board.getValue(next), prevVal = board.getValue(prev);
            if (nextVal == target && prevVal == target) {
                next = getNextPosition(DIRECTIONS[i], next);
                prev = getNextPosition(DIRECTIONS[i + 1], prev);
                if (isInvalidPosition(next))
                    continue;
                if (isInvalidPosition(prev))
                    continue;
                nextVal = board.getValue(next);
                prevVal = board.getValue(prev);
                if (nextVal == prevVal && nextVal == '+') {
                    System.out.println("FOUND");
                    count++;
                }
            }
        }
        return count;
    }

    // todo: refactor
    public static int recursiveTBT(Board board, Position position, char target, int depth, int count, int dirIdx) {
        int size = board.getN();
        if (isInvalidPosition(position)) return 0;
        char tileVal = board.getValue(position);

        if (depth == 0) {
            for (int i = 0; i < DIRECTIONS.length; i++) {
                int[] direction = DIRECTIONS[i];
                int offsetX = direction[0], offsetY = direction[1];
                Position nextPos = new Position(position.getX() + offsetX, position.getY() + offsetY);
                count += recursiveTBT(board, nextPos, target, depth + 1, count, i);
            }
            return count;
        } else if (depth < 3) {
            if (tileVal != target) {
                return 0;
            }
            int[] direction = DIRECTIONS[dirIdx];
            Position nextPos = addDirectionToPosition(position, direction);
            return recursiveTBT(board, nextPos, target, depth + 1, count, dirIdx);
        } else {
            return tileVal == '+' ? 1 : 0;
        }
    }

    private static Position getNextPosition(int[] direction, Position position) {
        return getNextPosition(direction[0], direction[1], position);
    }

    private static Position getNextPosition(int x, int y, Position position) {
        return new Position(position.getX() + x, position.getY() + y);
    }

    public static boolean isWinner(Board board, Position position) {
        int size = board.getN();
        char target = board.getValue(position);
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                int pivotX = position.getX() + i;
                int pivotY = position.getY() + j;
                if (isInvalidPosition(pivotX, pivotY))
                    continue;
                if (board.getValue(new Position(pivotX, pivotY)) != target)
                    continue;
                dirLoop:
                for (int k = 0; k < DIRECTIONS.length; k += 2) {
                    Position backward, forward;
                    backward = new Position(pivotX, pivotY);
                    forward = new Position(pivotX, pivotY);

                    for (int depth = 0; depth < 2; depth++) {
                        forward = addDirectionToPosition(forward, DIRECTIONS[k]);
                        backward = addDirectionToPosition(backward, DIRECTIONS[k + 1]);

                        boolean invalidPos = isInvalidPosition(forward, backward);
                        if (invalidPos) continue dirLoop;

                        boolean isNotTarget = board.getValue(forward) != target || board.getValue(backward) != target;
                        if (isNotTarget) continue dirLoop;
                    }

                    return true;
                }
            }
        }
        return false;
    }

    private static Position addDirectionToPosition(Position position, int[] direction) {
        int x = position.getX() + direction[0];
        int y = position.getY() + direction[1];
        return new Position(x, y);
    }

}
