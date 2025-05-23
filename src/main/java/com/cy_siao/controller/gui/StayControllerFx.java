package com.cy_siao.controller.gui;

import com.cy_siao.service.StayService;
import com.cy_siao.service.PersonService;
import com.cy_siao.service.BedService;
import com.cy_siao.view.ViewManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.cy_siao.model.person.Person;
import com.cy_siao.dao.BedDao;
import com.cy_siao.model.Bed;
import com.cy_siao.model.Stay;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.time.LocalDate;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;

public class StayControllerFx implements Initializable {

    private ViewManager viewManager;
    private List<Bed> bedList;
    private BedService bedService;

    @FXML
    private DatePicker arrivalDatePicker;
    @FXML
    private DatePicker departureDatePicker;
    @FXML
    private ComboBox<Person> personIdField;
    @FXML
    private ComboBox<Bed> bedIdField;
    @FXML
    private TextArea notesArea;
    @FXML
    private TableView<Stay> stayTableView;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button searchButton;
    @FXML
    private TableColumn<Stay, Integer> idCol;
    @FXML
    private TableColumn<Stay, LocalDate> arrivalDateCol;
    @FXML
    private TableColumn<Stay, LocalDate> departureDateCol;
    @FXML
    private TableColumn<Stay, String> personIdCol;
    @FXML
    private TableColumn<Stay, Integer> bedIdCol;

    @FXML
    private Button backButton;

