package com.cy_siao.dao;

import com.cy_siao.model.RestrictionRoom;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object class for managing restriction room in the database.
 * Handles CRUD operations for restriction for the room.
 */
public class RestrictionRoomDao {
    private final DatabaseUtil databaseUtil;
    
    /**
     * Constructor
     */
    public RestrictionRoomDao() {
        this.databaseUtil = new DatabaseUtil();
    }

    /**
     * Add a restricrion at a room in the database
     * @param restrictionRoom to add at the database
     */
    public void create(RestrictionRoom restrictionRoom) {
        String sql = "INSERT INTO RestrictionRoom (IdRoom, IdRestrictionType, logicOperator) VALUES (?, ?, ?)";
        try (Connection connect = databaseUtil.getConnection();
             PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, restrictionRoom.getIdRoom());
            pst.setInt(2, restrictionRoom.getIdRestrictionType());
            pst.setString(3, restrictionRoom.getLogicOperator());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("An error occurred when trying to create RestrictionRoom: " + e.getMessage());
        }
    }

    /**
     * Search a restriction with the id in this restriction
     * @param roomId id of the room
     * @param restrictionTypeId id of the restriction
     * @return The restriction which link at the room and null if there is no restriction link at this room
     */
    public RestrictionRoom findById(int roomId, int restrictionTypeId) {
        String sql = "SELECT * FROM RestrictionRoom WHERE IdRoom = ? AND IdRestrictionType = ?";
        try (Connection connect = databaseUtil.getConnection();
             PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, roomId);
            pst.setInt(2, restrictionTypeId);
            try (ResultSet rset = pst.executeQuery()) {
                if (rset.next()) {
                    return extractFromResultSet(rset);
                }
            }
        } catch (SQLException e) {
            System.err.println("An error occurred when trying to find RestrictionRoom: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all restriction room 
     * @return list of all restriction room of the database
     */
    public List<RestrictionRoom> findAll() {
        List<RestrictionRoom> restrictionRooms = new ArrayList<>();
        String sql = "SELECT * FROM RestrictionRoom";
        try (Connection connect = databaseUtil.getConnection();
             Statement st = connect.createStatement();
             ResultSet rset = st.executeQuery(sql)) {
            while (rset.next()) {
                restrictionRooms.add(extractFromResultSet(rset));
            }
        } catch (SQLException e) {
            System.err.println("An error occurred when trying to find all RestrictionRooms: " + e.getMessage());
        }
        return restrictionRooms;
    }

    /**
     * Get restriction room link at a room
     * @param idRoom id of room
     * @return the list of restriction link at this room
     */
    public List<RestrictionRoom> findByIdRoom(int idRoom) {
        List<RestrictionRoom> restrictionRooms = new ArrayList<>();
        String sql = "SELECT * FROM RestrictionRoom WHERE IdRoom = ?";
        try (Connection connect = databaseUtil.getConnection();
            PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, idRoom);
            try (ResultSet rset = pst.executeQuery()) {
                while (rset.next()) {
                    restrictionRooms.add(extractFromResultSet(rset));
            }
            }
        } catch (SQLException e) {
            System.err.println("An error occurred when trying to find all RestrictionRooms: " + e.getMessage());
        }
        return restrictionRooms;
    }

    /**
     * Modify a restriction room in database
     * @param restrictionRoom restriction room at modify with modification
     */
    public void update(RestrictionRoom restrictionRoom) {
        String sql = "UPDATE RestrictionRoom SET logic_operator = ? WHERE IdRoom = ? AND IdRestrictionType = ?";
        try (Connection connect = databaseUtil.getConnection();
             PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setString(1, restrictionRoom.getLogicOperator());
            pst.setInt(2, restrictionRoom.getIdRoom());
            pst.setInt(3, restrictionRoom.getIdRestrictionType());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("An error occurred when trying to update RestrictionRoom: " + e.getMessage());
        }
    }

    /**
     * Delete a restriction room of the database
     * @param roomId id of room link
     * @param restrictionTypeId id of restriction link
     * @return true if the delete was a success
     */
    public boolean delete(int roomId, int restrictionTypeId) {
        String sql = "DELETE FROM RestrictionRoom WHERE IdRoom = ? AND IdRestrictionType = ?";
        try (Connection connect = databaseUtil.getConnection();
             PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setInt(1, roomId);
            pst.setInt(2, restrictionTypeId);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("An error occurred when trying to delete RestrictionRoom: " + e.getMessage());
            return false;
        }
    }

    private RestrictionRoom extractFromResultSet(ResultSet rset){
        try {
        int roomId = rset.getInt("IdRoom");
        int restrictionTypeId = rset.getInt("IdRestrictionType");
        String logicOperator = rset.getString("logicOperator");
        return new RestrictionRoom(roomId, restrictionTypeId, logicOperator);
        }
        catch (SQLException e) {
            System.err.println("An error occurred when trying to delete RestrictionRoom: " + e.getMessage());
        }
        return null;  
    }

    /**
     * Search room restriction in data base with id of room
     * @param roomId the id of room
     * @return the list of restriction link at this room
     * @throws SQLException if there is an error with sql request
     */
    public List<RestrictionRoom> findByRoomId(int roomId) throws SQLException {
        String sql = "SELECT * FROM RestrictionRoom WHERE IdRoom = ?";
        try( Connection conn = databaseUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, roomId);
            try( ResultSet rs = pstmt.executeQuery()){
                List<RestrictionRoom> restrictionRooms = new ArrayList<>();
                while(rs.next()){
                    restrictionRooms.add(extractFromResultSet(rs));
                }
                return restrictionRooms;
            }
        }
    }
}
