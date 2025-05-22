package com.cy_siao.dao;

import com.cy_siao.model.RestrictionType;
import com.cy_siao.model.Room;
import com.cy_siao.model.person.Gender;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDao {

    private final DatabaseUtil databaseUtil;

    public RoomDao() {
        this.databaseUtil = new DatabaseUtil();
    }

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
                room.addRestriction( extractRestrictionFromResultSet(rs));
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.err.println("Error in finding all rooms: " + e.getMessage());
        }
        return rooms;
    }

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

    public void delete(int id) {
        String sql = "DELETE FROM room WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in deleting room: " + e.getMessage());
        }
    }

    private Room extractRoomFromResultSet(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("id"));
        room.setName(rs.getString("name"));
        room.setNbBedsMax(rs.getInt("nbbedsmax"));
        return room;
    }
    private RestrictionType extractRestrictionFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("idRestrictionType");
        return RestrictionTypeDao.getRestrictionType(rs, id);
    }


}
