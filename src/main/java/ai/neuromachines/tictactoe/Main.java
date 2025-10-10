package ai.neuromachines.tictactoe;

import ai.neuromachines.file.NetworkSerializer;
import ai.neuromachines.network.Network;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

import static ai.neuromachines.tictactoe.State.*;
import static java.lang.IO.println;
import static java.lang.IO.readln;

@SuppressWarnings("SameParameterValue")
public class Main {
    private static final Path path = Path.of("network.txt");  // Trained neural network file
    private final Board board = new Board();
    private final Network network;
    private State humanPlayer = X;
    private State aiPlayer = O;
    private State currentPlayer = X;

    public Main() throws IOException {
        this.network = openNetworkFromFile(path);
    }

    void main() {
        boolean isHumanFirst = isHumanFirst();
        humanPlayer = isHumanFirst ? X : O;
        aiPlayer = isHumanFirst ? O : X;

        State winner;
        do {
            board.printBoard();
            doMove();
            winner = board.getWinner();
        } while (winner == BLANK);
        if (winner == humanPlayer) {
            println("Congratulations, you've won!");
        } else {
            println("We're sorry, you lost");
        }
    }

    private static Network openNetworkFromFile(Path path) throws IOException {
        try (FileChannel ch = FileChannel.open(path)) {
            return NetworkSerializer.deserialize(ch);
        }
    }

    private static boolean isHumanFirst() {
        do {
            try {
                println("0) O player");
                println("1) X player");
                String answer = readln("Select a player [0-1]: ");
                int player = Integer.parseInt(answer);
                if (player == 0) {
                    return false;
                } else if (player == 1) {
                    return true;
                }
            } catch (Exception ignore) {
            }
        } while (true);
    }

    private void doMove() {
        if (currentPlayer == humanPlayer) {
            doHumanMove();
        } else {
            doAiMove();
        }
        currentPlayer = (currentPlayer == X) ? O : X;
    }

    private void doHumanMove() {
        do {
            try {
                String answer = readln("Move with '" + humanPlayer + "' to space [1-9]: ");
                int space = Integer.parseInt(answer);
                board.move(humanPlayer, space - 1);
                return;
            } catch (Exception ignore) {
            }
        } while (true);
    }

    private void doAiMove() {
        float[] boardState = board.getState().getNetworkInput();
        network.input(boardState);
        network.propagate();
        int move = Math.round(network.output()[0] * 10);  // network generates output values in 0..0.8 interval
        board.move(aiPlayer, move);
    }
}
