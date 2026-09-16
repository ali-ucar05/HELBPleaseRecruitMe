package com.example;

import javafx.application.Application;
import javafx.stage.Stage;

// Cette classe représente le point d’entrée du programme.
// Elle initialise le contrôleur et configure les éléments nécessaires
// au lancement de l’application JavaFX.
//
// C’est dans cette classe que l’application est démarrée.

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        HELBPleaseRecruitMeController Controller = new HELBPleaseRecruitMeController(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
