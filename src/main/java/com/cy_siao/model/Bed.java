package com.cy_siao.model;

import com.cy_siao.model.Stay;
import com.cy_siao.model.person.Person;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isItDouble() {
        return isDouble;
    }

    public void isDouble() {
        this.isDouble = true;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    /**
     * Returns true if the bed is available for the given date range (no overlap with existing stays).
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
     * Assigns a person to this bed for a given period, if available.
     */
    public void assignPerson(Person person, LocalDate dateArrival, LocalDate dateDeparture) {
        if (isAvailable(dateArrival, dateDeparture)) {
            Stay newStay = new Stay(this, person, dateArrival, dateDeparture);
            stays.add(newStay);
        } else {
            throw new IllegalStateException("Bed is not available during the selected period.");
        }
    }

    /**
     * Frees the bed for a given person by removing their stays.
     *
     * @param person Person to remove from the bed
     */
    public void free(Person person) {
        stays.removeIf(stay -> stay.getPerson().equals(person));
    }

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