package com.cy_siao.service;

import com.cy_siao.dao.BedDao;
import com.cy_siao.dao.RoomDao;
import com.cy_siao.model.Bed;
import com.cy_siao.model.person.Person;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Service class that handles business logic for Bed entities
 */
public class BedService {

    //Data access object for bed persistence
    private final BedDao bedDao;
    private final RoomDao roomDao;

    /**
     * Default constructor that initializes the BedDao
     */
    public BedService() {
        this.bedDao = new BedDao();
        this.roomDao = new RoomDao();
    }

    // CRUD OPERATIONS

    /**
     * Creates a new bed record in the database
     *
     * @param bed The bed entity to be created
     */
    public void createBed(Bed bed) {
        int restPlace = roomDao.restPlace(bed.getIdRoom());
        if (restPlace > 0) {
            bedDao.create(bed);
        }
    }

    /**
     * Retrieves a bed from the database by its ID
     *
     * @param id The unique identifier of the bed
     * @return The bed entity if found, null otherwise
     */
    public Bed getBedById(int id) {
        return bedDao.findById(id);
    }

    /**
     * Retrieves all beds from the database
     *
     * @return List of all bed entities
     */
    public List<Bed> getAllBeds() {
        return bedDao.findAll();
    }

    /**
     * Updates an existing bed record in the database
     *
     * @param bed The bed entity with updated information
     */
    public void updateBed(Bed bed) {
        bedDao.update(bed);
    }

    /**
     * Deletes a bed record from the database
     *
     * @param id The unique identifier of the bed to delete
     * @return true if the bed was delete
     */
    public boolean deleteBed(int id) {
        return bedDao.delete(id);
    }

    // BUSINESS METHODS

    /**
     * Checks if a bed is available for the given date range.
     *
     * @param bed           The bed to check availability for
     * @param dateArrival   The planned arrival date
     * @param dateDeparture The planned departure date
     * @return true if the bed is available for the given date range, false otherwise
     */
    public boolean isBedAvailable(Bed bed, LocalDate dateArrival, LocalDate dateDeparture) {
        return bed.isAvailable(dateArrival, dateDeparture);
    }

    /**
     * Checks whether a bed is currently occupied.
     *
     * @param bed The bed to check occupancy for
     * @return true if the bed is currently occupied, false otherwise
     */
    public boolean isOccupied(Bed bed) {
        return bed.isOccupied();
    }

    /**
     * Marks a bed as double-sized.
     *
     * @param bed The bed to mark as double
     */
    public void makeDouble(Bed bed) {
        bed.isDouble();
    }
}