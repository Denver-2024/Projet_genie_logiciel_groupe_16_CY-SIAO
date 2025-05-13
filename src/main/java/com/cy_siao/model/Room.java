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

    private int id;
    private String name;
    private int nbBedsMax;

    private List<Bed> beds = new ArrayList<>();
    private List<RestrictionType> restrictions = new ArrayList<>();

    /**
     * Default constructor
     */
    public Room() {
    }

    /**
     * Constructor with parameters
     */
    public Room(int id, String name, int nbBedsMax) {
        this.id = id;
        this.name = name;
        this.nbBedsMax = nbBedsMax;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNbBedsMax() {
        return nbBedsMax;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNbBedsMax(int nbBedsMax) {
        this.nbBedsMax = nbBedsMax;
    }

    public List<Bed> getBeds() {
        return beds;
    }

    public List<RestrictionType> getRestrictions() {
        return restrictions;
    }

    /**
     * Adds a bed to the room if there's space.
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
     */
    public void removeBed(Bed bed) {
        beds.remove(bed);
    }

    /**
     * Adds a restriction to the room.
     */
    public void addRestriction(RestrictionType restriction) {
        if (!restrictions.contains(restriction)) {
            restrictions.add(restriction);
        }
    }

    /**
     * Removes a restriction from the room.
     */
    public void removeRestriction(RestrictionType restriction) {
        restrictions.remove(restriction);
    }

    /**
     * Checks if a person respects all the room's restrictions.
     */
    public boolean isCompatible(Person person) {
        for (RestrictionType restriction : restrictions) {
            if (!restriction.isRespectedBy(person)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the list of available beds for a given date range.
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

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nbBedsMax=" + nbBedsMax +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Room room)) return false;
        return id == room.id &&
                nbBedsMax == room.nbBedsMax &&
                Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nbBedsMax);
    }
}
