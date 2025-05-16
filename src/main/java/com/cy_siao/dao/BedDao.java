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

    public void create(Bed bed) throws SQLException {
        String sql = "INSERT INTO bed (isDouble,isOccupied,idRoom) VALUES (?,?,?)";
        try (Connection conn = databaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setBoolean(1, bed.isItDouble());
            pstmt.setBoolean(2, bed.isOccupied());
            pstmt.setInt(3, bed.getIdRoom());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    bed.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating person failed, no ID obtained.");
                }
            }
        }
    }

    public Bed findById(int id) {
        String sql = "SELECT * FROM bed WHERE id = ?";
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
        String sql = "SELECT * FROM bed";
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
        String sql = "UPDATE bed SET isDouble = ?,isOccupied = ?, idRoom= ? WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, bed.isItDouble());
            pstmt.setBoolean(2, bed.isOccupied());
            pstmt.setInt(3, bed.getIdRoom());
            pstmt.setInt(4, bed.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in updating bed: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM bed WHERE id = ?";
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
        bed.setId(rs.getInt("id"));
        bed.setIdRoom(rs.getInt("idRoom"));
        if( rs.getBoolean("isDouble") ){bed.isDouble();}
        return bed;
    }
}
