package com.cy_siao.dao;

import com.cy_siao.model.Stay;
import com.cy_siao.model.Bed;
import com.cy_siao.model.person.Person;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StayDao {

    private final DatabaseUtil databaseUtil;
    private PersonDao personDao = new PersonDao();
    private BedDao bedDao = new BedDao();

    public StayDao() {
        this.databaseUtil = new DatabaseUtil();
        this.personDao = new PersonDao();
        this.bedDao = new BedDao();
    }

    public void create(Stay stay) {
        String sql = "INSERT INTO stay (IdPerson, IdBed, dateArrival, dateDeparture) VALUES (?, ?, ?, ?)";
        try (Connection conn = databaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, stay.getPerson().getId());
            pstmt.setInt(2, stay.getBed().getId());
            pstmt.setDate(3, Date.valueOf(stay.getDateArrival()));
            pstmt.setDate(4, Date.valueOf(stay.getDateDeparture()));
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                stay.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Error in creating stay: " + e.getMessage());
        }
    }

    public Stay findById(int id) {
        String sql = "SELECT * FROM stay WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractStayFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error in finding a stay by id: " + e.getMessage());
            return null;
        }
        return null;
    }

    public Stay findByPersonId(int id) {
        String sql = "SELECT * FROM stay WHERE IdPerson = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractStayFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error in finding a stay by person id: " + e.getMessage());
            return null;
        }
        return null;
    }

    public Stay findByBedId(int id) {
        String sql = "SELECT * FROM stay WHERE IdBed = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractStayFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error in finding a stay by bed id: " + e.getMessage());
            return null;
        }
        return null;
    }

    public Stay findByBedPerson(int idBed, int idPerson) throws SQLException {
        String sql = "SELECT * FROM stay WHERE IdBed = ? AND IdPerson = ?";
        try(Connection conn = databaseUtil.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setInt(1, idBed);
            pst.setInt(2, idPerson);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                return extractStayFromResultSet(rs);
            }
        }catch (SQLException e) {
            System.err.println("Error in finding a stay by bed id and person id: " + e.getMessage());
            return null;
        }
        return null;
    }

    public List<Stay> findAll() {
        List<Stay> stays = new ArrayList<>();
        String sql = "SELECT * FROM stay";
        try (Connection conn = databaseUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                stays.add(extractStayFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error in finding all stays: " + e.getMessage());
            return null;
        }
        return stays;
    }

    public void update(Stay stay) {
        String sql = "UPDATE stay SET IdPerson = ?, IdBed = ?, dateArrival = ?, dateDeparture = ? WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, stay.getPerson().getId());
            pstmt.setInt(2, stay.getBed().getId());
            pstmt.setDate(3, Date.valueOf(stay.getDateArrival()));
            pstmt.setDate(4, Date.valueOf(stay.getDateDeparture()));
            pstmt.setInt(5, stay.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in updating stay: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM stay WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in deleting stay: " + e.getMessage());
        }
    }

    private Stay extractStayFromResultSet(ResultSet rs) throws SQLException {
        LocalDate dateArrival = rs.getDate("datearrival").toLocalDate();
        LocalDate dateDeparture = rs.getDate("datedeparture").toLocalDate();
        Bed  bed = bedDao.findById(rs.getInt("idBed"));
        Person person = personDao.findById(rs.getInt("idPerson"));
        Stay stay = new Stay(bed, person, dateArrival, dateDeparture);
        stay.setId(rs.getInt("id"));
        return stay;
    }
}
