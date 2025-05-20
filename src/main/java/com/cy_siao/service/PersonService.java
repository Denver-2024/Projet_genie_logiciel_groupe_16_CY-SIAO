package com.cy_siao.service;

import com.cy_siao.dao.PersonDao;
import com.cy_siao.model.person.Address;
import com.cy_siao.model.person.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonService {

    private final PersonDao personDao;

    public PersonService() {
        this.personDao = new PersonDao();
    }

    public List<Person> getAllPersons() {
        return personDao.findAll(); // findAll
    }

    public Person getPersonById(int id) {
        List<Person> researchIn; // Rechercher l'id dans cette list
        researchIn = getAllPersons();
        for (Person p: researchIn){
            if (p.getId() == id){
                return p;
            }
        }
        return null;
    }

    public List<Person> getByFirstName(String firstName){
        List<Person> persons = new ArrayList<>();
        List<Person> personsName = new ArrayList<>();
        persons = getAllPersons();
        for (Person p : persons){
            if (p.getFirstName().equals(firstName)){
                personsName.add(p);
            }
        }
        if (personsName.isEmpty()){
            System.err.println("Error nobody with this first name : " + firstName);
        }
        return personsName;
    }

    public List<Person> getByLastName(String lastName){
        List<Person> persons = new ArrayList<>();
        List<Person> personsName = new ArrayList<>();
        persons = getAllPersons();
        for (Person p : persons){
            if (p.getLastName().equals(lastName)){
                personsName.add(p);
            }
        }
        if (personsName.isEmpty()){
            System.err.println("Error nobody with this last name : " + lastName);
        }
        return personsName;
    }

    public List<Person> getByName(String firstName, String lastName){
        List<Person> persons = new ArrayList<>();
        List<Person> personsName = new ArrayList<>();
        persons = getAllPersons();
        for (Person p : persons){
            if (p.getLastName().equals(lastName)  && p.getFirstName().equals(firstName)){
                personsName.add(p);
            }
        }
        if (personsName.isEmpty()){
            System.err.println("Error nobody with this name : " + firstName + " " + lastName);
        }
        return personsName;
    }

    public void createPerson(Person person) {
        // You could add validation logic here if needed
        personDao.createPerson(person);
    }

    public void updatePerson(Person person) {
        if (person.getId() <= 0) {
            throw new IllegalArgumentException("Invalid ID for update.");
        }
        personDao.updatePerson(person);
    }

    public void deletePerson(int id) {
        personDao.deletePerson(id);
    }

    public boolean isPersonCompatible(Person person1, Person person2) {
        // Based on the diagram, compatibility is checked via RestrictionType
        if (person1.getRestrictionType() == null || person2.getRestrictionType() == null) {
            return true; // No restriction blocks
        }
        return person1.getRestrictionType().isRespectedBy(person2)
                && person2.getRestrictionType().isRespectedBy(person1);
    }

    public void addAddressToPerson(int personId, Address address) {
        Person person = getPersonById(personId);
        if (person == null) {
            throw new IllegalArgumentException("No person found with ID: " + personId);
        }

        person.addAddress(address);
        personDao.updatePerson(person); // Persist updated data
    }
}