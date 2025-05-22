package com.cy_siao.dao;

import com.cy_siao.model.Stay;
import com.cy_siao.model.Bed;
import com.cy_siao.model.person.Gender;
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
        String sql = """
            SELECT s.*, 
                p.id as person_id, p.firstname, p.lastname, p.gender, p.age, 
                b.id as bed_id, b.idroom, b.nbplacesmax
            FROM stay s
            JOIN person p ON s.idperson = p.id
            JOIN bed b ON s.idbed = b.id
            """;
        
        try (Connection conn = databaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                stays.add(extractStayWithAssociationsFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error in finding all stays: " + e.getMessage());
            throw new RuntimeException("Database error", e);
        }
        return stays;
    }

    private Stay extractStayWithAssociationsFromResultSet(ResultSet rs) throws SQLException {
        // Extraction Stay de base
        Stay stay = new Stay();
        stay.setId(rs.getInt("id"));
        stay.setDateArrival(rs.getDate("datearrival").toLocalDate());
        stay.setDateDeparture(rs.getDate("datedeparture").toLocalDate());

        // Extraction Person
        Person person = new Person();
        person.setId(rs.getInt("person_id"));
        person.setFirstName(rs.getString("firstname"));
        person.setLastName(rs.getString("lastname"));
        String genderCode = rs.getString("gender");
        person.setGender("M".equals(genderCode) ? Gender.MALE : Gender.FEMALE);
        person.setAge(rs.getInt("age"));
        stay.setPerson(person);
        stay.setIdPerson(person.getId());

        // Extraction Bed
        Bed bed = new Bed();
        bed.setId(rs.getInt("bed_id"));
        bed.setIdRoom(rs.getInt("idroom"));
        //bed.isDouble(rs.getInt("nbplacesmax")); bizarre pour le isDouble
        stay.setBed(bed);
        stay.setIdBed(bed.getId());

        return stay;
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
        int  idBed = rs.getInt("idBed");
        int idPerson = rs.getInt("idPerson");
        Stay stay = new Stay(idBed, idPerson, dateArrival, dateDeparture);
        stay.setId(rs.getInt("id"));
        return stay;
    }

    public int countActiveStays() {
        int count = 0;
        String sql ="SELECT COUNT(*) FROM stay WHERE dateArrival <= CURRENT_DATE AND dateDeparture >= CURRENT_DATE AND hasLeft =FALSE";
        try(Connection conn = databaseUtil.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                count = rs.getInt(1);
            }
        }catch (SQLException e) {
            System.err.println("Error in counting active stays: " + e.getMessage());
        }
        return count;
    }




    public int countDeparted() {
        String sql = "SELECT COUNT(*) FROM Stay WHERE hasLeft = TRUE";
        try (Connection conn = databaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Error in counting departed stays: " + e.getMessage());
        }
        return 0;
    }


    public int countOngoing() {
        String sql = "SELECT COUNT(*) FROM Stay " +
                "WHERE dateDeparture >= CURRENT_DATE AND hasLeft = FALSE";
        try (Connection conn = databaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Error in counting ongoing stays: " + e.getMessage());
        }
        return 0;
    }


    public int countOverdue() {
        String sql = "SELECT COUNT(*) FROM Stay " +
                "WHERE dateDeparture < CURRENT_DATE AND hasLeft = FALSE";
        try (Connection conn = databaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Error in counting overdue stays: " + e.getMessage());
        }
        return 0;
    }

}
