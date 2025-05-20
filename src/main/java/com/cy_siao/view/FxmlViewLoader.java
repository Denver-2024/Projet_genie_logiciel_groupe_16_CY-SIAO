package com.cy_siao.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;

public class FxmlViewLoader {
    
    /**
     * Charge une vue FXML avec son contrôleur
     * @param fxmlPath Chemin relatif vers le fichier FXML (depuis resources)
     * @param controller Instance du contrôleur à injecter
     * @return La vue chargée
     * @throws RuntimeException Si le chargement échoue
     */
    public static Parent load(String fxmlPath, Object controller) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(controller);
            URL fxmlUrl = FxmlViewLoader.class.getResource(fxmlPath);
            if (fxmlUrl == null) {
                throw new IOException("FXML file not found: " + fxmlPath);
            }
            loader.setLocation(fxmlUrl);
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML view: " + fxmlPath, e);
        }
    }

    /**
     * Charge une vue FXML sans contrôleur pré-défini
     * @param fxmlPath Chemin relatif vers le fichier FXML
     * @return Pair contenant la vue et le contrôleur instancié
     */
    public static ViewControllerPair load(String fxmlPath) {
        try {
            URL fxmlUrl = FxmlViewLoader.class.getResource(fxmlPath);
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent view = loader.load();
            Object controller = loader.getController();
            return new ViewControllerPair(view, controller);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML view: " + fxmlPath, e);
        }
    }

    // Classe conteneur pour retourner view + controller
    public static class ViewControllerPair {
        public final Parent view;
        public final Object controller;

        public ViewControllerPair(Parent view, Object controller) {
            this.view = view;
            this.controller = controller;
        }
    }
}