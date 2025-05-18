package com.cy_siao;

import javafx.application.Application;
import com.cy_siao.view.GuiView;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        GuiView view = new GuiView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}