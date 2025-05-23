package com.cy_siao.service;

import com.cy_siao.dao.AddressDao;
import com.cy_siao.model.person.Address;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class to manage Address entities.
 * Provides abstraction over DAO for business logic handling.
 * Handles CRUD operations for Address objects by delegating to the DAO layer.
 */
public class AddressService {

    //The data access object for Address entities
    private final AddressDao addressDao;

    /**
     * Constructs a new AddressService with its DAO dependency
     */
    public AddressService() {
        this.addressDao = new AddressDao();
    }

    /**
     * Creates a new address entry in the database.
     * Validates and persists the address data.
     *
     * @param address The address object to create
     * @throws SQLException             if a database access error occurs
     * @throws IllegalArgumentException if address is null
     */
    public void createAddress(Address address) throws SQLException {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        addressDao.create(address);
    }

    /**
     * Finds an address by its ID.
     * Retrieves a single address record from the database.
     *
     * @param id The ID of the address to find
     * @return The found Address or null if not found
     * @throws SQLException             if a database access error occurs
     * @throws IllegalArgumentException if id is negative
     */
    public Address getAddressById(int id) throws SQLException {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        return addressDao.findById(id);
    }

    /**
     * Retrieves all addresses from the database.
     * Returns a list containing all address records.
     *
     * @return A list of all Address entries, empty list if none found
     * @throws SQLException if a database access error occurs
     */
    public List<Address> getAllAddresses() throws SQLException {
        return addressDao.findAll();
    }

    /**
     * Updates an existing address in the database.
     * Validates and updates the address data.
     *
     * @param address The address with updated data
     * @throws SQLException             if a database access error occurs
     * @throws IllegalArgumentException if address is null or has no ID
     */
    public void updateAddress(Address address) throws SQLException {
        if (address == null || address.getId() <= 0) {
            throw new IllegalArgumentException("Invalid address or address ID");
        }
        addressDao.update(address);
    }

    /**
     * Deletes an address by ID from the database.
     * Removes the address record permanently.
     *
     * @param id The ID of the address to delete
     * @throws SQLException             if a database access error occurs
     * @throws IllegalArgumentException if id is negative
     */
    public void deleteAddress(int id) throws SQLException {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        addressDao.delete(id);
    }
}