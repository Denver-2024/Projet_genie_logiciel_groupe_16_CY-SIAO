package com.cy_siao.service;

import com.cy_siao.dao.RestrictionRoomDao;
import com.cy_siao.dao.RestrictionTypeDao;
import com.cy_siao.dao.RoomDao;
import com.cy_siao.dao.BedDao;
import com.cy_siao.model.Bed;
import com.cy_siao.model.RestrictionRoom;
import com.cy_siao.model.Room;
import com.cy_siao.model.RestrictionType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Service class that handles all room-related business logic including CRUD operations
 * and management of beds and restrictions.
 */
public class RoomService {

    //Data access object for Room entities
    private final RoomDao roomDao;
    //Data access object for Bed entities 
    private final BedDao bedDao;
    //Data access object for RestrictionRoom entities
    private final RestrictionRoomDao restrictionRoomDao;
    //Data access object for RestrictionType entities
    private RestrictionTypeDao restrictionTypeDao;

    /**
     * Constructs a new RoomService with all required DAOs initialized
     */
    public RoomService() {
        this.roomDao = new RoomDao();
        this.bedDao = new BedDao();
        this.restrictionRoomDao = new RestrictionRoomDao();
        this.restrictionTypeDao = new RestrictionTypeDao();
    }

    // CRUD OPERATIONS

    /**
     * Creates a new room in the database
     *
     * @param room The room entity to create
     */
    public void createRoom(Room room) {
        roomDao.create(room);
    }

    /**
     * Retrieves a room by its ID
     *
     * @param id The ID of the room to find
     * @return The found room or null if not found
     */
    public Room getRoomById(int id) {
        return roomDao.findById(id);
    }

    /**
     * Retrieves all rooms from the database
     *
     * @return List of all rooms
     */
    public List<Room> getAllRooms() {
        return roomDao.findAll();
    }

    /**
     * Updates an existing room in the database
     *
     * @param room The room entity with updated information
     */
    public void updateRoom(Room room) {
        roomDao.update(room);
    }

    /**
     * Deletes a room from the database
     *
     * @param id The ID of the room to delete
     * @return true of the delete is a success
     */
    public boolean deleteRoom(int id) {
        return roomDao.delete(id);
    }

    // BUSINESS METHODS

    /**
     * Adds a bed to a room and updates the database
     *
     * @param room The room to add the bed to
     * @param bed  The bed to be added
     */
    public void addBedToRoom(Room room, Bed bed) {
        if (room.addBed(bed)){
            bedDao.update(bed);
        }
    }

    /**
     * Removes a bed from a room and updates the database
     *
     * @param room The room to remove the bed from
     * @param bed  The bed to be removed
     */
    public void removeBedFromRoom(Room room, Bed bed) {
        room.removeBed(bed);
        bedDao.update(bed);
    }

    /**
     * Adds a restriction to a room using AND as the default logical operator
     *
     * @param room        The room who needs a restriction
     * @param restriction The restriction to be added to the room
     * @throws SQLException If database operation fails
     */
    public void addRestrictionToRoom(Room room, RestrictionType restriction) throws SQLException {
        this.addRestrictionToRoom(room, restriction, "AND");
    }

    /**
     * Adds a restriction to a specified room with the given logic operator.
     * This method ensures the restriction is associated with the room and
     * persists the relationship in the database.
     *
     * @param room          The room to which the restriction will be added
     * @param restriction   The restriction that will be applied to the room
     * @param logicOperator The logical operator (e.g., AND, OR) defining how
     *                      the restriction interacts with other restrictions
     * @throws SQLException If database operation fails
     */
    public void addRestrictionToRoom(Room room, RestrictionType restriction, String logicOperator) throws SQLException {
        boolean exists = restrictionTypeDao.findAll().stream().anyMatch(r -> r.equals(restriction));
        if (!exists) {
            restrictionTypeDao.create(restriction);
        }
        room.addRestriction(restriction);
        restrictionRoomDao.create(new RestrictionRoom(room.getId(), restriction.getId(), logicOperator));
    }

    /**
     * Removes a restriction from a room and updates the database
     *
     * @param room        The room to remove the restriction from
     * @param restriction The restriction to be removed
     */
    public void removeRestrictionFromRoom(Room room, RestrictionType restriction) {
        room.removeRestriction(restriction);
        roomDao.update(room);
    }

    /**
     * Gets all available beds in a room for a given time period
     *
     * @param room          The room to check
     * @param dateArrival   The arrival date
     * @param dateDeparture The departure date
     * @return List of available beds
     */
    public List<Bed> getAvailableBeds(Room room, LocalDate dateArrival, LocalDate dateDeparture) {
        return room.getAvailableBeds(dateArrival, dateDeparture);
    }

    /**
     * Checks if a room has any available beds for a given time period
     *
     * @param room          The room to check
     * @param dateArrival   The arrival date
     * @param dateDeparture The departure date
     * @return True if beds are available, false otherwise
     */
    public boolean hasAvailableBeds(Room room, LocalDate dateArrival, LocalDate dateDeparture) {
        return !getAvailableBeds(room, dateArrival, dateDeparture).isEmpty();
    }

    /**
     * Checks if a room is at maximum bed capacity
     *
     * @param room The room to check
     * @return True if room is full, false otherwise
     */
    public boolean isRoomFull(Room room) {
        return room.getBeds().size() >= room.getNbBedsMax();
    }
}