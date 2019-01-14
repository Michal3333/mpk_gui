package com.snoreware.mpk;

import com.snoreware.mpk.guiControllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MpkApplication  extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml"));
        MenuController.stage = primaryStage;
        primaryStage.setTitle("mpk");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}

