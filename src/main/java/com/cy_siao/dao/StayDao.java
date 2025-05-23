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

/**
 * Data Access Object for handling stay-related database operations.
 */
public class StayDao {

    //Database utility instance for database connections
    private final DatabaseUtil databaseUtil;
    //DAO for person-related operations
    private PersonDao personDao = new PersonDao();
    //DAO for bed-related operations 
    private BedDao bedDao = new BedDao();

    /**
     * Constructor initializing the required DAOs and database utility.
     */
    public StayDao() {
        this.databaseUtil = new DatabaseUtil();
        this.personDao = new PersonDao();
        this.bedDao = new BedDao();
    }

    /**
     * Creates a new stay record in the database.
     *
     * @param stay The stay object to be created
     */
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

    /**
     * Finds a stay by its ID.
     *
     * @param id The ID of the stay to find
     * @return The found stay or null if not found
     */
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

    /**
     * Finds a stay by person ID.
     *
     * @param id The person ID to search for
     * @return The found stay or null if not found
     */
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

    /**
     * Finds a stay by bed ID.
     *
     * @param id The bed ID to search for
     * @return The found stay or null if not found
     */
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

    /**
     * Finds a stay by both bed ID and person ID.
     *
     * @param idBed    The bed ID
     * @param idPerson The person ID
     * @return The found stay or null if not found
     * @throws SQLException if a database error occurs
     */
    public Stay findByBedPerson(int idBed, int idPerson) throws SQLException {
        String sql = "SELECT * FROM stay WHERE IdBed = ? AND IdPerson = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, idBed);
            pst.setInt(2, idPerson);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return extractStayFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error in finding a stay by bed id and person id: " + e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * Retrieves all stays with their associated person and bed information.
     *
     * @return List of all stays
     */
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

    //Extracts a Stay object with its associated Person and Bed from a ResultSet
    private Stay extractStayWithAssociationsFromResultSet(ResultSet rs) throws SQLException {
        Stay stay = new Stay();
        stay.setId(rs.getInt("id"));
        stay.setDateArrival(rs.getDate("datearrival").toLocalDate());
        stay.setDateDeparture(rs.getDate("datedeparture").toLocalDate());

        Person person = new Person();
        person.setId(rs.getInt("person_id"));
        person.setFirstName(rs.getString("firstname"));
        person.setLastName(rs.getString("lastname"));
        String genderCode = rs.getString("gender");
        person.setGender("M".equals(genderCode) ? Gender.MALE : Gender.FEMALE);
        person.setAge(rs.getInt("age"));
        stay.setPerson(person);
        stay.setIdPerson(person.getId());

        Bed bed = new Bed();
        bed.setId(rs.getInt("bed_id"));
        bed.setIdRoom(rs.getInt("idroom"));
        stay.setBed(bed);
        stay.setIdBed(bed.getId());

        return stay;
    }

    /**
     * Updates an existing stay record.
     *
     * @param stay The stay object with updated information
     */
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

    /**
     * Deletes a stay record by ID.
     *
     * @param id The ID of the stay to delete
     * @return true if the delete is a success
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM stay WHERE id = ?";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error in deleting stay: " + e.getMessage());
            return false;
        }
    }

    //Extracts a basic Stay object from a ResultSet
    private Stay extractStayFromResultSet(ResultSet rs) throws SQLException {
        LocalDate dateArrival = rs.getDate("datearrival").toLocalDate();
        LocalDate dateDeparture = rs.getDate("datedeparture").toLocalDate();
        int idBed = rs.getInt("idBed");
        int idPerson = rs.getInt("idPerson");
        Stay stay = new Stay(idBed, idPerson, dateArrival, dateDeparture);
        stay.setId(rs.getInt("id"));
        return stay;
    }

    /**
     * Counts the number of currently active stays.
     *
     * @return The number of active stays
     */
    public int countActiveStays() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM stay WHERE dateArrival <= CURRENT_DATE AND dateDeparture >= CURRENT_DATE AND hasLeft =FALSE";
        try (Connection conn = databaseUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error in counting active stays: " + e.getMessage());
        }
        return count;
    }

    /**
     * Counts the number of departed stays.
     *
     * @return The number of departed stays
     */
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

    /**
     * Counts the number of ongoing stays.
     *
     * @return The number of ongoing stays
     */
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

    /**
     * Counts the number of overdue stays.
     *
     * @return The number of overdue stays
     */
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