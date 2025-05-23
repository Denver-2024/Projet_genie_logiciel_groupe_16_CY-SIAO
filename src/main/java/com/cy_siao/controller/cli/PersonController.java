package com.cy_siao.controller.cli;

import com.cy_siao.model.person.Gender;
import com.cy_siao.model.person.Person;
import com.cy_siao.service.PersonService;
import com.cy_siao.view.CLIView;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller class for managing Person-related CLI interactions.
 * Handles user input and delegates operations to the PersonService.
 */
public class PersonController {
    // Service for person-related operations 
    private PersonService personService;
    // CLI view for user interaction 
    private CLIView view;

    /**
     * Constructs a new PersonController and initializes the PersonService.
     */
    public PersonController(){
        this.personService = new PersonService();
    }

    /**
     * Starts the person management menu loop.
     * Handles user choices for listing, adding, updating, and deleting persons.
     *
     * @param view the CLIView instance for user interaction
     * @throws SQLException if a database access error occurs
     */
    public void start(CLIView view) throws SQLException {
        int option;
        this.view=view;
        do{
            option=view.showPersonMenu();
            switch (option){
                case 1:
                    view.showMessage(personService.getAllPersons().toString());
                    break;
                case 2:
                    this.addPerson();
                    break;
                case 3:
                    this.updatePerson();
                    break;
                case 4:
                    this.deletePerson();
            }
        } while(option!=0);
    }

    // Adds a new person by prompting the user for details.
    private void addPerson(){
        view.showMessage("Enter person details:");
        String firstName = view.askString("First name: ");
        String lastName = view.askString("Last Name :");
        int age = view.askInt("Age :");
        String genderStr = view.askString("Gender (MALE/FEMALE) :");

        try {
            Gender gender = Gender.fromString(genderStr);
            personService.createPerson(new Person(lastName, firstName, gender, age));
            view.showMessage("Person added successfully");
        } catch (NumberFormatException e) {
            view.showError("L'âge doit être un nombre valide");
        } catch (IllegalArgumentException e) {
            view.showError("Le genre doit être MALE ou FEMALE");
        }
    }


    // Updates an existing person after searching by name.
    private void updatePerson() throws SQLException {
        String lastName = view.askString("Last Name :");
        String firstName = view.askString("First name: ");
        List<Person> persons = personService.getByName(lastName,firstName);
        view.showMessage(persons.toString());
        int id = Integer.parseInt(view.askString("Enter person id: "));
        Person person = personService.getPersonById(id);
        if(person!=null){
            person.setFirstName(view.askString("Enter new first name: "));
            person.setLastName(view.askString("Enter new last name: "));
            personService.updatePerson(person);
        }else{
            view.showError("No person found with id: "+id);
        }
    }

    // Deletes a person after searching by name.
    private void deletePerson() throws SQLException {
        String lastName = view.askString("Last Name :");
        String firstName = view.askString("First name: ");
        List<Person> persons = personService.getByName(firstName,lastName);
        view.showMessage(persons.toString());
        int id = Integer.parseInt(view.askString("Enter person id: "));
        Person person = personService.getPersonById(id);
        if (person!=null){
            personService.deletePerson(id);
        }else{
            view.showError("No person found with id: "+id);
        }
    }
}

