package com.cy_siao.dao;

import com.cy_siao.model.Knows;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KnowsDao{
    private final DatabaseUtil databaseUtil;

    public KnowsDao() {
        this.databaseUtil = new DatabaseUtil();
    }

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

    public Knows findByIds(int personId, int addressId) {
        String sql = "SELECT * FROM Knows WHERE IdPerson = ? AND IdAddress = ?";
        try (Connection connect = databaseUtil.getConnection();
             PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, personId);
            pst.setInt(2, addressId);
            try (ResultSet rset = pst.executeQuery()) {
                if (rset.next()) {
                    return extractFromResultSet(rset);
                }
            }
        } catch (SQLException e) {
            System.err.println("An error occurred when trying to find Knows: " + e.getMessage());
        }
        return null;
    }

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


private Knows extractFromResultSet(ResultSet rset){
        try {
        int personId = rset.getInt("IdPerson");
        int addressId = rset.getInt("IdAddress");
        return new Knows(personId, addressId);
    }
    catch (SQLException e) {
        System.err.println("An error occurred: " + e.getMessage());
    }
    return null;
}
}
