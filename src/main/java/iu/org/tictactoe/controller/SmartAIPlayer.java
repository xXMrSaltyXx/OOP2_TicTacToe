package iu.org.tictactoe.controller;

import iu.org.tictactoe.model.Board;
import iu.org.tictactoe.model.Player;

public class SmartAIPlayer {

    // Hauptmethode für den KI-Zug
    public int[] getMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        // Alle möglichen Züge ausprobieren
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board.getCell(row, col) == Player.EMPTY) {
                    // Kopie des Boards erstellen für die Simulation
                    Board boardCopy = new Board(board);
                    
                    // Temporären Zug auf der Kopie ausführen
                    boardCopy.makeMove(row, col, Player.AI);

                    // Minimax-Wert für diesen Zug berechnen
                    int score = minimax(boardCopy, 0, false);

                    // Wenn dieser Zug besser ist als der bisher beste
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }

        return bestMove;
    }

    // Minimax-Algorithmus zur Bewertung der Züge
    private int minimax(Board board, int depth, boolean isMaximizing) {
        // Prüfen, ob das Spiel vorbei ist
        Player result = board.checkWinner();

        // Bewertung des Endzustands
        if (result == Player.AI) return 10 - depth; // KI gewinnt
        if (result == Player.HUMAN) return depth - 10; // Mensch gewinnt
        if (result == Player.EMPTY) return 0; // Unentschieden

        if (isMaximizing) {
            // KI maximiert den Score
            int bestScore = Integer.MIN_VALUE;

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board.getCell(row, col) == Player.EMPTY) {
                        // Zug auf dem übergebenen Board ausführen
                        board.makeMove(row, col, Player.AI);
                        int score = minimax(board, depth + 1, false);
                        // Zug rückgängig machen
                        board.makeMove(row, col, Player.EMPTY);
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }

            return bestScore;
        } else {
            // Mensch minimiert den Score
            int bestScore = Integer.MAX_VALUE;

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board.getCell(row, col) == Player.EMPTY) {
                        board.makeMove(row, col, Player.HUMAN);
                        int score = minimax(board, depth + 1, true);
                        board.makeMove(row, col, Player.EMPTY);
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }

            return bestScore;
        }
    }
}

