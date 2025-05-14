package com.cy_siao.dao;

import com.cy_siao.model.Stay;
import com.cy_siao.model.Bed;
import com.cy_siao.model.person.Person;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StayDao {

    private final DatabaseUtil databaseUtil;

    public StayDao() {
        this.databaseUtil = new DatabaseUtil();
    }

    public void create(Stay stay) throws SQLException {
        String sql = "INSERT INTO stay (personid, bedid, datearrival, datedeparture) VALUES (?, ?, ?, ?)";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, stay.getPerson().getId());
            pstmt.setInt(2, stay.getBed().getId());
            pstmt.setDate(3, Date.valueOf(stay.getDateArrival()));
            pstmt.setDate(4, Date.valueOf(stay.getDateDeparture()));
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                // Tu peux stocker l'ID si tu l'ajoutes dans la classe Stay
                // stay.setId(rs.getInt(1));
            }
        }
    }

    public Stay findById(int id, Person person, Bed bed) throws SQLException {
        String sql = "SELECT * FROM stay WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractStayFromResultSet(rs, person, bed);
            }
        }
        return null;
    }

    public List<Stay> findAll(Person person, Bed bed) throws SQLException {
        List<Stay> stays = new ArrayList<>();
        String sql = "SELECT * FROM stay";
        try (Connection conn = databaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                stays.add(extractStayFromResultSet(rs, person, bed));
            }
        }
        return stays;
    }

    public void update(Stay stay) throws SQLException {
        String sql = "UPDATE stay SET personid = ?, bedid = ?, datearrival = ?, datedeparture = ? WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, stay.getPerson().getId());
            pstmt.setInt(2, stay.getBed().getId());
            pstmt.setDate(3, Date.valueOf(stay.getDateArrival()));
            pstmt.setDate(4, Date.valueOf(stay.getDateDeparture()));
            pstmt.setInt(5, stay.getBed().getId()); // Remplacer si stay a un champ id
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM stay WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    private Stay extractStayFromResultSet(ResultSet rs, Person person, Bed bed) throws SQLException {
        LocalDate dateArrival = rs.getDate("datearrival").toLocalDate();
        LocalDate dateDeparture = rs.getDate("datedeparture").toLocalDate();
        return new Stay(bed, person, dateArrival, dateDeparture);
    }
}
