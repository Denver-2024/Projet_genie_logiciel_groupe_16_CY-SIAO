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
     * The method who update a person in tables
     * @param person the person to update
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
            values.add(person.getGender().toString());
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
     * @param id the ID of the person to retrieve
     * @return the retrieved Person entity, or null if not found
     */
    public Person getPersonById(int id) {
        String sql = "SELECT * FROM person WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractPersonFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Erroe in gettign person by id: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves all Person records from the database.
     * Iterates through the result set and maps each row to a Person object.
     *
     * @return a list of all persons found in the database. Returns an empty list if no records are found.
     */
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM person";
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                persons.add(extractPersonFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error from getting all persons: " + e.getMessage());
        }
        return persons;
    }


    // A method to extract a person from the result set
    private Person extractPersonFromResultSet(ResultSet rs) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setFirstName(rs.getString("first_name"));
        person.setLastName(rs.getString("last_name"));
        person.setGender(Gender.valueOf(rs.getString("gender")));
        person.setAge(rs.getInt("age"));
        person.setPlaceOfBirth(rs.getString("place_of_birth"));
        person.setSocialSecurityNumber(rs.getInt("social_security_number"));
        return person;
    }
    /**
     * Deletes a Person from the database by its ID.
     *
     * @param id the ID of the person to delete
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
}