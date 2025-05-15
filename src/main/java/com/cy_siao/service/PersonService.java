package com.cy_siao.service;

import com.cy_siao.dao.PersonDao;
import com.cy_siao.model.person.Address;
import com.cy_siao.model.person.Person;

import java.util.List;

public class PersonService {

    private final PersonDao personDao;

    public PersonService() {
        this.personDao = new PersonDao();
    }

    public List<Person> getAllPersons() {
        return personDao.getAllPersons();
    }

    public Person getPersonById(int id) {
        return personDao.getPersonById(id);
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
        Person person = personDao.getPersonById(personId);
        if (person == null) {
            throw new IllegalArgumentException("No person found with ID: " + personId);
        }

        person.addAddress(address);
        personDao.updatePerson(person); // Persist updated data
    }
}