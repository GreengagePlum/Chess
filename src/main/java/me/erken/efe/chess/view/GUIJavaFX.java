package me.erken.efe.chess.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import me.erken.efe.chess.controller.GameController;

public class GUIJavaFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/view.fxml"));
        Pane root = loader.load();

        GameController controller = loader.getController();
        controller.setUpController();

        Scene scene = new Scene(root, 710, 710);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Chess");
        primaryStage.show();
    }
}
