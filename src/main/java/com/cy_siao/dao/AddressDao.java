package com.cy_siao.dao;

import com.cy_siao.model.person.Address;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDao {
    private final DatabaseUtil databaseUtil;

    public AddressDao() {
        this.databaseUtil = new DatabaseUtil();
    }

    public void create(Address address) throws SQLException {
        String sql = "INSERT INTO addresses (street_number, street_name, postal_code, city_name) VALUES (?, ?, ?, ?)";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, address.getStreetNumber());
            pstmt.setString(2, address.getStreetName());
            pstmt.setInt(3, address.getPostalCode());
            pstmt.setString(4, address.getCityName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in creation of the address: " + e.getMessage());
        }
    }

    public Address findById(int id) throws SQLException {
        String sql = "SELECT * FROM addresses WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractAddressFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Address> findAll() throws SQLException {
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT * FROM addresses";
        try (Connection conn = databaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                addresses.add(extractAddressFromResultSet(rs));
            }
        }
        return addresses;
    }

    public void update(Address address) throws SQLException {
        String sql = "UPDATE addresses SET street_number = ?, street_name = ?, postal_code = ?, city_name = ? WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, address.getStreetNumber());
            pstmt.setString(2, address.getStreetName());
            pstmt.setInt(3, address.getPostalCode());
            pstmt.setString(4, address.getCityName());
            pstmt.setInt(5, address.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM addresses WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    private Address extractAddressFromResultSet(ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setId(rs.getInt("id"));
        address.setStreetNumber(rs.getInt("street_number"));
        address.setStreetName(rs.getString("street_name"));
        address.setPostalCode(rs.getInt("postal_code"));
        address.setCityName(rs.getString("city_name"));
        return address;
    }

}
