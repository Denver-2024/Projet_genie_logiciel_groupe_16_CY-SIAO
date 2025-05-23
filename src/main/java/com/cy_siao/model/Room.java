package com.cy_siao.model;

import com.cy_siao.model.RestrictionType;
import com.cy_siao.model.person.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represents a room that contains beds and applies restrictions.
 * Allows management of beds, restrictions, and compatibility checks.
 */
public class Room {

    //Room identifier
    private int id;
    //Room name 
    private String name;
    //Maximum number of beds allowed in room
    private int nbBedsMax;
    //List of beds in room
    private List<Bed> beds = new ArrayList<>();
    //List of restrictions applied to room
    private List<RestrictionType> restrictions = new ArrayList<>();

    /**
     * Default constructor
     */
    public Room() {
    }

    /**
     * Constructor with parameters
     *
     * @param name      Name of the room
     * @param nbBedsMax Maximum number of beds allowed
     */
    public Room(String name, int nbBedsMax) {
        this.name = name;
        this.nbBedsMax = nbBedsMax;
    }

    /**
     * Gets the room ID
     *
     * @return The room identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the room name
     *
     * @return The name of the room
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the maximum number of beds allowed
     *
     * @return Maximum number of beds
     */
    public int getNbBedsMax() {
        return nbBedsMax;
    }

    /**
     * Sets the room ID
     *
     * @param id Room identifier to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the room name
     *
     * @param name Room name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the maximum number of beds
     *
     * @param nbBedsMax Maximum number of beds to set
     */
    public void setNbBedsMax(int nbBedsMax) {
        this.nbBedsMax = nbBedsMax;
    }

    /**
     * Gets the list of beds in the room
     *
     * @return List of beds
     */
    public List<Bed> getBeds() {
        return beds;
    }

    /**
     * Gets the list of restrictions applied to the room
     *
     * @return List of restrictions
     */
    public List<RestrictionType> getRestrictions() {
        return restrictions;
    }

    /**
     * Adds a bed to the room if there's space.
     *
     * @param bed The bed to add
     * @throws IllegalStateException if room is at maximum capacity
     */
    public void addBed(Bed bed) {
        if (beds.size() < nbBedsMax) {
            beds.add(bed);
        } else {
            throw new IllegalStateException("Maximum number of beds reached for this room.");
        }
    }

    /**
     * Removes a bed from the room.
     *
     * @param bed The bed to remove
     */
    public void removeBed(Bed bed) {
        beds.remove(bed);
    }

    /**
     * Adds a restriction to the room.
     *
     * @param restriction The restriction to add
     */
    public void addRestriction(RestrictionType restriction) {
        if (!restrictions.contains(restriction)) {
            restrictions.add(restriction);
        }
    }

    /**
     * Removes a restriction from the room.
     *
     * @param restriction The restriction to remove
     */
    public void removeRestriction(RestrictionType restriction) {
        restrictions.remove(restriction);
    }

    /**
     * Returns the list of available beds for a given date range.
     *
     * @param dateArrival   The arrival date
     * @param dateDeparture The departure date
     * @return List of available beds for the specified period
     */
    public List<Bed> getAvailableBeds(LocalDate dateArrival, LocalDate dateDeparture) {
        List<Bed> availableBeds = new ArrayList<>();
        for (Bed bed : beds) {
            if (bed.isAvailable(dateArrival, dateDeparture)) {
                availableBeds.add(bed);
            }
        }
        return availableBeds;
    }

    /**
     * Returns a string representation of the Room
     *
     * @return A string containing room details
     */
    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nbBedsMax=" + nbBedsMax +
                '}';
    }

    /**
     * Checks if this Room is equal to another object
     *
     * @param o The object to compare with
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Room room)) return false;
        return id == room.id &&
                nbBedsMax == room.nbBedsMax &&
                Objects.equals(name, room.name);
    }

    /**
     * Generates a hash code for this Room
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, nbBedsMax);
    }
}