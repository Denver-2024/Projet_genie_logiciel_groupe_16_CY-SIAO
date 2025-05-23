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

    //Data access layer for Person entities
    private final PersonDao personDao;
    //Data access layer for Address entities  
    private AddressDao addressDao;
    //Data access layer for Knows relationship
    private KnowsDao knowsDao;

    /**
     * Initializes a new instance of the PersonService class.
     * The service is responsible for managing Person objects and their associations
     * with addresses and other related entities. It uses data access objects
     * (personDao, addressDao, knowsDao) to perform database operations.
     */
    public PersonService() {
        this.personDao = new PersonDao();
        this.addressDao = new AddressDao();
        this.knowsDao = new KnowsDao();
    }

    /**
     * Retrieves all persons from the database
     *
     * @return List of all Person objects
     * @throws SQLException if database error occurs
     */
    public List<Person> getAllPersons() throws SQLException {
        return personDao.findAll();
    }

    /**
     * Finds a person by their ID and loads their addresses
     *
     * @param id The ID of the person to find
     * @return The Person object if found, null otherwise
     * @throws SQLException if database error occurs
     */
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

    /**
     * Finds all persons with the given first name
     *
     * @param firstName The first name to search for
     * @return List of matching Person objects
     * @throws SQLException if database error occurs
     */
    public List<Person> getByFirstName(String firstName) throws SQLException {
        List<Person> persons = new ArrayList<>();
        List<Person> personsName = new ArrayList<>();
        persons = getAllPersons();
        for (Person p : persons) {
            if (p.getFirstName().equals(firstName)) {
                personsName.add(p);
            }
        }
        if (personsName.isEmpty()) {
            System.err.println("Error nobody with this first name : " + firstName);
        }
        return personsName;
    }

    /**
     * Finds all persons with the given last name
     *
     * @param lastName The last name to search for
     * @return List of matching Person objects
     * @throws SQLException if database error occurs
     */
    public List<Person> getByLastName(String lastName) throws SQLException {
        List<Person> persons = new ArrayList<>();
        List<Person> personsName = new ArrayList<>();
        persons = getAllPersons();
        for (Person p : persons) {
            if (p.getLastName().equals(lastName)) {
                personsName.add(p);
            }
        }
        if (personsName.isEmpty()) {
            System.err.println("Error nobody with this last name : " + lastName);
        }
        return personsName;
    }

    /**
     * Finds all persons with the given first and last name
     *
     * @param firstName The first name to search for
     * @param lastName  The last name to search for
     * @return List of matching Person objects
     * @throws SQLException if database error occurs
     */
    public List<Person> getByName(String firstName, String lastName) throws SQLException {
        List<Person> persons = new ArrayList<>();
        List<Person> personsName = new ArrayList<>();
        persons = getAllPersons();
        for (Person p : persons) {
            if (p.getLastName().equals(lastName) && p.getFirstName().equals(firstName)) {
                personsName.add(p);
            }
        }
        if (personsName.isEmpty()) {
            System.err.println("Error nobody with this name : " + firstName + " " + lastName);
        }
        return personsName;
    }

    /**
     * Creates a new person record
     *
     * @param person The Person object to create
     */
    public void createPerson(Person person) {
        // You could add validation logic here if needed
        personDao.createPerson(person);
    }

    /**
     * Updates an existing person record
     *
     * @param person The Person object to update
     * @throws IllegalArgumentException if person ID is invalid
     */
    public void updatePerson(Person person) {
        if (person.getId() <= 0) {
            throw new IllegalArgumentException("Invalid ID for update.");
        }
        personDao.updatePerson(person);
    }

    /**
     * Deletes a person by their ID
     *
     * @param id The ID of the person to delete
     * @return true if the delete is a success
     */
    public boolean deletePerson(int id) {
        return personDao.deletePerson(id);
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

    /**
     * Associates an address with a person
     *
     * @param personId The ID of the person
     * @param address  The address to add
     * @throws SQLException             if database error occurs
     * @throws IllegalArgumentException if person not found
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