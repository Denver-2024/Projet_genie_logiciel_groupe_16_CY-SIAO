package com.cy_siao.service;

import com.cy_siao.dao.RestrictionRoomDao;
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
 * 
 */
public class RoomService {

    private final RoomDao roomDao;
    private final BedDao bedDao;
    private final RestrictionRoomDao restrictionRoomDao;

    /**
     * 
     */
    public RoomService() {
        this.roomDao = new RoomDao();
        this.bedDao = new BedDao();
        this.restrictionRoomDao = new RestrictionRoomDao();
    }

    // CRUD OPERATIONS

    /**
     * 
     * @param room
     */
    public void createRoom(Room room) {
        roomDao.create(room);
    }

    /**
     * 
     * @param id
     * @return
     */
    public Room getRoomById(int id) {
        return roomDao.findById(id);
    }

    /**
     * 
     * @return
     */
    public List<Room> getAllRooms() {
        return roomDao.findAll();
    }

    public void updateRoom(Room room) {
        roomDao.update(room);
    }

    /**
     * 
     * @param id
     */
    public void deleteRoom(int id) {
        roomDao.delete(id);
    }

    // BUSINESS METHODS

    /**
     * 
     * @param room
     * @param bed
     */
    public void addBedToRoom(Room room, Bed bed) {
        room.addBed(bed); // already checks max number of beds
        bedDao.update(bed);
    }

    /**
     * 
     * @param room
     * @param bed
     */
    public void removeBedFromRoom(Room room, Bed bed) {
        room.removeBed(bed);
        bedDao.update(bed);
    }

    /**
     *Add a restriction to a room
     * With And for the logic operator automatically
     * @param room the room who need a restriction
     * @param restriction the restriction who must be add to the room
     */
    public void addRestrictionToRoom(Room room, RestrictionType restriction) {
       this.addRestrictionToRoom(room, restriction, "AND");
    }

    /**
     * Adds a restriction to a specified room with the given logic operator.
     * This method ensures the restriction is associated with the room and
     * persists the relationship in the database.
     *
     * @param room the room to which the restriction will be added
     * @param restriction the restriction that will be applied to the room
     * @param logicOperator the logical operator (e.g., AND, OR) defining how
     *                      the restriction interacts with other restrictions
     */
    public void addRestrictionToRoom(Room room, RestrictionType restriction, String logicOperator) {
        room.addRestriction(restriction);
        restrictionRoomDao.create(new RestrictionRoom(room.getId(), restriction.getId(), logicOperator));
    }
    /**
     * 
     * @param room
     * @param restriction
     */
    public void removeRestrictionFromRoom(Room room, RestrictionType restriction) {
        room.removeRestriction(restriction);
        roomDao.update(room);
    }

    /**
     * 
     * @param room
     * @param dateArrival
     * @param dateDeparture
     * @return
     */
    public List<Bed> getAvailableBeds(Room room, LocalDate dateArrival, LocalDate dateDeparture) {
        return room.getAvailableBeds(dateArrival, dateDeparture);
    }

    /**
     * 
     * @param room
     * @param dateArrival
     * @param dateDeparture
     * @return
     */
    public boolean hasAvailableBeds(Room room, LocalDate dateArrival, LocalDate dateDeparture) {
        return !getAvailableBeds(room, dateArrival, dateDeparture).isEmpty();
    }

    /**
     * 
     * @param room
     * @return
     */
    public boolean isRoomFull(Room room) {
        return room.getBeds().size() >= room.getNbBedsMax();
    }
}