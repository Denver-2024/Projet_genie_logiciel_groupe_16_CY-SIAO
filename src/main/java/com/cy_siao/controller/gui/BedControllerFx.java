package com.cy_siao.controller.gui;

import com.cy_siao.model.Bed;
import com.cy_siao.service.BedService;
import com.cy_siao.view.ViewManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BedControllerFx implements Initializable {

    private ViewManager viewManager;

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

    @FXML
    private Button backButton;

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
        backButton.setOnAction(e -> handleBackButton());
    }

    public void setViewManager(ViewManager viewManager){
        this.viewManager = viewManager;
    }

    private void handleBackButton(){
        this.viewManager.showMainMenu();
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
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox getView() {
        // Initialisation des composants s'ils ne sont pas instanciés par FXML
        if (idRoomField == null) idRoomField = new TextField();
        if (isDoubleCheckBox == null) isDoubleCheckBox = new CheckBox();
        if (addButton == null) addButton = new Button("Add Bed");
        if (deleteButton == null) deleteButton = new Button("Delete Bed");
        if (backButton == null) backButton = new Button("Back");

        if (bedTableView == null) {
            bedTableView = new TableView<>();
            idCol = new TableColumn<>("ID");
            idRoomCol = new TableColumn<>("ID Room");
            isDoubleCol = new TableColumn<>("Nb Places");

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            idRoomCol.setCellValueFactory(new PropertyValueFactory<>("idRoom"));
            isDoubleCol.setCellValueFactory(new PropertyValueFactory<>("nbPlaces"));

            bedTableView.getColumns().addAll(idCol, idRoomCol, isDoubleCol);
            bedTableView.setItems(bedList);
        }

        // Button Listeners
        addButton.setOnAction(e -> handleAddBed());
        deleteButton.setOnAction(e -> handleDeleteBed());
        backButton.setOnAction(e -> handleBackButton());

        // Disposition
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(20));
        layout.getChildren().addAll(
                new Label("Room ID :"), idRoomField,
                new Label("Is the Bed Double ?"), isDoubleCheckBox,
                addButton, deleteButton, backButton,
                bedTableView
        );

        return layout;
    }
}
