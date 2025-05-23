package com.cy_siao.controller.gui;

import com.cy_siao.dao.BedDao;
import com.cy_siao.dao.StayDao;
import com.cy_siao.view.ViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;

public class DashboardController {

    @FXML
    private PieChart pieOccupation;

    @FXML
    private BarChart<String, Number> barDepartures;

    @FXML
    private Button backButton;

    private ViewManager viewManager;

    @FXML
    public void initialize() {
        StayDao stayDao = new StayDao();
        BedDao bedDao = new BedDao();

        int totalLits = bedDao.countTotalBeds();
        int litsOccupés = stayDao.countActiveStays();

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Occupés", litsOccupés),
                new PieChart.Data("Libres", totalLits - litsOccupés)
        );

        pieOccupation.setData(pieData);

        // Ajoute les valeurs sur les parts
        pieData.forEach(data -> {
            String label = String.format("%.0f", data.getPieValue());
            Tooltip tooltip = new Tooltip(data.getName() + ": " + label);
            Tooltip.install(data.getNode(), tooltip);

            // Ajout d'un label sur la part après que le nœud soit bien initialisé
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    StackPane stack = (StackPane) newNode;
                    Label valueLabel = new Label(label);
                    valueLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
                    stack.getChildren().add(valueLabel);
                }
            });
        });

        int partis = stayDao.countDeparted();
        int enCours = stayDao.countOngoing();
        int enRetard = stayDao.countOverdue();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.getData().add(new XYChart.Data<>("Sortis", partis));
        serie.getData().add(new XYChart.Data<>("Actifs", enCours));
        serie.getData().add(new XYChart.Data<>("En retard", enRetard));

        barDepartures.getData().add(serie);

        backButton.setOnAction(e -> handleBackButton());
    }

    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    private void handleBackButton() {
        this.viewManager.showMainMenu();
    }
}
