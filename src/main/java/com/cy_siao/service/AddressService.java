package com.cy_siao.service;

import com.cy_siao.dao.AddressDao;
import com.cy_siao.model.person.Address;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class to manage Address entities.
 * Provides abstraction over DAO for business logic handling.
 */
public class AddressService {

    private final AddressDao addressDao;

    public AddressService() {
        this.addressDao = new AddressDao();
    }

    /**
     * Creates a new address entry.
     *
     * @param address The address to create
     * @throws SQLException if a database access error occurs
     */
    public void createAddress(Address address) throws SQLException {
        addressDao.create(address);
    }

    /**
     * Finds an address by its ID.
     *
     * @param id The ID of the address
     * @return The found Address or null if not found
     * @throws SQLException if a database access error occurs
     */
    public Address getAddressById(int id) throws SQLException {
        return addressDao.findById(id);
    }

    /**
     * Retrieves all addresses from the database.
     *
     * @return A list of all Address entries
     * @throws SQLException if a database access error occurs
     */
    public List<Address> getAllAddresses() throws SQLException {
        return addressDao.findAll();
    }

    /**
     * Updates an existing address.
     *
     * @param address The address with updated data
     * @throws SQLException if a database access error occurs
     */
    public void updateAddress(Address address) throws SQLException {
        addressDao.update(address);
    }

    /**
     * Deletes an address by ID.
     *
     * @param id The ID of the address to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteAddress(int id) throws SQLException {
        addressDao.delete(id);
    }
}