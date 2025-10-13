package ai.neuromachines.tictactoe;

import ai.neuromachines.file.NetworkSerializer;
import ai.neuromachines.network.Network;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Optional;

import static ai.neuromachines.tictactoe.Player.O;
import static ai.neuromachines.tictactoe.Player.X;
import static java.lang.IO.println;
import static java.lang.IO.readln;

@SuppressWarnings("SameParameterValue")
public class Main {
    private static final Path path = Path.of("network.txt");  // Trained neural network file
    private final Board board = new Board();
    private final Network network;
    private Player humanPlayer = X;
    private Player aiPlayer = O;
    private Player currentPlayer = X;

    public Main() throws IOException {
        this.network = openNetworkFromFile(path);
    }

    void main() {
        boolean isHumanFirst = isHumanFirst();
        humanPlayer = isHumanFirst ? X : O;
        aiPlayer = isHumanFirst ? O : X;

        board.printBoard();
        Optional<Player> winner;
        do {
            doMove();
            board.printBoard();
            winner = board.getWinner();
        } while (winner.isEmpty() && board.hasFreeSpace());
        String gameResult = winner
                .map(player -> (player == humanPlayer) ?
                        "Congratulations, you've won!" :
                        "We're sorry, you lost")
                .orElse("The game ended in a draw");
        println(gameResult);
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
        println("AI move:");
        try {
            float[] boardState = board.getState().getNetworkInput();
            network.input(boardState);
            network.propagate();
            int move = argMax(network.output());
            board.move(aiPlayer, move);
        } catch (Exception e) {
            // incorrect move, do random move
            while (true) {
                try {
                    int move = (int) Math.round(Math.random() * 8);
                    board.move(aiPlayer, move);
                    break;
                } catch (Exception ignore) {
                }
            }
        }
    }

    private static int argMax(float[] array) {
        int maxI = 0;
        float max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                maxI = i;
            }
        }
        return maxI;
    }
}
