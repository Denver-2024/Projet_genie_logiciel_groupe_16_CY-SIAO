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

/**
 * Controller class for managing Person-related GUI operations.
 * Handles CRUD operations for Person entities and their addresses.
 */
public class PersonControllerFx implements Initializable {
    //Text field for first name input
    @FXML
    private TextField firstNameField;
    //Text field for last name input
    @FXML
    private TextField lastNameField;
    //Text field for age input 
    @FXML
    private TextField ageField;
    //Text field for place of birth input
    @FXML
    private TextField placeOfBirthField;
    //Text field for social security number input
    @FXML
    private TextField socialSecurityNumberField;
    //Text field for street number input
    @FXML
    private TextField streetNumberField;
    //Text field for street name input
    @FXML
    private TextField streetNameField;
    //Text field for postal code input
    @FXML
    private TextField postalCodeField;
    //Text field for city name input
    @FXML
    private TextField cityNameField;
    //Combo box for gender selection
    @FXML
    private ComboBox<Gender> genderComboBox;
    //Table view to display persons
    @FXML
    private TableView<Person> personTableView;
    //Button to add new person
    @FXML
    private Button addButton;
    //Button to update person
    @FXML
    private Button updateButton;
    //Button to delete person
    @FXML
    private Button deleteButton;
    //Button to add address
    @FXML
    private Button addAddressButton;
    //Column for first name in table
    @FXML
    private TableColumn<Person, String> firstNameCol;
    //Column for last name in table
    @FXML
    private TableColumn<Person, String> lastNameCol;
    //Column for age in table
    @FXML
    private TableColumn<Person, Integer> ageCol;
    //Column for gender in table
    @FXML
    private TableColumn<Person, Gender> genderCol;
    //Column for place of birth in table
    @FXML
    private TableColumn<Person, String> placeOfBirthCol;
    //Column for social security number in table
    @FXML
    private TableColumn<Person, Long> socialSecurityNumberCol;
    //Column for address in table
    @FXML
    private TableColumn<Person, String> addressCol;
    //Button to go back
    @FXML
    private Button backButton;

    //Observable list of persons
    private ObservableList<Person> personList = FXCollections.observableArrayList();
    //Service for person operations
    private PersonService personService = new PersonService();
    //Service for address operations
    private AddressService addressService = new AddressService();
    //Manager for view navigation
    private ViewManager viewManager;

    /**
     * Initializes the controller class.
     * Sets up the table columns, loads initial data and configures event handlers.
     *
     * @param location  The location used to resolve relative paths
     * @param resources The resources used to localize the root object
     */
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

    /**
     * Sets the view manager for navigation control.
     *
     * @param viewManager The ViewManager instance to set
     */
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    //Handler for adding new address to selected person
    private void handleAddAddress() {
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            try {
                Integer streetNumber = Integer.parseInt(streetNumberField.getText());
                String streetName = streetNameField.getText();
                Integer postalCode = Integer.parseInt(postalCodeField.getText());
                String cityName = cityNameField.getText();

                if (streetNumber > 0 && streetNumber < 1000000000 &&
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

    //Handler for adding new person
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
                personList.setAll(personService.getAllPersons());

                firstNameField.clear();
                lastNameField.clear();
                ageField.clear();
                genderComboBox.setValue(null);
                placeOfBirthField.clear();
                socialSecurityNumberField.clear();

            } else {
                showAlert("Veuillez entrer des informations valides pour la personne");
            }
        } catch (NumberFormatException e) {
            showAlert("Format d'âge invalide");
        } catch (SQLException e) {
            showAlert("Erreur lors de la création de la personne : " + e.getMessage());
        }
    }

    //Handler for updating existing person
    private void handleUpdatePerson() {
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            personTableView.getSelectionModel().clearSelection();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String placeOfBirth = placeOfBirthField.getText();
            String socialSecurityNumber = socialSecurityNumberField.getText();
            String ageText = ageField.getText();

            if (ageText == null || ageText.trim().isEmpty()) {
                showAlert("L'âge ne peut pas être vide");
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
                    showAlert("Veuillez remplir tous les champs obligatoires correctement");
                }
            } catch (NumberFormatException e) {
                showAlert("Format d'âge invalide. Veuillez entrer un nombre entier valide.");
            }
        } else {
            showAlert("Veuillez sélectionner une personne à mettre à jour");
        }
    }

    //Handler for back button navigation
    private void handleBackButton() {
        this.viewManager.showMainMenu();
    }

    //Handler for deleting person
    private void handleDeletePerson() {
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            personTableView.getSelectionModel().clearSelection();
            boolean success = personService.deletePerson(selectedPerson.getId());
            if (success){
                showAlert("Success delete");
                personList.remove(selectedPerson);
            }
            else{
                showAlert("It is currently impossible to delete this data, it is probably being used elsewhere. Delete it below.");
            }
        } else {
            showAlert("Please select a person to delete");
        }
    }

    //Shows alert dialog with error message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
