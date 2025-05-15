package com.cy_siao.service;

import com.cy_siao.dao.BedDao;
import com.cy_siao.model.Bed;
import com.cy_siao.model.person.Person;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BedService {

    private final BedDao bedDao;

    public BedService() {
        this.bedDao = new BedDao();
    }

    // CRUD OPERATIONS

    public void createBed(Bed bed) throws SQLException {
        bedDao.create(bed);
    }

    public Bed getBedById(int id) throws SQLException {
        return bedDao.findById(id);
    }

    public List<Bed> getAllBeds() throws SQLException {
        return bedDao.findAll();
    }

    public void updateBed(Bed bed) throws SQLException {
        bedDao.update(bed);
    }

    public void deleteBed(int id) throws SQLException {
        bedDao.delete(id);
    }

    // BUSINESS METHODS

    /**
     * Checks if a bed is available for the given date range.
     */
    public boolean isBedAvailable(Bed bed, LocalDate dateArrival, LocalDate dateDeparture) {
        return bed.isAvailable(dateArrival, dateDeparture);
    }

    /**
     * Assigns a person to the bed if it's available.
     */
    public boolean assignPersonToBed(Bed bed, Person person, LocalDate dateArrival, LocalDate dateDeparture) {
        return bed.assignPerson(person, dateArrival, dateDeparture);
    }

    /**
     * Frees the bed for the given person.
     */
    public void freeBed(Bed bed, Person person) {
        bed.free(person);
    }

    /**
     * Checks whether a bed is currently occupied.
     */
    public boolean isOccupied(Bed bed) {
        return bed.isOccupied();
    }

    /**
     * Marks a bed as double.
     */
    public void makeDouble(Bed bed) {
        bed.isDouble();
    }
}