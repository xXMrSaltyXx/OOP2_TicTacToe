package iu.org.tictactoe.model;

public class Board {
    private final Player[][] grid;

    public Board() {
        grid = new Player[3][3];
        reset();
    }
    
    // Copy constructor to create a deep copy of the board
    public Board(Board other) {
        grid = new Player[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(other.grid[i], 0, grid[i], 0, 3);
        }
    }

    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = Player.EMPTY;
            }
        }
    }

    public boolean makeMove(int row, int col, Player player) {
        if (grid[row][col] == Player.EMPTY) {
            grid[row][col] = player;
            return true;
        }
        return false;
    }

    public Player getCell(int row, int col) {
        return grid[row][col];
    }

    public Player checkWinner() {
        // Horizontale Prüfung
        for (int row = 0; row < 3; row++) {
            if (grid[row][0] != Player.EMPTY &&
                    grid[row][0] == grid[row][1] &&
                    grid[row][1] == grid[row][2]) {
                return grid[row][0];
            }
        }

        // Vertikale Prüfung
        for (int col = 0; col < 3; col++) {
            if (grid[0][col] != Player.EMPTY &&
                    grid[0][col] == grid[1][col] &&
                    grid[1][col] == grid[2][col]) {
                return grid[0][col];
            }
        }

        // Diagonale Prüfung
        if (grid[0][0] != Player.EMPTY &&
                grid[0][0] == grid[1][1] &&
                grid[1][1] == grid[2][2]) {
            return grid[0][0];
        }

        if (grid[0][2] != Player.EMPTY &&
                grid[0][2] == grid[1][1] &&
                grid[1][1] == grid[2][0]) {
            return grid[0][2];
        }

        // Prüfen auf Unentschieden
        boolean isFull = true;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (grid[row][col] == Player.EMPTY) {
                    isFull = false;
                    break;
                }
            }
            if (!isFull) break;
        }

        return isFull ? Player.EMPTY : null;
    }
}