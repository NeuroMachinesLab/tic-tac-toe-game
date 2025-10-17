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
    private final Board board = new Board();
    private Network network;
    private Player humanPlayer = X;
    private Player aiPlayer = O;
    private Player currentPlayer = X;

    void main()  throws IOException {
        String level = chooseGameDifficulty();
        network = openNetworkFromFile("network-" + level + ".txt"); // Trained neural network file

        boolean isHumanFirst = isHumanFirst();
        humanPlayer = isHumanFirst ? X : O;
        aiPlayer = isHumanFirst ? O : X;

        printEmptyBoard();
        Optional<Player> winner;
        do {
            doMove();
            board.printBoard();
            winner = board.getWinner();
        } while (winner.isEmpty() && board.hasFreeSpace());
        String gameResult = winner
                .map(player -> (player == humanPlayer) ?
                        "Congratulations, you've won!" :
                        "Good game, but you lost")
                .orElse("The game ended in a draw");
        println(gameResult);
    }

    private static Network openNetworkFromFile(String fileName) throws IOException {
        Path path = Path.of(fileName);
        try (FileChannel ch = FileChannel.open(path)) {
            return NetworkSerializer.deserialize(ch);
        }
    }

    private static String chooseGameDifficulty() {
        String[] levelNames = new String[]{"easy", "medium", "hard"};
        do {
            try {
                println();
                println("1) Easy");
                println("2) Medium");
                println("3) Hard");
                String answer = readln("Choose Difficulty?  [1-3]: ");
                int level = Integer.parseInt(answer);
                if (level >= 1 && level <= 3) {
                    return levelNames[level - 1];
                }
            } catch (Exception ignore) {
            }
        } while (true);
    }

    private static boolean isHumanFirst() {
        do {
            try {
                println();
                println("1) X player");
                println("2) O player");
                String answer = readln("Select a player [1-2]: ");
                int player = Integer.parseInt(answer);
                if (player == 1) {
                    return true;
                } else if (player == 2) {
                    return false;
                }
            } catch (Exception ignore) {
            }
        } while (true);
    }

    private static void printEmptyBoard() {
        println();
        println("| 1 | 2 | 3 |");
        println("-------------");
        println("| 4 | 5 | 6 |");
        println("-------------");
        println("| 7 | 8 | 9 |");
        println();
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
        int move;
        try {
            float[] boardState = board.getState().getNetworkInput();
            network.input(boardState);
            network.propagate();
            move = argMax(network.output());
            board.move(aiPlayer, move);
        } catch (Exception e) {
            // incorrect move, do random move
            while (true) {
                try {
                    move = (int) Math.round(Math.random() * 8.5 - 0.5);
                    board.move(aiPlayer, move);
                    break;
                } catch (Exception ignore) {
                }
            }
        }
        println("AI move: " + (move + 1));
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
