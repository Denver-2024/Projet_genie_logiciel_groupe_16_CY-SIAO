package com.cy_siao.dao;

import com.cy_siao.model.RestrictionType;
import com.cy_siao.model.Room;
import com.cy_siao.model.person.Gender;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object class for handling Room entities in the database.
 * Provides CRUD operations for Room objects.
 */
public class RoomDao {

    //Database utility instance for handling connections
    private final DatabaseUtil databaseUtil;

    /**
     * Constructor initializes database utility.
     */
    public RoomDao() {
        this.databaseUtil = new DatabaseUtil();
    }

    /**
     * Creates a new room record in the database.
     *
     * @param room The Room object to be created
     */
    public void create(Room room) {
        String sql = "INSERT INTO room (name, nbbedsmax) VALUES (?, ?)";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, room.getName());
            pstmt.setInt(2, room.getNbBedsMax());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                room.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Error in creating room: " + e.getMessage());
        }
    }

    /**
     * Retrieves a room by its ID from the database.
     *
     * @param id The ID of the room to find
     * @return Room object if found, null otherwise
     */
    public Room findById(int id) {
        String sql = "SELECT * FROM room WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractRoomFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in finding room by id: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves all rooms from the database including their restrictions.
     *
     * @return List of all rooms with their associated restrictions
     */
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT r.*, rt.*,rt.id as idRestrictionType\n" +
                "FROM room r\n" +
                "LEFT JOIN restrictionRoom rr ON r.id= rr.idRoom\n" +
                "LEFT JOIN restrictionType rt ON rr.idRestrictionType = rt.id;";
        try (Connection conn = databaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Room room = extractRoomFromResultSet(rs);
                room.addRestriction(extractRestrictionFromResultSet(rs));
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.err.println("Error in finding all rooms: " + e.getMessage());
        }
        return rooms;
    }

    /**
     * Updates an existing room record in the database.
     *
     * @param room The Room object containing updated information
     */
    public void update(Room room) {
        String sql = "UPDATE room SET name = ?, nbbedsmax = ? WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, room.getName());
            pstmt.setInt(2, room.getNbBedsMax());
            pstmt.setInt(3, room.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in updating room: " + e.getMessage());
        }
    }

    /**
     * Deletes a room record from the database.
     *
     * @param id The ID of the room to delete
     * @return true if the delete is a success
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM room WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error in deleting room: " + e.getMessage());
            return false;
        }
    }

    //Extracts room data from result set and creates Room object
    private Room extractRoomFromResultSet(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("id"));
        room.setName(rs.getString("name"));
        room.setNbBedsMax(rs.getInt("nbbedsmax"));
        return room;
    }

    //Extracts restriction data from result set and creates RestrictionType object
    private RestrictionType extractRestrictionFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("idRestrictionType");
        return RestrictionTypeDao.getRestrictionType(rs, id);
    }
}