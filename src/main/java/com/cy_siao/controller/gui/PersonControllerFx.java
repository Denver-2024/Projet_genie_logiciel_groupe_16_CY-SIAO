package com.cy_siao.controller.gui;

import com.cy_siao.service.AddressService;
import com.cy_siao.service.PersonService;
import com.cy_siao.view.ViewManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.cy_siao.model.person.Person;
import com.cy_siao.model.person.Gender;
import com.cy_siao.model.person.Address;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PersonControllerFx implements Initializable {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField placeOfBirthField;
    @FXML
    private TextField socialSecurityNumberField;
    @FXML
    private TextField streetNumberField;
    @FXML
    private TextField streetNameField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityNameField;
    @FXML
    private ComboBox<Gender> genderComboBox;
    @FXML
    private TableView<Person> personTableView;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addAddressButton;
    @FXML
    private TableColumn<Person, String> firstNameCol;
    @FXML
    private TableColumn<Person, String> lastNameCol;
    @FXML
    private TableColumn<Person, Integer> ageCol;
    @FXML
    private TableColumn<Person, Gender> genderCol;
    @FXML
    private TableColumn<Person, String> placeOfBirthCol;
    @FXML
    private TableColumn<Person, Long> socialSecurityNumberCol;
    @FXML
    private TableColumn<Person, String> addressCol;

    @FXML
    private Button backButton;

    private ObservableList<Person> personList = FXCollections.observableArrayList();
    private PersonService personService = new PersonService();
    private AddressService addressService = new AddressService();
    private ViewManager viewManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderComboBox.setItems(FXCollections.observableArrayList(Gender.values()));
        try {
            personList = FXCollections.observableArrayList(personService.getAllPersons());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        personTableView.setItems(personList);

        addButton.setOnAction(e -> handleAddPerson());
        updateButton.setOnAction(e -> handleUpdatePerson());
        deleteButton.setOnAction(e -> handleDeletePerson());
        addAddressButton.setOnAction(e -> handleAddAddress());
        backButton.setOnAction(e -> handleBackButton());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        placeOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("placeOfBirth"));
        socialSecurityNumberCol.setCellValueFactory(new PropertyValueFactory<>("socialSecurityNumber"));
        addressCol.setCellValueFactory(cellData -> {
            List<Address> addresses = cellData.getValue().getAddresses();
            if (addresses == null || addresses.isEmpty()) {
                return new javafx.beans.property.SimpleStringProperty("");
            }
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < addresses.size(); i++) {
                Address addr = addresses.get(i);
                str.append(addr.getStreetNumber()).append(" ")
                        .append(addr.getStreetName()).append(", ")
                        .append(addr.getPostalCode()).append(" ")
                        .append(addr.getCityName());
                if (i < addresses.size() - 1) {
                    str.append(" | ");
                }
            }
            return new javafx.beans.property.SimpleStringProperty(str.toString());
        });
    }
    public void setViewManager(ViewManager viewManager){
        this.viewManager = viewManager;
    }


    private void handleAddAddress() {
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            try {
                Integer streetNumber = Integer.parseInt(streetNumberField.getText());
                String streetName = streetNameField.getText();
                Integer postalCode = Integer.parseInt(postalCodeField.getText());
                String cityName = cityNameField.getText();

                if (streetNumber>0 && streetNumber<1000000000 &&
                        streetName != null && !streetName.isEmpty() &&
                        postalCode > 0 && postalCode < 1000000000 &&
                        cityName != null && !cityName.isEmpty()) {

                    Address address = new Address(streetNumber, streetName, postalCode, cityName);
                    try {
                        addressService.createAddress(address);
                    } catch (SQLException ex) {
                        showAlert("Error creating address: " + ex.getMessage());
                        return;
                    }
                    try {
                        personService.addAddressToPerson(selectedPerson.getId(), address);
                    } catch (SQLException ex) {
                        showAlert("Error adding address to person: " + ex.getMessage());
                        return;
                    }

                    streetNumberField.clear();
                    streetNameField.clear();
                    postalCodeField.clear();
                    cityNameField.clear();

                    personList.setAll(personService.getAllPersons());
                    showAlert("Address added successfully");
                } else {
                    showAlert("Please enter valid address details");
                }
            } catch (Exception e) {
                showAlert("Error adding address: " + e.getMessage());
            }
        } else {
            showAlert("Please select a person to add address");
        }
    }

    private void handleAddPerson() {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String placeOfBirth = placeOfBirthField.getText();
            String socialSecurityNumber = socialSecurityNumberField.getText();
            int age = Integer.parseInt(ageField.getText());
            Gender gender = genderComboBox.getValue();

            if (firstName != null && !firstName.isEmpty() && gender != null && age > 0 && age < 150 && lastName != null && !lastName.isEmpty()) {
                Person person = new Person(lastName, firstName, gender, age);
                if (placeOfBirth != null && !placeOfBirth.isEmpty()) {
                    person.setPlaceOfBirth(placeOfBirth);
                }
                if (socialSecurityNumber != null && !socialSecurityNumber.isEmpty()) {
                    person.setSocialSecurityNumber(Long.parseLong(socialSecurityNumber));
                }
                
                personService.createPerson(person);
                // Refresh the list with the updated data
                personList.setAll(personService.getAllPersons());
                
                firstNameField.clear();
                lastNameField.clear();
                ageField.clear();
                genderComboBox.setValue(null);
                placeOfBirthField.clear();
                socialSecurityNumberField.clear();
                
            } else {
                showAlert("Please insert valid infos of the person");
            }
        } catch (NumberFormatException e) {
            showAlert("Age format invalid");
        } catch (SQLException e) {
            showAlert("Error when trying to register the person: " + e.getMessage());
        }
    }

    private void handleUpdatePerson() {
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            personTableView.getSelectionModel().clearSelection();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String placeOfBirth = placeOfBirthField.getText();
            String socialSecurityNumber = socialSecurityNumberField.getText();
            String ageText = ageField.getText();

            // check the age field
            if (ageText == null || ageText.trim().isEmpty()) {
                showAlert("Age field cannot be empty");
                return;
            }

            try {
                int age = Integer.parseInt(ageText);
                Gender gender = genderComboBox.getValue();

                if (firstName != null && !firstName.isEmpty() &&
                        gender != null &&
                        age > 0 && age < 150 &&
                        lastName != null && !lastName.isEmpty()) {

                    selectedPerson.setFirstName(firstName);
                    selectedPerson.setLastName(lastName);
                    selectedPerson.setGender(gender);
                    selectedPerson.setAge(age);

                    if (placeOfBirth != null && !placeOfBirth.isEmpty()) {
                        selectedPerson.setPlaceOfBirth(placeOfBirth);
                    }
                    if (socialSecurityNumber != null && !socialSecurityNumber.isEmpty()) {
                        selectedPerson.setSocialSecurityNumber(Long.parseLong(socialSecurityNumber));
                    }
                    personService.updatePerson(selectedPerson);
                    personList.set(personList.indexOf(selectedPerson), selectedPerson);
                    firstNameField.clear();
                    lastNameField.clear();
                    ageField.clear();
                    genderComboBox.setValue(null);
                    placeOfBirthField.clear();
                    showAlert("Person updated successfully");
                } else {
                    showAlert("Please fill in all the mandatory fields");
                }
            } catch (NumberFormatException e) {
                showAlert("Age format invalid. Please insert a valid integer age.");
            }
        } else {
            showAlert("Please select a person to be updated.");
        }
    }

    private void handleBackButton(){
        this.viewManager.showMainMenu();
    }

    private void handleDeletePerson() {
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            personTableView.getSelectionModel().clearSelection();
            personList.remove(selectedPerson);
            personService.deletePerson(selectedPerson.getId());
        } else {
            showAlert("Please select a person to delete");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}