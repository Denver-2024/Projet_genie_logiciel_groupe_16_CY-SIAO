package com.cy_siao.dao;

import com.cy_siao.model.person.Address;
import com.cy_siao.model.person.Person;
import com.cy_siao.model.person.Gender;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PersonDao is a Data Access Object (DAO) class responsible for managing
 * database operations related to the Person entity. This class provides
 * Create, Read, Update, and Delete (CRUD) operations for Person records.
 */
public class PersonDao {

    /**
     * Creates a new person record in the database
     *
     * @param person The Person object to be created in the database
     * @throws SQLException If database access error occurs
     */
    public void createPerson(Person person) {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO person (");
        List<Object> values = new ArrayList<>();

        // Construction dynamique de la requête
        if (person.getFirstName() != null) {
            sqlBuilder.append("firstName,");
            values.add(person.getFirstName());
        }
        if (person.getLastName() != null) {
            sqlBuilder.append("lastName,");
            values.add(person.getLastName());
        }
        if (person.getGender() != null) {
            sqlBuilder.append("gender,");
            values.add(person.getGender().toString().charAt(0));
        }
        if (person.getAge() > 0) {
            sqlBuilder.append("age,");
            values.add(person.getAge());
        }
        if (person.getPlaceOfBirth() != null) {
            sqlBuilder.append("placeOfBirth,");
            values.add(person.getPlaceOfBirth());
        }
        if (person.getSocialSecurityNumber() > 0) {
            sqlBuilder.append("socialSecurityNumber,");
            values.add(person.getSocialSecurityNumber());
        }

        // Suppression de la dernière virgule
        String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1) + ") VALUES (" +
                String.join(",", Collections.nCopies(values.size(), "?")) + ")";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Définition des paramètres
            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    person.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating person failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la personne dans la base de données: " + e.getMessage());
        }
    }

    /**
     * Updates an existing person record in the database
     *
     * @param person The Person object containing updated information
     * @throws IllegalArgumentException If person ID is invalid
     * @throws SQLException             If database access error occurs
     */
    public void updatePerson(Person person) {
        if (person.getId() <= 0) {
            throw new IllegalArgumentException("L'ID de la personne est requis pour la mise à jour");
        }

        StringBuilder sqlBuilder = new StringBuilder("UPDATE person SET ");
        List<Object> values = new ArrayList<>();

        if (person.getFirstName() != null) {
            sqlBuilder.append("firstName = ?,");
            values.add(person.getFirstName());
        }
        if (person.getLastName() != null) {
            sqlBuilder.append("lastName = ?,");
            values.add(person.getLastName());
        }
        if (person.getGender() != null) {
            sqlBuilder.append("gender = ?,");
            values.add(person.getGender().toString().charAt(0));
        }
        if (person.getAge() > 0) {
            sqlBuilder.append("age = ?,");
            values.add(person.getAge());
        }
        if (person.getPlaceOfBirth() != null) {
            sqlBuilder.append("placeOfBirth = ?,");
            values.add(person.getPlaceOfBirth());
        }
        if (person.getSocialSecurityNumber() > 0) {
            sqlBuilder.append("socialSecurityNumber = ?,");
            values.add(person.getSocialSecurityNumber());
        }

        // Suppression de la dernière virgule et ajout de la clause WHERE
        String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1) + " WHERE id = ?";
        values.add(person.getId());

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la personne: " + e.getMessage());
        }
    }

    /**
     * Retrieves a Person from the database by its ID.
     *
     * @param id The ID of the person to retrieve
     * @return The Person object if found, null otherwise
     * @throws SQLException If database access error occurs
     */
    public Person findById(int id) {
        String sql = "SELECT * FROM person WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractPersonFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error in getting person by id: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves all Person records from the database with their associated addresses.
     *
     * @return List of Person objects with their addresses, empty list if none found
     * @throws SQLException If database access error occurs
     */
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT p.*,\n" +
                "a.id as idAddress, a.*\n" +
                "FROM Person p\n" +
                "LEFT JOIN Knows k ON p.Id = k.IdPerson\n" +
                "LEFT JOIN Address a ON k.IdAddress = a.Id;";
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Person person = extractPersonFromResultSet(resultSet);
                person.addAddress(extractAddressFromResultSet(resultSet));
                persons.add(person);
            }
        } catch (SQLException e) {
            System.err.println("Error from getting all persons: " + e.getMessage());
        }
        return persons;
    }

    // Extracts person data from ResultSet
    private Person extractPersonFromResultSet(ResultSet rs) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setFirstName(rs.getString("firstName"));
        person.setLastName(rs.getString("lastName"));
        String genderCode = rs.getString("gender");
        person.setGender("M".equals(genderCode) ? Gender.MALE : Gender.FEMALE);
        person.setAge(rs.getInt("age"));
        person.setPlaceOfBirth(rs.getString("placeOfBirth"));
        person.setSocialSecurityNumber(rs.getLong("socialSecurityNumber"));
        return person;
    }

    /**
     * Deletes a person record from the database
     *
     * @param id The ID of the person to delete
     * @throws SQLException If database access error occurs
     */
    public void deletePerson(int id) {
        String sql = "DELETE FROM person WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la personne: " + e.getMessage());
        }
    }

    // Extracts address data from ResultSet 
    private Address extractAddressFromResultSet(ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setId(rs.getInt("idAddress"));
        address.setStreetNumber(rs.getInt("streetNumber"));
        address.setStreetName(rs.getString("streetName"));
        address.setPostalCode(rs.getInt("postalCode"));
        address.setCityName(rs.getString("cityName"));
        return address;
    }
}