package ai.neuromachines.tictactoe;

import java.util.Arrays;

import static ai.neuromachines.tictactoe.State.BLANK;

public class BoardState {
    private final State[] state = new State[9];

    public BoardState() {
        Arrays.fill(state, BLANK);
    }

    public State get(int i) {
        return state[i];
    }

    public void set(int i, State state) {
        this.state[i] = state;
    }

    public boolean hasFreeSpace() {
        return Arrays.stream(state)
                .anyMatch(s -> s == BLANK);
    }

    public float[] getNetworkInput() {
        float[] input = new float[state.length];
        int i = 0;
        for (State s : state) {
            input[i++] = s.getState();
        }
        return input;
    }
}
