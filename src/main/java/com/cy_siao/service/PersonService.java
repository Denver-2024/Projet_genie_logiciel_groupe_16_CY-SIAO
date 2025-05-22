package com.cy_siao.service;

import com.cy_siao.dao.AddressDao;
import com.cy_siao.dao.KnowsDao;
import com.cy_siao.dao.PersonDao;
import com.cy_siao.model.Knows;
import com.cy_siao.model.person.Address;
import com.cy_siao.model.person.Person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonService {

    private final PersonDao personDao;
    private AddressDao addressDao;
    private KnowsDao knowsDao;

    public PersonService() {
        this.personDao = new PersonDao();
        this.addressDao = new AddressDao();
        this.knowsDao = new KnowsDao();
    }

    public List<Person> getAllPersons() throws SQLException {
        return personDao.findAll();
    }

    public Person getPersonById(int id) throws SQLException {
        Person person = personDao.findById(id);
        if (person != null) {
            List<Address> addresses = addressDao.findByPersonId(id);
            if (addresses != null) {
                person.setAddresses(addresses);
            }
        }
        return person;
    }

    public List<Person> getByFirstName(String firstName) throws SQLException {
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

    public List<Person> getByLastName(String lastName) throws SQLException {
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

    public List<Person> getByName(String firstName, String lastName) throws SQLException {
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

 /*   public boolean isPersonCompatible(Person person1, Person person2) {
        // Based on the diagram, compatibility is checked via RestrictionType
        if (person1.getRestrictionType() == null || person2.getRestrictionType() == null) {
            return true; // No restriction blocks
        }
        return person1.getRestrictionType().isRespectedBy(person2)
                && person2.getRestrictionType().isRespectedBy(person1);
    }
*/
    public void addAddressToPerson(int personId, Address address) throws SQLException {
        Person person = getPersonById(personId);
        if (person == null) {
            throw new IllegalArgumentException("No person found with ID: " + personId);
        }

        //person.addAddress(address);
        knowsDao.create(new Knows(personId, address.getId()));
    }
}