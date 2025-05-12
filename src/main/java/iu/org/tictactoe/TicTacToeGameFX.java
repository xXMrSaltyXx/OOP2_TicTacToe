package iu.org.tictactoe;

import iu.org.tictactoe.controller.AIPlayer;
import iu.org.tictactoe.controller.SmartAIPlayer;
import iu.org.tictactoe.model.Board;
import iu.org.tictactoe.model.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class TicTacToeGameFX extends Application {
    private Board board;
    private AIPlayer simpleAiPlayer;
    private SmartAIPlayer smartAiPlayer;
    private Button[][] buttons;
    private Label statusLabel;
    private boolean isSmartAI = true; // true für SmartAI, false für einfache KI

    @Override
    public void start(Stage primaryStage) {
        board = new Board();
        simpleAiPlayer = new AIPlayer();
        smartAiPlayer = new SmartAIPlayer();

        // Root-Layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Status-Label
        statusLabel = new Label("Dein Zug! (X)");
        statusLabel.setFont(Font.font(16));
        HBox topBox = new HBox(statusLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(0, 0, 15, 0));
        root.setTop(topBox);

        // Spielfeld-Grid
        GridPane gameGrid = new GridPane();
        gameGrid.setAlignment(Pos.CENTER);
        gameGrid.setHgap(10);
        gameGrid.setVgap(10);

        buttons = new Button[3][3];

        // Buttons initialisieren
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button(" ");
                button.setFont(Font.font(24));
                button.setPrefSize(100, 100);

                final int finalRow = row;
                final int finalCol = col;

                button.setOnAction( _ -> {
                    if (board.makeMove(finalRow, finalCol, Player.HUMAN)) {
                        updateButton(finalRow, finalCol);
                        checkGameState();

                        // KI-Zug
                        if (board.checkWinner() == null) {
                            statusLabel.setText("KI denkt nach...");

                            // KI-Zug mit einer kurzen Verzögerung
                            new Thread(() -> {
                                try {
                                    Thread.sleep(500); // Kurze Verzögerung für bessere UX
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                javafx.application.Platform.runLater(() -> {
                                    aiTurn();
                                    statusLabel.setText("Dein Zug! (X)");
                                });
                            }).start();
                        }
                    }
                });

                buttons[row][col] = button;
                gameGrid.add(button, col, row);
            }
        }

        root.setCenter(gameGrid);

        // Reset-Button
        Button resetButton = new Button("Neues Spiel");
        resetButton.setFont(Font.font(14));
        resetButton.setOnAction(_ -> resetGame());
        
        // KI-Modus Toggle
        ToggleButton aiToggleButton = new ToggleButton(isSmartAI ? "Smart AI" : "Simple AI");
        aiToggleButton.setFont(Font.font(14));
        aiToggleButton.setSelected(isSmartAI);
        aiToggleButton.setOnAction(_ -> {
            isSmartAI = aiToggleButton.isSelected();
            aiToggleButton.setText(isSmartAI ? "Smart AI" : "Simple AI");
        });

        HBox bottomBox = new HBox(10, resetButton, aiToggleButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(15, 0, 0, 0));
        root.setBottom(bottomBox);

        // Scene erstellen
        Scene scene = new Scene(root, 400, 450);
        try {
            // Methode 1: Über ClassLoader
            String css = Objects.requireNonNull(TicTacToeGameFX.class.getClassLoader().getResource("styles.css")).toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e1) {
            try {
                // Methode 2: Über absoluten Pfad
                String css = Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm();
                scene.getStylesheets().add(css);
            } catch (Exception e2) {
                try {
                    // Methode 3: Über relativen Pfad
                    String css = Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm();
                    scene.getStylesheets().add(css);
                } catch (Exception e3) {
                    System.out.println("CSS konnte nicht geladen werden. Verwende Standard-Styling.");
                }
            }
        }

        // Bühne einrichten
        primaryStage.setTitle("TicTacToe mit JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void aiTurn() {
        int[] move;
        if (isSmartAI) {
            // SmartAI-Zug
            move = smartAiPlayer.getMove(board);
        } else {
            // Einfache KI-Zug
            move = simpleAiPlayer.getMove(board);
        }
        board.makeMove(move[0], move[1], Player.AI);
        updateButton(move[0], move[1]);
        checkGameState();
    }

    private void updateButton(int row, int col) {
        Player player = board.getCell(row, col);
        buttons[row][col].setText(player.toString());

        // CSS-Klassen für Styling
        if (player == Player.HUMAN) {
            buttons[row][col].getStyleClass().add("x-move");
        } else if (player == Player.AI) {
            buttons[row][col].getStyleClass().add("o-move");
        }

        buttons[row][col].setDisable(player != Player.EMPTY);
    }

    private void checkGameState() {
        Player winner = board.checkWinner();
        if (winner != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spielende");

            if (winner == Player.HUMAN) {
                alert.setHeaderText("Glückwunsch!");
                alert.setContentText("Du hast gewonnen!");
            } else if (winner == Player.AI) {
                alert.setHeaderText("Schade!");
                alert.setContentText("Die KI hat gewonnen!");
            } else {
                alert.setHeaderText("Unentschieden!");
                alert.setContentText("Das Spiel endet unentschieden!");
            }

            alert.showAndWait();

            // Spiel zurücksetzen
            resetGame();
        }
    }

    private void resetGame() {
        board.reset();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(" ");
                buttons[row][col].setDisable(false);
                buttons[row][col].getStyleClass().removeAll("x-move", "o-move");
            }
        }
        statusLabel.setText("Dein Zug! (X)");
    }

    public static void main(String[] args) {
        launch(args);
    }
}