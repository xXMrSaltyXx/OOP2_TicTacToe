package iu.org.tictactoe.controller;

import iu.org.tictactoe.model.Board;
import iu.org.tictactoe.model.Player;

import java.util.Random;

public class AIPlayer {
    private final Random random = new Random();

    public int[] getMove(Board board) {
        // Einfache KI: Zufällige Züge
        while (true) {
            int row = random.nextInt(3);
            int col = random.nextInt(3);
            if (board.getCell(row, col) == Player.EMPTY) {
                return new int[]{row, col};
            }
        }
    }
}
