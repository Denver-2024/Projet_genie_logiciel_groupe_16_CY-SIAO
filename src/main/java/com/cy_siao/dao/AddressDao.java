package com.cy_siao.dao;

import com.cy_siao.model.person.Address;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object class for managing Address entities in the database
 */
public class AddressDao {
    //Database utility instance for handling connections
    private final DatabaseUtil databaseUtil;

    /**
     * Default constructor initializing database utility
     */
    public AddressDao() {
        this.databaseUtil = new DatabaseUtil();
    }

    /**
     * Creates a new address record in the database
     *
     * @param address the Address object to persist
     * @throws SQLException if a database error occurs
     */
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

    /**
     * Finds an address by its ID
     *
     * @param id the ID of the address to find
     * @return the found Address object or null if not found
     */
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
     * Finds all addresses associated with a person
     *
     * @param personId the id of the person to find addresses for
     * @return List of addresses associated with the person
     * @throws SQLException if a database error occurs
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

    /**
     * Retrieves all addresses from the database
     *
     * @return List of all addresses
     */
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

    /**
     * Updates an existing address in the database
     *
     * @param address the Address object to update
     */
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

    /**
     * Deletes an address from the database
     *
     * @param id the ID of the address to delete
     * @return true if the delete is a success
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM address WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error in deleting address: " + e.getMessage());
            return false;
        }
    }

    //Helper method to create Address object from ResultSet
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