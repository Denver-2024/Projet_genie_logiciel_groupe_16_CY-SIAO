package com.cy_siao.dao;

import com.cy_siao.model.person.Person;
import com.cy_siao.model.person.Relationship;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object class for managing relationship data between persons in the database.
 * Handles CRUD operations for relationships between people.
 */
public class RelationshipDao {

    //Database utility instance for connections
    private final DatabaseUtil databaseUtil;
    //Data access object for Person entities
    private final PersonDao personDao;

    /**
     * Constructor initializes database utility and person DAO
     */
    public RelationshipDao() {
        this.databaseUtil = new DatabaseUtil();
        this.personDao = new PersonDao();
    }

    /**
     * Creates a new relationship record in the database
     *
     * @param relationship The relationship object to persist
     * @throws SQLException if a database access error occurs
     */
    public void create(Relationship relationship) throws SQLException {
        String sql = "INSERT INTO relationship (idperson1, idperson2, relationtype) VALUES (?, ?, ?)";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, relationship.getPerson1().getId());
            pstmt.setInt(2, relationship.getPerson2().getId());
            pstmt.setString(3, relationship.getRelationType());
            pstmt.executeUpdate();
        }
    }

    /**
     * Retrieves all relationships from the database
     *
     * @return List of all relationship objects
     * @throws SQLException if a database access error occurs
     */
    public List<Relationship> findAll() throws SQLException {
        List<Relationship> relationships = new ArrayList<>();
        String sql = "SELECT * FROM relationship";
        try (Connection conn = databaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id1 = rs.getInt("idperson1");
                int id2 = rs.getInt("idperson2");
                String relationType = rs.getString("relationtype");

                Person person1 = personDao.findById(id1);
                Person person2 = personDao.findById(id2);

                if (person1 != null && person2 != null) {
                    relationships.add(new Relationship(person1, person2, relationType));
                }
            }
        }
        return relationships;
    }

    /**
     * Deletes a relationship from the database
     *
     * @param relationship The relationship object to delete
     * @throws SQLException if a database access error occurs
     * @return true if the delete is a success
     */
    public boolean delete(Relationship relationship) throws SQLException {
        String sql = "DELETE FROM relationship WHERE idperson1 = ? AND idperson2 = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, relationship.getPerson1().getId());
            pstmt.setInt(2, relationship.getPerson2().getId());
            pstmt.executeUpdate();
            return true;
        }catch (SQLException e) {
            System.err.println("Error when trying this relation: " + e.getMessage());
            return false;
        }
    }

    /**
     * Finds a relationship between two specific persons
     *
     * @param person1 The first person in the relationship
     * @param person2 The second person in the relationship
     * @return The relationship if found, null otherwise
     * @throws SQLException if a database access error occurs
     */
    public Relationship findByPersons(Person person1, Person person2) throws SQLException {
        String sql = "SELECT * FROM relationship WHERE idperson1 = ? AND idperson2 = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, person1.getId());
            pstmt.setInt(2, person2.getId());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String relationType = rs.getString("relationtype");
                    return new Relationship(person1, person2, relationType);
                }
            }
        }
        return null;
    }
}