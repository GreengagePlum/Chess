module me.erken.efe.chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens me.erken.efe.chess.view to javafx.fxml, javafx.graphics;
    opens me.erken.efe.chess.controller to javafx.fxml, javafx.graphics;

    exports me.erken.efe.chess.main;
    exports me.erken.efe.chess.controller;
    exports me.erken.efe.chess.view;
    exports me.erken.efe.chess.model;
}