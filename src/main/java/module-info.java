module iu.org.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens iu.org.tictactoe to javafx.fxml;
    exports iu.org.tictactoe;
    exports iu.org.tictactoe.model;
    opens iu.org.tictactoe.model to javafx.fxml;
    exports iu.org.tictactoe.controller;
    opens iu.org.tictactoe.controller to javafx.fxml;
}