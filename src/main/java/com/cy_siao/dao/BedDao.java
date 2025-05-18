package com.cy_siao.dao;

import com.cy_siao.model.Bed;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BedDao {
    private final DatabaseUtil databaseUtil;

    public BedDao() {
        this.databaseUtil = new DatabaseUtil();
    }

    public void create(Bed bed) {
    String sql = "INSERT INTO Bed (nbPlacesMax, IdRoom) VALUES (?, ?)";
    try (Connection conn = databaseUtil.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
        pstmt.setInt(1, bed.isItDouble() ? 2 : 1);
        pstmt.setInt(2, bed.getIdRoom());
        pstmt.executeUpdate();

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                bed.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating bed failed, no ID obtained.");
            }
        }
    } catch (SQLException e) {
        // Log l'erreur et/ou notifier l'utilisateur
        System.err.println("Erreur lors de la création du lit: " + e.getMessage());
        // Optionnel : relancer une RuntimeException pour interrompre le flux
        throw new RuntimeException("Erreur de base de données", e);
    }
}

    public Bed findById(int id) {
        String sql = "SELECT * FROM Bed WHERE Id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractBedFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in finding bed by id: " + e.getMessage());
        }
        return null;
    }

    public List<Bed> findAll() {
        List<Bed> beds = new ArrayList<>();
        String sql = "SELECT * FROM Bed";
        try (Connection conn = databaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                beds.add(extractBedFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error in finding all beds: " + e.getMessage());
        }
        return beds;
    }

    public void update(Bed bed) {
        String sql = "UPDATE Bed SET nbPlacesMax = ?, IdRoom = ? WHERE Id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bed.isItDouble() ? 2 : 1);
            pstmt.setInt(2, bed.getIdRoom());
            pstmt.setInt(3, bed.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in updating bed: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Bed WHERE Id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in deleting bed: " + e.getMessage());
        }
    }

    private Bed extractBedFromResultSet(ResultSet rs) throws SQLException {
        Bed bed = new Bed();
        bed.setId(rs.getInt("Id"));
        bed.setIdRoom(rs.getInt("IdRoom"));
        if (rs.getInt("nbPlacesMax") == 2) {
            bed.isDouble();
        }
        return bed;
    }
}