package com.cy_siao.controller;

import com.cy_siao.model.person.Gender;
import com.cy_siao.model.person.Person;
import com.cy_siao.service.PersonService;
import com.cy_siao.view.CLIView;

import java.util.List;
import java.util.Scanner;

public class PersonController {
    private PersonService personService;
    private CLIView view;

    public PersonController(){
        this.personService = new PersonService();
    }

    public void start(CLIView view) {
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


    private void updatePerson(){
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

    private void deletePerson(){
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

