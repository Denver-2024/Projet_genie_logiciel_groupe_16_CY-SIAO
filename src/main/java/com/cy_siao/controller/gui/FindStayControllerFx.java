package com.cy_siao.controller.gui;

import com.cy_siao.model.Room;
import com.cy_siao.model.person.Person;
import com.cy_siao.view.ViewManager;
import com.cy_siao.service.PersonService;
import com.cy_siao.service.StayService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FindStayControllerFx implements Initializable {

    @FXML private ComboBox<Person> personComboBox;
    @FXML private Button addButton;
    @FXML private Button backButton;
    @FXML private Button removeButton;
    @FXML private Button searchStay;
    @FXML private TextField numberField;
    @FXML private TableView<Person> selectedPersonTableView;
    @FXML private TableColumn<Person, Integer> idCol;
    @FXML private TableColumn<Person, String> firstNameCol;
    @FXML private TableColumn<Person, String> lastNameCol;
    @FXML private TableColumn<Person, Integer> ageCol;

    private ObservableList<Person> selectedPersons = FXCollections.observableArrayList();

    private ViewManager viewManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configuration des colonnes du tableau
        backButton.setOnAction(e -> handleBackButton());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        PersonService personService = new PersonService();
        List<Person> allPersons;
        try {
            allPersons = personService.getAllPersons();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Person> selectionnablePersonList = FXCollections.observableArrayList(allPersons);
        
        selectedPersonTableView.setItems(selectedPersons);

        // Configurer la ComboBox avec recherche
        personComboBox.setItems(selectionnablePersonList);
        personComboBox.setCellFactory(param -> new ListCell<Person>() {
            @Override
            protected void updateItem(Person person, boolean empty) {
                super.updateItem(person, empty);
                setText(empty || person == null ? null : person.getFirstName());
            }
        });
        personComboBox.setButtonCell(new ListCell<Person>() {
            @Override
            protected void updateItem(Person person, boolean empty) {
                super.updateItem(person, empty);
                setText(empty || person == null ? null : person.getFirstName());
            }
        });

        // Gestion des boutons
        addButton.setOnAction(e -> addSelectedPerson());
        removeButton.setOnAction(e -> removeSelectedPerson());
        searchStay.setOnAction(e -> handleSearchStayButton());

        // Validation numérique
        numberField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                numberField.setText(newVal.replaceAll("[^\\d]", ""));
            }
        });

    }

    public void setViewManager(ViewManager viewManager){
        this.viewManager = viewManager;
    }

    private void handleBackButton(){
        this.viewManager.showMainMenu();
    }


    private void addSelectedPerson() {
        Person selected = personComboBox.getValue();
        if (selected != null && !selectedPersons.contains(selected)) {
            selectedPersons.add(selected);
        }
    }

    private void removeSelectedPerson() {
        Person selected = selectedPersonTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selectedPersons.remove(selected);
        }
    }

    // Méthode pour récupérer le nombre saisi
    public int getEnteredNumber() {
        try {
            return Integer.parseInt(numberField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Méthode pour récupérer les personnes sélectionnées
    public ObservableList<Person> getSelectedPersons() {
        return selectedPersons;
    }

    public void handleSearchStayButton(){
        int nbDay = Integer.parseInt(numberField.getText());
        List<Person> lstPerson = new ArrayList<>();
        lstPerson.addAll(selectedPersons);
        if(nbDay > 0 && lstPerson.size() != 0){
            StayService stayService = new StayService();
            Room roomFind = stayService.findStay(lstPerson, nbDay);
            LocalDate arrivalDate = stayService.findStayRoom(lstPerson, nbDay, roomFind);
            if (roomFind != null){
                handleStayFindPopup(roomFind, arrivalDate, nbDay);
            }
        }
    }

    @FXML
    private void handleStayFindPopup(Room room, LocalDate date, int nbDay) {
        // Création de la boîte de dialogue
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Sejour Trouvé");
        dialog.setHeaderText("Jour d'arrivé :"+date+"\nDans la salle :"+room.getName());
        
        // Ajout du contenu
        Label label = new Label("Voulez vous réserver les lits pour ces personnes ?");
        VBox content = new VBox(10, label);
        content.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(content);
        
        // Ajout des boutons
        ButtonType buttonTypeOk = new ButtonType("Confirmer", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, buttonTypeCancel);
        
        // Style optionnel
        dialog.getDialogPane().setStyle("-fx-background-color: #f5f5f5;");
        
        // Gestion de la réponse
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType == buttonTypeOk) {
                System.out.println("Reservation des sejours a mettre ici");
                LocalDate arrivalDate = date;
                LocalDate departureDate = date.plusDays(nbDay);
            }
        });
    }
}