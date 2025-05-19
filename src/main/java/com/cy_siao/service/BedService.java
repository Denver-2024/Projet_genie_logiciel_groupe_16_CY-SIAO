package com.cy_siao.service;

import com.cy_siao.dao.BedDao;
import com.cy_siao.model.Bed;
import com.cy_siao.model.person.Person;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * 
 */
public class BedService {

    private final BedDao bedDao;

    /**
     * 
     */
    public BedService() {
        this.bedDao = new BedDao();
    }

    // CRUD OPERATIONS

    /**
     * 
     * @param bed
     */
    public void createBed(Bed bed) {
        bedDao.create(bed);
    }

    /**
     * 
     * @param id
     * @return
     */
    public Bed getBedById(int id) {
        return bedDao.findById(id);
    }

    /**
     * 
     * @return
     */
    public List<Bed> getAllBeds() {
        return bedDao.findAll();
    }

    /**
     * 
     * @param bed
     */
    public void updateBed(Bed bed) {
        bedDao.update(bed);
    }

    /**
     * 
     * @param id
     */
    public void deleteBed(int id) {
        bedDao.delete(id);
    }

    // BUSINESS METHODS

    /**
     * Checks if a bed is available for the given date range.
     * 
     * @param bed
     * @param dateArrival
     * @param dateDeparture
     */
    public boolean isBedAvailable(Bed bed, LocalDate dateArrival, LocalDate dateDeparture) {
        return bed.isAvailable(dateArrival, dateDeparture);
    }

    /**
     * Checks whether a bed is currently occupied.
     * 
     * @param bed
     */
    public boolean isOccupied(Bed bed) {
        return bed.isOccupied();
    }

    /**
     * Marks a bed as double.
     * 
     * @param bed 
     */
    public void makeDouble(Bed bed) {
        bed.isDouble();
    }
}