package com.yeyoan.blackjack.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Sandbox extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setMaximized(true);

        Parent root = FXMLLoader.load(Sandbox.class.getResource("/fxml/blackjack.fxml"));

        Scene scene = new Scene(root, 300, 200);
        scene.getStylesheets().add(Sandbox.class.getResource("/css/light.css").toExternalForm());

        stage.setTitle("Blackjack");
        stage.setScene(scene);
        stage.show();
    }
} 
