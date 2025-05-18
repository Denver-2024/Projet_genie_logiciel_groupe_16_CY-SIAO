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
    private StayService stayService;
    private PersonService personService;
    private BedService bedService;
    private CLIView view;

    public StayController() {
        this.stayService = new StayService();
        this.personService = new PersonService();
        this.bedService = new BedService();
    }

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

    private void freeBed() throws SQLException {
        view.showMessage("=== Libérer un lit pour une personne ===");
        Person person = selectPerson();
        if (person == null) return;

        Bed bed = selectBed();
        if (bed == null) return;

        stayService.free(bed, person);
        view.showMessage("Lit libéré pour cette personne (en mémoire).");
    }

    private void listAllStays() {
        List<Stay> stays = stayService.getAllStays(); // à ajouter dans StayService
        if (stays.isEmpty()) {
            view.showMessage("Aucun séjour trouvé.");
        } else {
            stays.forEach(stay -> view.showMessage(stay.toString()));
        }
    }

    private Person selectPerson() {
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

    private Bed selectBed() throws SQLException
    {
        int id = view.askInt("ID du lit : ");
        Bed bed = bedService.getBedById(id);
        if (bed == null) view.showError("Lit introuvable.");
        return bed;
    }
}
