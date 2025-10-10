package ai.neuromachines.tictactoe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum State {
    BLANK(-1),
    O(0),
    X(1);

    private final int state;

    @Override
    public String toString() {
        return (this == BLANK) ? " " : name();
    }
}
