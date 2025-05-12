# TicTacToe mit Minimax-KI
## Java-Implementierung eines klassischen Spiels

---

## Das Spiel im Überblick

Bei diesem Projekt handelt es sich um ein Tic-Tac-Toe Spiel, das in Java mit JavaFX entwickelt wurde. Das Besondere daran ist, dass es zwei verschiedene KI-Gegner bietet:

1. **Simple AI**: Macht zufällige Züge ohne Strategie
2. **Smart AI**: Verwendet den Minimax-Algorithmus für optimale Entscheidungen

## Spielfunktionen

- Spieler verwendet X, die KI verwendet O
- Man kann zwischen einfacher und smarter KI umschalten
- Das Programm erkennt automatisch, wenn jemand gewonnen hat oder es unentschieden steht
- Nach Spielende startet automatisch eine neue Runde

## Minimax-Algorithmus - Grundprinzip

Der in der Smart AI verwendete Minimax-Algorithmus ist eine wichtige Technik im Bereich der Spieltheorie und künstlichen Intelligenz.

### Funktionsweise:

Der Algorithmus simuliert alle möglichen Spielverläufe und bewertet sie, um den optimalen Zug zu finden.

1. **Simulation**: Die KI probiert jeden möglichen Zug aus
2. **Bewertung**: Die Endergebnisse werden bewertet:
   - KI gewinnt: positiv (+10)
   - Mensch gewinnt: negativ (-10)
   - Unentschieden: neutral (0)
3. **Auswahl**: Die KI wählt den Zug, der zum besten Ergebnis führt

Der Algorithmus wechselt zwischen Maximieren (für die KI) und Minimieren (für den menschlichen Spieler), daher der Name "Minimax".

### Vorteile bei Tic-Tac-Toe

- Eine perfekte Minimax-KI kann nicht verlieren
- Sie erkennt und blockiert die Gewinnzüge des Gegners
- In Tic-Tac-Toe kann die KI den gesamten Spielbaum berechnen

## Programmstruktur

Das Programm verwendet eine einfache MVC-Struktur:

- **Model**: Board und Player-Klassen für den Spielzustand
- **Controller**: AIPlayer und SmartAIPlayer für die Spiellogik
- **View**: TicTacToeGameFX für die grafische Oberfläche

## Kerncode der Smart-AI

```java
// Hier ist der Hauptcode des Minimax-Algorithmus
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
        
        // Alle möglichen Züge durchgehen
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board.getCell(row, col) == Player.EMPTY) {
                    board.makeMove(row, col, Player.AI);
                    int score = minimax(board, depth + 1, false);
                    board.makeMove(row, col, Player.EMPTY);
                    bestScore = Math.max(score, bestScore);
                }
            }
        }
        
        return bestScore;
    } else {
        // Mensch minimiert den Score
        int bestScore = Integer.MAX_VALUE;
        
        // Alle möglichen Züge durchgehen
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
```

## Anwendung starten

Um das Spiel zu starten, führen Sie folgenden Befehl aus:

```bash
./gradlew run
```

Tipp: Versuchen Sie, gegen die Smart-AI zumindest ein Unentschieden zu erreichen!