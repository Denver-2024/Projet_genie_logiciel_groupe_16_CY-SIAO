package com.cy_siao.service;

import com.cy_siao.dao.RoomDao;
import com.cy_siao.dao.BedDao;
import com.cy_siao.model.Bed;
import com.cy_siao.model.Room;
import com.cy_siao.model.RestrictionType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RoomService {

    private final RoomDao roomDao;
    private final BedDao bedDao;

    public RoomService() {
        this.roomDao = new RoomDao();
        this.bedDao = new BedDao();
    }

    // CRUD OPERATIONS

    public void createRoom(Room room) {
        roomDao.create(room);
    }

    public Room getRoomById(int id) {
        return roomDao.findById(id);
    }

    public List<Room> getAllRooms() {
        return roomDao.findAll();
    }

    public void updateRoom(Room room) {
        roomDao.update(room);
    }

    public void deleteRoom(int id) {
        roomDao.delete(id);
    }

    // BUSINESS METHODS

    public void addBedToRoom(Room room, Bed bed) {
        room.addBed(bed); // already checks max number of beds
        bedDao.update(bed);
    }

    public void removeBedFromRoom(Room room, Bed bed) {
        room.removeBed(bed);
        bedDao.update(bed);
    }

    public void addRestrictionToRoom(Room room, RestrictionType restriction) {
        room.addRestriction(restriction);
        roomDao.update(room);
    }

    public void removeRestrictionFromRoom(Room room, RestrictionType restriction) {
        room.removeRestriction(restriction);
        roomDao.update(room);
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