package com.cy_siao.dao;

import com.cy_siao.model.RestrictionType;
import com.cy_siao.model.person.Gender;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestrictionTypeDao {

    private final DatabaseUtil databaseUtil;

    public RestrictionTypeDao() {
        this.databaseUtil = new DatabaseUtil();
    }

    public void create(RestrictionType restriction) throws SQLException {
        String sql = "INSERT INTO restrictiontype (label, minage, maxage, genderrestriction) VALUES (?, ?, ?, ?)";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, restriction.getLabel());
            pstmt.setObject(2, restriction.getMinAge(), Types.INTEGER);
            pstmt.setObject(3, restriction.getMaxAge(), Types.INTEGER);
            pstmt.setString(4, restriction.getGenderRestriction().name().substring(0, 1)); // "M" or "F"
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                restriction.setId(rs.getInt(1));
            }
        }
    }

    public RestrictionType findById(int id) throws SQLException {
        String sql = "SELECT * FROM restrictiontype WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<RestrictionType> findAll() throws SQLException {
        List<RestrictionType> restrictions = new ArrayList<>();
        String sql = "SELECT * FROM restrictiontype";
        try (Connection conn = databaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                restrictions.add(extractFromResultSet(rs));
            }
        }
        return restrictions;
    }

    public void update(RestrictionType restriction) throws SQLException {
        String sql = "UPDATE restrictiontype SET label = ?, minage = ?, maxage = ?, genderrestriction = ? WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, restriction.getLabel());
            pstmt.setObject(2, restriction.getMinAge(), Types.INTEGER);
            pstmt.setObject(3, restriction.getMaxAge(), Types.INTEGER);
            pstmt.setString(4, String.valueOf(restriction.getGenderRestriction().toString().charAt(0))); // "M" or "F"
            pstmt.setInt(5, restriction.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM restrictiontype WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    private RestrictionType extractFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        return getRestrictionType(rs, id);
    }

    static RestrictionType getRestrictionType(ResultSet rs, int id) throws SQLException {
        String label = rs.getString("label");
        Integer minAge = rs.getObject("minage") != null ? rs.getInt("minage") : null;
        Integer maxAge = rs.getObject("maxage") != null ? rs.getInt("maxage") : null;
        String genderCode = rs.getString("genderrestriction");

        Gender gender = "M".equals(genderCode) ? Gender.MALE : Gender.FEMALE;

        return new RestrictionType(id, label, gender, minAge, maxAge);
    }
}