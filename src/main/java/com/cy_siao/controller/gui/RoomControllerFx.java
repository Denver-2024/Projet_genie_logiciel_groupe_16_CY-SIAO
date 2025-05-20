package com.cy_siao.controller.gui;

import com.cy_siao.model.RestrictionType;
import com.cy_siao.model.Room;
import com.cy_siao.service.RoomService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RoomControllerFx implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private Spinner<Integer> nbBedsMaxSpinner;

    @FXML
    private TextField restrictionField;

    @FXML
    private TableView<Room> roomTableView;

    @FXML
    private TableColumn<Room, Long> idCol;

    @FXML
    private TableColumn<Room, String> nameCol;

    @FXML
    private TableColumn<Room, Integer> nbBedsMaxCol;

    @FXML
    private TableColumn<Room, String> restrictionCol;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    private ObservableList<Room> roomList = FXCollections.observableArrayList();
    private RoomService roomService = new RoomService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configure spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        nbBedsMaxSpinner.setValueFactory(valueFactory);

        // Load rooms from service
        roomList = FXCollections.observableArrayList(roomService.getAllRooms());
        roomTableView.setItems(roomList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nbBedsMaxCol.setCellValueFactory(new PropertyValueFactory<>("nbBedsMax"));
        restrictionCol.setCellValueFactory(cellData -> {
            List<RestrictionType> restrictions = cellData.getValue().getRestrictions();
            String joined = restrictions.stream()
                    .map(RestrictionType::getLabel)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(joined);
        });


        addButton.setOnAction(e -> handleAddRoom());
        deleteButton.setOnAction(e -> handleDeleteRoom());
    }

    private void handleAddRoom() {
        String name = nameField.getText();
        int nbBedsMax = nbBedsMaxSpinner.getValue();
        String restriction = restrictionField.getText();

        if (name.isEmpty()) {
            showAlert("Name is required.");
            return;
        }

        Room room = new Room(name, nbBedsMax);
        roomService.createRoom(room);
        roomList.add(room);

        nameField.clear();
        restrictionField.clear();
        nbBedsMaxSpinner.getValueFactory().setValue(1);
    }

    private void handleDeleteRoom() {
        Room selectedRoom = roomTableView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            roomTableView.getSelectionModel().clearSelection();
            roomService.deleteRoom(selectedRoom.getId());
            roomList.remove(selectedRoom);
        } else {
            showAlert("Please select a room to delete");
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
