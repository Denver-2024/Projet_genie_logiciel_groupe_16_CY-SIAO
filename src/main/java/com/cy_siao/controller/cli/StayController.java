package com.cy_siao.controller.cli;

import com.cy_siao.model.Bed;
import com.cy_siao.model.Stay;
import com.cy_siao.model.person.Person;
import com.cy_siao.service.BedService;
import com.cy_siao.service.PersonService;
import com.cy_siao.service.StayService;
import com.cy_siao.view.CLIView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class StayController {
    // Service for stay-related operations
    private StayService stayService;
    // Service for person-related operations
    private PersonService personService;
    // Service for bed-related operations
    private BedService bedService;
    // CLI view for user interaction
    private CLIView view;

    /**
     * Constructs a new StayController and initializes services.
     */
    public StayController() {
        this.stayService = new StayService();
        this.personService = new PersonService();
        this.bedService = new BedService();
    }

    /**
     * Starts the stay management menu loop.
     * Handles user choices for assigning, unassigning, freeing beds, and listing stays.
     *
     * @param view the CLIView instance for user interaction
     * @throws SQLException if a database access error occurs
     */
    public void start(CLIView view) throws SQLException {
        this.view = view;
        int option;
        do {
            option = view.showStayMenu(); // à implémenter dans CLIView
            switch (option) {
                case 1 -> assignStay();
                case 2 -> unassignStay();
                case 3 -> freeBed();
                case 4 -> listAllStays();
            }
        } while (option != 0);
    }

    // Assigns a person to a bed for a stay.
    private void assignStay() throws SQLException {
        view.showMessage("=== Assigner une personne à un lit ===");
        Person person = selectPerson();
        if (person == null) return;

        Bed bed = selectBed();
        if (bed == null) return;

        LocalDate arrival = view.askDate("Date d'arrivée (YYYY-MM-DD) : ");
        LocalDate departure = view.askDate("Date de départ (YYYY-MM-DD) : ");

        if (stayService.assignPersonToBed(person, bed, arrival, departure)) {
            view.showMessage("La personne a bien été assignée au lit.");
        } else {
            view.showError("Impossible d'assigner cette personne au lit (incompatibilité ou lit occupé).");
        }
    }

    // Unassigns a person from a bed.
    private void unassignStay() throws SQLException {
        view.showMessage("=== Désassigner une personne d'un lit ===");
        Person person = selectPerson();
        if (person == null) return;

        Bed bed = selectBed();
        if (bed == null) return;

        if (stayService.unassign(person, bed)) {
            view.showMessage("Séjour supprimé avec succès.");
        } else {
            view.showError("Aucun séjour trouvé pour cette personne et ce lit.");
        }
    }

    // Lists all stays.
    private void listAllStays() {
        List<Stay> stays = stayService.getAllStays(); // à ajouter dans StayService
        if (stays.isEmpty()) {
            view.showMessage("Aucun séjour trouvé.");
        } else {
            stays.forEach(stay -> view.showMessage(stay.toString()));
        }
    }

    // Prompts user to select a person.
    private Person selectPerson() throws SQLException {
        String lastName = view.askString("Nom : ");
        String firstName = view.askString("Prénom : ");
        List<Person> persons = personService.getByName(firstName, lastName);

        if (persons.isEmpty()) {
            view.showError("Aucune personne trouvée.");
            return null;
        }

        view.showMessage(persons.toString());
        int id = view.askInt("ID de la personne : ");
        Person person = personService.getPersonById(id);

        if (person == null) view.showError("Personne introuvable.");
        return person;
    }

    // Prompts user to select a bed.
    private Bed selectBed() throws SQLException
    {
        int id = view.askInt("ID du lit : ");
        Bed bed = bedService.getBedById(id);
        if (bed == null) view.showError("Lit introuvable.");
        return bed;
    }
}
