package com.cy_siao.dao;

import com.cy_siao.model.RestrictionRoom;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestrictionRoomDao {
    private final DatabaseUtil databaseUtil;

    public RestrictionRoomDao() {
        this.databaseUtil = new DatabaseUtil();
    }

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
