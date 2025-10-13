package ai.neuromachines.tictactoe;

import ai.neuromachines.Assert;
import lombok.Getter;

import java.util.Optional;

import static ai.neuromachines.tictactoe.State.*;
import static java.lang.IO.print;
import static java.lang.IO.println;

@Getter
public class Board {

    private final static int[][] WIN_CELLS = new int[][]{
            new int[]{0, 1, 2},  // 1-st row
            new int[]{3, 4, 5},  // 2-nd row
            new int[]{6, 7, 8},  // 3-d  row
            new int[]{0, 3, 6},  // 1-st col
            new int[]{1, 4, 7},  // 2-nd col
            new int[]{2, 5, 8},  // 3-d  col
            new int[]{0, 4, 8},  // 1-st diagonal
            new int[]{2, 4, 6}}; // 2-nd diagonal

    private final BoardState state = new BoardState();


    public void printBoard() {
        println();
        for (int i = 0; i < 3; i++) {
            printRow(i);
            printRowDelimiter(i);
        }
        println();
    }

    private void printRow(int row) {
        for (int i = 0; i < 3; i++) {
            print("| ");
            print(state.get(row * 3 + i));
            print(" ");
        }
        println("|");
    }

    private void printRowDelimiter(int row) {
        if (row < 2) {
            println("-------------");
        }
    }

    public void move(Player player, int cell) {
        Assert.isTrue(cell >= 0 && cell <= 8, "Incorrect move: " + cell);
        Assert.isTrue(state.get(cell) == BLANK, "Space is occupied: " + cell);
        state.set(cell, player.toState());
    }

    /**
     * @return X or O, otherwise empty optional
     */
    public Optional<Player> getWinner() {
        for (int[] cells : WIN_CELLS) {
            boolean oWin = true;
            boolean xWin = true;
            for (int c : cells) {
                State s = state.get(c);
                if (s == BLANK) {
                    oWin = false;
                    xWin = false;
                    break;
                } else if (s == X) {
                    oWin = false;
                } else if (s == O) {
                    xWin = false;
                }
            }
            if (oWin) {
                return Optional.of(Player.O);
            } else if (xWin) {
                return Optional.of(Player.X);
            }
        }
        return Optional.empty();
    }

    public boolean hasFreeSpace() {
        return state.hasFreeSpace();
    }
}
