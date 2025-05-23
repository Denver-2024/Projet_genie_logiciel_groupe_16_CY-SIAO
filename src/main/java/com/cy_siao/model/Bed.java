package com.cy_siao.model;

import com.cy_siao.model.Stay;
import com.cy_siao.model.person.Person;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Represents a bed in a room, with maximum capacity and an ID reference to the room it belongs to.
 * Handles assignment of persons and availability checking.
 */
public class Bed {

    private int id; // Unique identifier for the bed
    private boolean isDouble = false; // Boolean to say if the bed is double or single
    private int idRoom; // ID of the room this bed belongs to

    // Internal list of stays — not shown in the diagram but required for methods to work
    private List<Stay> stays = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Bed() {
    }

    /**
     * Constructor with room ID and max occupancy.
     *
     * @param idRoom Room ID
     */
    public Bed(int idRoom) {
        this.idRoom = idRoom;
    }

    /**
     * Constructor with room ID and bed type.
     *
     * @param idRoom   Room ID
     * @param isDouble Whether the bed is double or single
     */
    public Bed(int idRoom, boolean isDouble) {
        this.idRoom = idRoom;
        this.isDouble = isDouble;
    }

    /**
     * Gets the bed's unique identifier.
     *
     * @return The bed ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this bed.
     *
     * @param id The unique identifier to set for the bed
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Checks if the bed is a double bed.
     *
     * @return true if double bed, false if single bed
     */
    public boolean isItDouble() {
        return isDouble;
    }

    /**
     * Sets this bed as a double bed.
     */
    public void isDouble() {
        this.isDouble = true;
    }

    /**
     * Gets the number of places for this bed.
     *
     * @return 2 if double bed, 1 if single bed
     */
    public int getNbPlace() {
        return isDouble ? 2 : 1;
    }

    /**
     * Gets the bed type as a JavaFX property.
     *
     * @return BooleanProperty indicating if bed is double
     */
    public BooleanProperty isDoubleProperty() {
        return new SimpleBooleanProperty(isDouble);
    }

    /**
     * Gets the room ID this bed belongs to.
     *
     * @return The room ID
     */
    public int getIdRoom() {
        return idRoom;
    }

    /**
     * Gets the list of stays associated with this bed.
     *
     * @return List of stays
     */
    public List<Stay> getStays() {
        return stays;
    }

    /**
     * Sets the room ID for this bed.
     *
     * @param idRoom The room ID to set
     */
    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    /**
     * Returns true if the bed is available for the given date range (no overlap with existing stays).
     *
     * @param dateArrival   The arrival date to check
     * @param dateDeparture The departure date to check
     * @return true if bed is available, false if occupied during date range
     */
    public boolean isAvailable(LocalDate dateArrival, LocalDate dateDeparture) {
        for (Stay stay : stays) {
            if (stay.getDateArrival().isBefore(dateDeparture) &&
                    stay.getDateDeparture().isAfter(dateArrival)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a stay to this bed's list of stays.
     *
     * @param stay The stay to add
     */
    public void addStay(Stay stay) {
        stays.add(stay);
    }

    /**
     * Checks if the bed currently has any stays.
     *
     * @return true if bed has stays, false if empty
     */
    public boolean isOccupied() {
        return !stays.isEmpty();
    }

    @Override
    public String toString() {
        return "Bed{" +
                "id=" + id +
                ", isDouble=" + isDouble +
                ", idRoom=" + idRoom +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Bed bed)) return false;
        return id == bed.id &&
                isDouble == bed.isDouble &&
                idRoom == bed.idRoom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isDouble, idRoom);
    }
}