package com.cy_siao.controller.gui;

import com.cy_siao.service.PersonService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.cy_siao.model.person.Person;
import com.cy_siao.model.person.Gender;

import java.net.URL;
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
    private TextField phoneNumberField;
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

    private ObservableList<Person> personList = FXCollections.observableArrayList();
    private PersonService personService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderComboBox.setItems(FXCollections.observableArrayList(Gender.values()));
        personTableView.setItems(personList);

        addButton.setOnAction(e -> handleAddPerson());
        updateButton.setOnAction(e -> handleUpdatePerson());
        deleteButton.setOnAction(e -> handleDeletePerson());
    }

    public PersonControllerFx(){
        personList = (ObservableList<Person>) personService.getAllPersons();
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
                if (socialSecurityNumber != null && !socialSecurityNumber.isEmpty()){
                    person.setSocialSecurityNumber(Long.parseLong(socialSecurityNumber));
                }
                personList.add(person);
                personService.createPerson(person);
                firstNameField.clear();
                lastNameField.clear();
                ageField.clear();
                genderComboBox.setValue(null);
            } else {
                showAlert("Please enter valid person details");
                firstNameField.clear();
                lastNameField.clear();
                ageField.clear();
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid age format");
        }
    }

    private void handleUpdatePerson() {
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            // Update person implementation here
        } else {
            showAlert("Please select a person to update");
        }
    }

    private void handleDeletePerson() {
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            // Delete person implementation here
        } else {
            showAlert("Please select a person to delete");
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