    private ObservableList<Stay> stayList = FXCollections.observableArrayList();
    private StayService stayService = new StayService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        PersonService personService = new PersonService();
        List<Person> personList = null;
        try {
            personList = personService.getAllPersons();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Person> observablePersonList = FXCollections.observableArrayList(personList);
        personIdField.setItems(observablePersonList);

        bedList = new ArrayList<>();
        ObservableList<Bed> observableBedList = FXCollections.observableArrayList(bedList);
        bedIdField.setItems(observableBedList);

        // Initialisation des combobox et tableaux
        stayList = FXCollections.observableArrayList(stayService.getAllStays());
        stayTableView.setItems(stayList);

        // Configuration des boutons
        addButton.setOnAction(e -> handleAddStay());
        updateButton.setOnAction(e -> handleUpdateStay());
        deleteButton.setOnAction(e -> handleDeleteStay());
        searchButton.setOnAction(e -> handleSearchStay());
        backButton.setOnAction(e -> handleBackButton());


        // Configuration des colonnes du tableau
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        arrivalDateCol.setCellValueFactory(new PropertyValueFactory<>("dateArrival"));
        departureDateCol.setCellValueFactory(new PropertyValueFactory<>("dateDeparture"));
        bedIdCol.setCellValueFactory(new PropertyValueFactory<>("idBed"));
        personIdCol.setCellValueFactory(cellData -> {
        Stay stay = cellData.getValue();
            return new SimpleStringProperty(
                stay.getPerson() != null ? 
                stay.getPerson().getLastName() : 
                "N/A"
            );
        });

        arrivalDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> updateAvailableBeds());
        departureDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> updateAvailableBeds());
        personIdField.valueProperty().addListener((obs, oldVal, newVal) -> updateAvailableBeds());
    }

    public void setViewManager(ViewManager viewManager){
        this.viewManager = viewManager;
    }

    private void handleAddStay() {
        try {
            LocalDate arrivalDate = arrivalDatePicker.getValue();
            LocalDate departureDate = departureDatePicker.getValue();
            int personId = personIdField.getValue().getId();
            Person personSelect = personIdField.getValue();
            int bedId = bedIdField.getValue().getId();
            Bed bedSelect = bedIdField.getValue();

            if (arrivalDate != null && departureDate != null && 
                !arrivalDate.isAfter(departureDate)) {
                
                Stay stay = new Stay(bedId, personId, arrivalDate, departureDate);
                
                stayService.assignPersonToBed(personSelect, bedSelect, arrivalDate, departureDate);
                stayList.add(stay);
                
                clearFields();
                showAlert("Stay added successfully", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Invalid dates: arrival must be before departure", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid ID format", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error adding stay: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void handleUpdateStay() {
        Stay selectedStay = stayTableView.getSelectionModel().getSelectedItem();
        if (selectedStay != null) {
            try {
                LocalDate newArrivalDate = arrivalDatePicker.getValue();
                LocalDate newDepartureDate = departureDatePicker.getValue();
                Person newPersonSelect = personIdField.getValue();
                int newPersonId = personIdField.getValue().getId();
                int newBedId = bedIdField.getValue().getId();
                Bed newBedSelect = bedIdField.getValue();

                if (newArrivalDate != null && newDepartureDate != null && 
                    !newArrivalDate.isAfter(newDepartureDate)) {
                    
                    selectedStay.setDateArrival(newArrivalDate);
                    selectedStay.setDateDeparture(newDepartureDate);
                    selectedStay.setPerson(newPersonSelect);
                    selectedStay.setBed(newBedSelect);
                    
                    stayService.updateStay(selectedStay);
                    stayTableView.refresh();
                    
                    showAlert("Stay updated successfully", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Invalid dates: arrival must be before departure", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid ID format", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Please select a stay to update", Alert.AlertType.WARNING);
        }
    }

    private void handleDeleteStay() {
        Stay selectedStay = stayTableView.getSelectionModel().getSelectedItem();
        if (selectedStay != null) {
            stayService.deleteStay(selectedStay);
            stayList.remove(selectedStay);
            showAlert("Stay deleted successfully", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Please select a stay to delete", Alert.AlertType.WARNING);
        }
    }

    private void handleSearchStay() {
        // Implémentez la logique de recherche selon vos besoins
        showAlert("Search functionality to be implemented", Alert.AlertType.INFORMATION);
    }

    private void handleBackButton(){
        this.viewManager.showMainMenu();
    }

    private void clearFields() {
        arrivalDatePicker.setValue(null);
        departureDatePicker.setValue(null);
        personIdField.setValue(null);
        bedIdField.setValue(null);
        notesArea.clear();
    }

    private void updateAvailableBeds() {
        LocalDate arrival = arrivalDatePicker.getValue();
        LocalDate departure = departureDatePicker.getValue();
        Person selectedPerson = personIdField.getValue();

        if (arrival != null && departure != null && selectedPerson != null 
            && !arrival.isAfter(departure)) {
            
            try {
                // Récupère les lits disponibles pour la période et le genre
                BedDao bedDaoTest = new BedDao();
                List<Bed> allBeds = bedDaoTest.findAll();
                stayService.connectStayToBed(allBeds);
                List<Bed> availableBeds = new ArrayList<>();
                for (Bed bed : allBeds){
                    if (stayService.isAssignable(selectedPerson, bed, arrival, departure)){
                        availableBeds.add(bed);
                    }
                }

                // Met à jour la ComboBox
                ObservableList<Bed> observableBeds = FXCollections.observableArrayList(availableBeds);
                bedIdField.setItems(observableBeds);
                
                // Configure l'affichage dans la ComboBox
                bedIdField.setCellFactory(param -> new ListCell<Bed>() {
                    @Override
                    protected void updateItem(Bed bed, boolean empty) {
                        super.updateItem(bed, empty);
                        setText(empty || bed == null ? null : "Lit #" + bed.getId() + " - Chambre " + bed.getIdRoom());
                    }
                });
                
                bedIdField.setButtonCell(new ListCell<Bed>() {
                    @Override
                    protected void updateItem(Bed bed, boolean empty) {
                        super.updateItem(bed, empty);
                        setText(empty || bed == null ? null : "Lit #" + bed.getId() + " - Chambre " + bed.getIdRoom());
                    }
                });
                
            } catch (Exception e) {
                showAlert("Error occurred when trying to get the beds: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == Alert.AlertType.ERROR ? "Error" : "Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox getView() {
        if (arrivalDatePicker == null) arrivalDatePicker = new DatePicker();
        if (departureDatePicker == null) departureDatePicker = new DatePicker();
        if (personIdField == null) personIdField = new ComboBox<>();
        if (bedIdField == null) bedIdField = new ComboBox<>();
        if (notesArea == null) notesArea = new TextArea();
        if (addButton == null) addButton = new Button("Add Stay");
        if (updateButton == null) updateButton = new Button("Update Stay");
        if (deleteButton == null) deleteButton = new Button("Delete Stay");
        if (searchButton == null) searchButton = new Button("Search Stay");
        if (backButton == null) backButton = new Button("Back");
        if (stayTableView == null) stayTableView = new TableView<>();

        // Colonnes du tableau si non initialisées
        if (stayTableView.getColumns().isEmpty()) {
            idCol = new TableColumn<>("ID");
            arrivalDateCol = new TableColumn<>("Arrival");
            departureDateCol = new TableColumn<>("Departure");
            bedIdCol = new TableColumn<>("Bed");
            personIdCol = new TableColumn<>("Person");

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            arrivalDateCol.setCellValueFactory(new PropertyValueFactory<>("dateArrival"));
            departureDateCol.setCellValueFactory(new PropertyValueFactory<>("dateDeparture"));
            bedIdCol.setCellValueFactory(new PropertyValueFactory<>("idBed"));
            personIdCol.setCellValueFactory(cellData -> {
                Stay stay = cellData.getValue();
                return new SimpleStringProperty(
                        stay.getPerson() != null ? stay.getPerson().getLastName() : "N/A"
                );
            });

            stayTableView.getColumns().addAll(idCol, arrivalDateCol, departureDateCol, bedIdCol, personIdCol);
            stayTableView.setItems(stayList);
        }

        // Actions
        addButton.setOnAction(e -> handleAddStay());
        updateButton.setOnAction(e -> handleUpdateStay());
        deleteButton.setOnAction(e -> handleDeleteStay());
        searchButton.setOnAction(e -> handleSearchStay());
        backButton.setOnAction(e -> handleBackButton());

        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(20));
        layout.getChildren().addAll(
                new Label("Date Arrival:"), arrivalDatePicker,
                new Label("Date Departure:"), departureDatePicker,
                new Label("Person:"), personIdField,
                new Label("Bed:"), bedIdField,
                new Label("Remarks:"), notesArea,
                addButton, updateButton, deleteButton, searchButton, backButton,
                stayTableView
        );

        return layout;
    }
}