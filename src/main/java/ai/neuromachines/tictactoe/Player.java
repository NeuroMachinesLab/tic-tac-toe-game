package ai.neuromachines.tictactoe;

public enum Player {
    X, O;

    public State toState() {
        return (this == X) ? State.X : State.O;
    }
}
