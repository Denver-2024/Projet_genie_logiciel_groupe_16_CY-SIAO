package com.cy_siao;

import com.cy_siao.controller.cli.CLIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;

import com.cy_siao.view.ViewManager;

import java.sql.SQLException;

/**
 * Class where we start the application
 * can choose between cli or gui interface in arg when we launch
 */
public class App extends Application {

    private static boolean launchCLI = false;

    /**
     * Main method of the app
     * @param args cli or gui for have command line interface or graphical user interface 
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        CLIController cliController = new CLIController();
        // Analyse des arguments
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "cli":
                    launchCLI = true;
                    cliController.start();
                    break;
                case "gui":
                    launch(args); // Lancer JavaFX normalement
                    break;
                default:
                    System.out.println("⚠️ Argument inconnu : " + args[0] + " → Lancement du mode graphique par défaut.");
                    launch(args);
                    break;
            }
        } else {
            // Aucun argument → mode graphique
            launch(args);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        if (!launchCLI) {
            new ViewManager(primaryStage);
            primaryStage.show();
        }
    }
}
