package com.cy_siao.dao;

import com.cy_siao.model.person.Person;
import com.cy_siao.model.person.Gender;
import com.cy_siao.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PersonDao is a Data Access Object (DAO) class responsible for managing
 * database operations related to the Person entity. This class provides
 * Create, Read, Update, and Delete (CRUD) operations for Person records.
 */
public class PersonDao {

    /**
     * Inserts a new Person into the database.
     *
     * @param person the Person entity to create
     */
    public void createPerson(Person person) {
        String sql = "INSERT INTO person (first_name, last_name, gender, age, place_of_birth, social_security_number) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getGender().toString());
            statement.setInt(4, person.getAge());
            statement.setString(5, person.getPlaceOfBirth());
            statement.setInt(6, person.getSocialSecurityNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(Gender.valueOf(resultSet.getString("gender")));
                person.setAge(resultSet.getInt("age"));
                person.setPlaceOfBirth(resultSet.getString("place_of_birth"));
                person.setSocialSecurityNumber(resultSet.getInt("social_security_number"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates an existing Person in the database.
     *
     * @param person the Person entity with updated details
     */
    public void updatePerson(Person person) {
        String sql = "UPDATE person SET first_name = ?, last_name = ?, gender = ?, age = ?, place_of_birth = ?, " +
                "social_security_number = ? WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getGender().toString());
            statement.setInt(4, person.getAge());
            statement.setString(5, person.getPlaceOfBirth());
            statement.setInt(6, person.getSocialSecurityNumber());
            statement.setInt(7, person.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            e.printStackTrace();
        }
    }
}
