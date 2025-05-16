package com.cy_siao.service;

import com.cy_siao.dao.RoomDao;
import com.cy_siao.model.Bed;
import com.cy_siao.model.Room;
import com.cy_siao.model.RestrictionType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RoomService {

    private final RoomDao roomDao;

    public RoomService() {
        this.roomDao = new RoomDao();
    }

    // CRUD OPERATIONS

    public void createRoom(Room room) throws SQLException {
        roomDao.create(room);
    }

    public Room getRoomById(int id) throws SQLException {
        return roomDao.findById(id);
    }

    public List<Room> getAllRooms() throws SQLException {
        return roomDao.findAll();
    }

    public void updateRoom(Room room) throws SQLException {
        roomDao.update(room);
    }

    public void deleteRoom(int id) throws SQLException {
        roomDao.delete(id);
    }

    // BUSINESS METHODS

    public void addBedToRoom(Room room, Bed bed) {
        room.addBed(bed); // already checks max number of beds
    }

    public void removeBedFromRoom(Room room, Bed bed) {
        room.removeBed(bed);
    }

    public void addRestrictionToRoom(Room room, RestrictionType restriction) {
        room.addRestriction(restriction);
    }

    public void removeRestrictionFromRoom(Room room, RestrictionType restriction) {
        room.removeRestriction(restriction);
    }

    public List<Bed> getAvailableBeds(Room room, LocalDate dateArrival, LocalDate dateDeparture) {
        return room.getAvailableBeds(dateArrival, dateDeparture);
    }

    public boolean hasAvailableBeds(Room room, LocalDate dateArrival, LocalDate dateDeparture) {
        return !getAvailableBeds(room, dateArrival, dateDeparture).isEmpty();
    }

    public boolean isRoomFull(Room room) {
        return room.getBeds().size() >= room.getNbBedsMax();
    }
}