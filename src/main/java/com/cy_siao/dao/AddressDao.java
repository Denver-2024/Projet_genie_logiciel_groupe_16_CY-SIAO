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
        String sql = "INSERT INTO address (streetNumber, streetName, postalCode, cityName) VALUES (?, ?, ?, ?)";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, address.getStreetNumber());
            pstmt.setString(2, address.getStreetName());
            pstmt.setInt(3, address.getPostalCode());
            pstmt.setString(4, address.getCityName());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    address.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating address failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in creation of the address: " + e.getMessage());
        }
    }

    public Address findById(int id) {
        String sql = "SELECT * FROM address WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractAddressFromResultSet(rs);
                }
            } catch (SQLException e) {
                System.err.println("Error in finding address by id: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error in finding address by id: " + e.getMessage());
        }
        return null;
    }

    /**
     *
     * @param personId the id of the person we want to find the addresses
     * @return The list of addresses of the person or null
     */
    public List<Address> findByPersonId(int personId) throws SQLException {
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT a.* FROM address a JOIN Knows k ON a.id = k.idAddress WHERE k.idPerson = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, personId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    addresses.add(extractAddressFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in finding addresses by person id: " + e.getMessage());
        }
        return addresses;
    }
    public List<Address> findAll() {
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT * FROM address";
        try (Connection conn = databaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                addresses.add(extractAddressFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error in finding all addresses: " + e.getMessage());
        }
        return addresses;
    }

    public void update(Address address) {
        String sql = "UPDATE address SET streetNumber = ?, streetName = ?, postalCode = ?, cityName = ? WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, address.getStreetNumber());
            pstmt.setString(2, address.getStreetName());
            pstmt.setInt(3, address.getPostalCode());
            pstmt.setString(4, address.getCityName());
            pstmt.setInt(5, address.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in updating address: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM address WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in deleting address: " + e.getMessage());
        }
    }

    private Address extractAddressFromResultSet(ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setId(rs.getInt("id"));
        address.setStreetNumber(rs.getInt("streetNumber"));
        address.setStreetName(rs.getString("streetName"));
        address.setPostalCode(rs.getInt("postalCode"));
        address.setCityName(rs.getString("cityName"));
        return address;
    }

}