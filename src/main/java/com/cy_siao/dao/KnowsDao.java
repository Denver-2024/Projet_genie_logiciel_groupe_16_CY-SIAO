package com.cy_siao.dao;

import com.cy_siao.model.Knows;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for managing Knows entities in the database
 */
public class KnowsDao {
    //Database utility instance for managing connections
    private final DatabaseUtil databaseUtil;

    /**
     * Constructs a new KnowsDao with a DatabaseUtil instance
     */
    public KnowsDao() {
        this.databaseUtil = new DatabaseUtil();
    }

    /**
     * Creates a new Knows relationship in the database
     *
     * @param knows The Knows entity to create
     */
    public void create(Knows knows) {
        String sql = "INSERT INTO Knows (IdPerson, IdAddress) VALUES (?, ?)";
        try (Connection connect = databaseUtil.getConnection();
             PreparedStatement pst = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, knows.getIdPerson());
            pst.setInt(2, knows.getIdAddress());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("An error occurred when trying to create Knows: " + e.getMessage());
        }
    }

    /**
     * Finds a Knows relationship by person ID and address ID
     *
     * @param personId  The ID of the person
     * @param addressId The ID of the address
     * @return The found Knows entity or null if not found
     */
    public Knows findByIds(int personId, int addressId) {
        String sql = "SELECT * FROM Knows WHERE IdPerson = ? AND IdAddress = ?";
        try (Connection connect = databaseUtil.getConnection();
             PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, personId);
            pst.setInt(2, addressId);
            try (ResultSet rset = pst.executeQuery()) {
                if (rset.next()) {
                    return extractKnowsFromResultSet(rset);
                }
            }
        } catch (SQLException e) {
            System.err.println("An error occurred when trying to find Knows: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves all Knows relationships from the database
     *
     * @return List of all Knows entities
     */
    public List<Knows> findAll() {
        List<Knows> knowledge = new ArrayList<>();
        String sql = "SELECT * FROM Knows";
        try (Connection connect = databaseUtil.getConnection();
             Statement st = connect.createStatement();
             ResultSet rset = st.executeQuery(sql)) {
            while (rset.next()) {
                int personId = rset.getInt("IdPerson");
                int addressId = rset.getInt("IdAddress");
                knowledge.add(new Knows(personId, addressId));
            }
        } catch (SQLException e) {
            System.err.println("An error occurred when trying to find all Knows: " + e.getMessage());
        }
        return knowledge;
    }

    /**
     * Deletes a Knows relationship from the database
     *
     * @param personId  The ID of the person
     * @param addressId The ID of the address
     */
    public void delete(int personId, int addressId) {
        String sql = "DELETE FROM Knows WHERE IdPerson = ? AND IdAddress = ?";
        try (Connection connect = databaseUtil.getConnection();
             PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, personId);
            pst.setInt(2, addressId);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("An error occurred when trying to delete Knows: " + e.getMessage());
        }
    }

    //Extracts a Knows object from a ResultSet
    private Knows extractKnowsFromResultSet(ResultSet rset) {
        try {
            int personId = rset.getInt("IdPerson");
            int addressId = rset.getInt("IdAddress");
            return new Knows(personId, addressId);
        } catch (SQLException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        return null;
    }
}