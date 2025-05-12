package iu.org.tictactoe.model;

public enum Player {
    HUMAN('X'), AI('O'), EMPTY(' ');

    private final char symbol;

    Player(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
