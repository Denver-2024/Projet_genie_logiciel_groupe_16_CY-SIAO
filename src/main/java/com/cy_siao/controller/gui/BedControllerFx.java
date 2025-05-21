package com.cy_siao.controller.gui;

import com.cy_siao.model.Bed;
import com.cy_siao.service.BedService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class BedControllerFx implements Initializable {

    @FXML
    private TextField idRoomField;

    @FXML
    private CheckBox isDoubleCheckBox;

    @FXML
    private TableView<Bed> bedTableView;

    @FXML
    private TableColumn<Bed, Long> idCol;

    @FXML
    private TableColumn<Bed, Long> idRoomCol;

    @FXML
    private TableColumn<Bed, Long> isDoubleCol;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    private ObservableList<Bed> bedList = FXCollections.observableArrayList();
    private BedService bedService = new BedService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bedList = FXCollections.observableArrayList(bedService.getAllBeds());
        bedTableView.setItems(bedList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idRoomCol.setCellValueFactory(new PropertyValueFactory<>("idRoom"));
        isDoubleCol.setCellValueFactory(new PropertyValueFactory<>("nbPlace"));

        addButton.setOnAction(e -> handleAddBed());
        deleteButton.setOnAction(e -> handleDeleteBed());
    }

    private void handleAddBed() {
        try {
            int idRoom = Integer.parseInt(idRoomField.getText());
            boolean isDouble = isDoubleCheckBox.isSelected();

            Bed bed = new Bed(idRoom, isDouble);
            bedList.add(bed);
            bedService.createBed(bed);

            idRoomField.clear();
            isDoubleCheckBox.setSelected(false);
        } catch (NumberFormatException e) {
            showAlert("Invalid Room ID");
        }
    }

    private void handleDeleteBed() {
        Bed selectedBed = bedTableView.getSelectionModel().getSelectedItem();
        if (selectedBed != null) {
            bedTableView.getSelectionModel().clearSelection();
            bedList.remove(selectedBed);
            bedService.deleteBed(selectedBed.getId());
        } else {
            showAlert("Please select a bed to delete");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
