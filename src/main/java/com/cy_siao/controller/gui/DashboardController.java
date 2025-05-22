package com.cy_siao.controller.gui;

import com.cy_siao.dao.BedDao;
import com.cy_siao.dao.StayDao;
import com.cy_siao.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

public class DashboardController {

    @FXML
    private PieChart pieOccupation;

    @FXML
    private BarChart<String, Number> barDepartures;
    private ViewManager viewManager;

    @FXML
    public void initialize() {
        StayDao stayDao = new StayDao();
        BedDao bedDao = new BedDao();

        int totalLits = bedDao.countTotalBeds();
        int litsOccupés = stayDao.countActiveStays();

        pieOccupation.getData().addAll(
                new PieChart.Data("Occupés", litsOccupés),
                new PieChart.Data("Libres", totalLits - litsOccupés)
        );

        int partis = stayDao.countDeparted();
        int enCours = stayDao.countOngoing();
        int enRetard = stayDao.countOverdue();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.getData().add(new XYChart.Data<>("Sortis", partis));
        serie.getData().add(new XYChart.Data<>("Actifs", enCours));
        serie.getData().add(new XYChart.Data<>("En retard", enRetard));

        barDepartures.getData().add(serie);
    }
    public void setViewManager(ViewManager viewManager){
        this.viewManager = viewManager;
    }

    private void handleBackButton(){
        this.viewManager.showMainMenu();
    }
}