package com.cy_siao.controller.gui;

import com.cy_siao.dao.RestrictionTypeDao;
import com.cy_siao.model.RestrictionType;
import com.cy_siao.model.Room;
import com.cy_siao.model.person.Gender;
import com.cy_siao.service.RoomService;
import com.cy_siao.view.ViewManager;

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

/**
 * Controller class for managing rooms in the JavaFX UI.
 * Handles room creation, updating, deletion and restriction management.
 */
public class RoomControllerFx implements Initializable {

    // View manager for screen navigation
    private ViewManager viewManager;

    @FXML
    private TextField nameField1; // Room name

    @FXML
    private Spinner<Integer> nbBedsMaxSpinner; // Max number of beds spinner

    @FXML
    private TextField nameField2; // Restriction name

    @FXML
    private ComboBox<Gender> genderRestriction; // Gender restriction dropdown

    @FXML
    private TextField minAgeField; // Min age restriction field

    @FXML
    private TextField maxAgeField; // Max age restriction field

    @FXML
    private Button addButton; // Add room button

    @FXML
    private Button deleteButton; // Delete room button

    @FXML
    private Button addRestrictionButton; // Add restriction button

    @FXML
    private Button updateButton; // Update room button

    @FXML
    private Button backButton; // Back navigation button

    @FXML
    private ComboBox<String> logicOperator; // Logic operator dropdown

    @FXML
    private TableView<Room> roomTableView; // Room table view

    @FXML
    private TableColumn<Room, Long> idCol; // Room ID column

    @FXML
    private TableColumn<Room, String> nameCol; // Room name column

    @FXML
    private TableColumn<Room, Integer> nbBedsMaxCol; // Max beds column

    @FXML
    private TableColumn<Room, String> restrictionCol; // Restrictions column

    private ObservableList<Room> roomList = FXCollections.observableArrayList(); // Observable room list
    private final RoomService roomService = new RoomService(); // Room service for data operations

    /**
     * Initializes the controller class. Sets up UI controls and loads initial data.
     *
     * @param location  The location used to resolve relative paths
     * @param resources The resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Init Spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50,1);
        nbBedsMaxSpinner.setValueFactory(valueFactory);

        // Init ComboBox
        genderRestriction.setItems(FXCollections.observableArrayList(Gender.values()));
        logicOperator.setItems(FXCollections.observableArrayList("AND", "OR"));

        // Load Rooms
        List<Room> allRooms = roomService.getAllRooms();
        roomService.connectBedToRoom(allRooms);
        roomList.setAll(allRooms);
        roomTableView.setItems(roomList);

        // Set Table Columns
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nbBedsMaxCol.setCellValueFactory(new PropertyValueFactory<>("nbBedsMax"));
        restrictionCol.setCellValueFactory(cellData -> {
            List<RestrictionType> restrictions = cellData.getValue().getRestrictions();
            String joined = restrictions.stream().map(RestrictionType::getLabel).collect(Collectors.joining(", "));
            return new SimpleStringProperty(joined);
        });

        // Buttons
        addButton.setOnAction(e -> handleAddRoom());
        updateButton.setOnAction(e -> handleUpdateRoom());
        deleteButton.setOnAction(e -> handleDeleteRoom());
        addRestrictionButton.setOnAction(e -> handleAddRestriction());
        backButton.setOnAction(e -> handleBackButton());
    }

    /**
     * Sets the view manager for screen navigation.
     *
     * @param viewManager The view manager to set
     */
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    // Handles back button navigation
    private void handleBackButton() {
        this.viewManager.showMainMenu();
    }

    // Handles adding a new room
    @FXML
    private void handleAddRoom() {
        try {
            String name = nameField1.getText();
            int nbBedsMax = nbBedsMaxSpinner.getValue();

            if (name == null || name.trim().isEmpty()) {
                showAlert("Room name is required.");
                return;
            }

            Room room = new Room(name, nbBedsMax);
            roomService.createRoom(room);
            roomList.add(room);
            roomTableView.refresh();

        } catch (Exception e) {
            showAlert("Error adding room: " + e.getMessage());
        }
    }

    // Handles adding restrictions to a room
    @FXML
    private void handleAddRestriction() {
        try {
            String restrictionName = nameField2.getText();
            Gender gender = genderRestriction.getValue();
            Integer minAge = minAgeField.getText().isEmpty() ? null : Integer.parseInt(minAgeField.getText());
            Integer maxAge = maxAgeField.getText().isEmpty() ? null : Integer.parseInt(maxAgeField.getText());
            String operator = logicOperator.getValue();


            if ((restrictionName == null || restrictionName.trim().isEmpty()) &&
                    gender == null && minAge == null && maxAge == null && operator == null) {
                showAlert("At least one restriction field must be filled.");
                return;
            }

            RestrictionType restriction = new RestrictionType(restrictionName, gender, minAge, maxAge);
            Room selectedRoom = roomTableView.getSelectionModel().getSelectedItem();
            if (selectedRoom == null) {
                showAlert("Please select a room to add restriction.");
                return;
            }

            // Add restriction to the room
            roomService.addRestrictionToRoom(selectedRoom, restriction);
            System.out.println("Added restriction to room: " + selectedRoom.getId());
            System.out.println("Restriction: " + restriction.getLabel());

            showAlert("Restriction added successfully");

            // Clear fields
            nameField2.clear();
            genderRestriction.setValue(null);
            logicOperator.setValue(null);
            minAgeField.clear();
            maxAgeField.clear();
            roomTableView.refresh();

        } catch (NumberFormatException e) {
            showAlert("Min and Max age must be valid integers.");
        } catch (Exception e) {
            showAlert("Error adding restriction: " + e.getMessage());
        }
    }

    // Handles updating an existing room
    @FXML
    private void handleUpdateRoom() {
        Room selectedRoom = roomTableView.getSelectionModel().getSelectedItem();

        if (selectedRoom != null) {
            String name = nameField1.getText();
            Integer nbBedsMax = nbBedsMaxSpinner.getValue();

            if (name == null || name.trim().isEmpty()) {
                showAlert("Room name is required.");
                return;
            }

            if (nbBedsMax < 0) {
                showAlert("Number of beds must be non-negative.");
                return;
            }

            // Update the room object
            selectedRoom.setName(name.trim());
            selectedRoom.setNbBedsMax(nbBedsMax);

            // Update in database
            roomService.updateRoom(selectedRoom);

            // Refresh TableView
            roomTableView.refresh();

            // Clear selection and fields
            roomTableView.getSelectionModel().clearSelection();
            nameField1.clear();
            nbBedsMaxSpinner.getValueFactory().setValue(1);

            showAlert("Room updated successfully.");

        } else {
            showAlert("Please select a room to update.");
        }
    }

    // Handles deleting a room
    @FXML
    private void handleDeleteRoom() {
        Room selectedRoom = roomTableView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            roomTableView.getSelectionModel().clearSelection();
            boolean success = roomService.deleteRoom(selectedRoom.getId());
            if (success){
                showAlert("Success delete");
                roomList.remove(selectedRoom);
                roomTableView.refresh();
            }
            else{
                showAlert("It is currently impossible to delete this data, it is probably being used elsewhere. Delete it below.");
            }
        } else {
            showAlert("Please select a room to delete.");
        }
    }

    // Shows alert dialog with message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}