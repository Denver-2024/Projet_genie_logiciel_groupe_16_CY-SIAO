package com.cy_siao;

import javafx.application.Application;
import com.cy_siao.view.MainMenuView;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainMenuView mainMenu = new MainMenuView();
        mainMenu.showInStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}