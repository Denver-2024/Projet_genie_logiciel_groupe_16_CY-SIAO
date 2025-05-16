package com.cy_siao.model.person;

import com.cy_siao.model.Bed;
import com.cy_siao.model.person.Person;
import com.cy_siao.model.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a relationship between two persons (e.g., family, couple, etc.)
 * and provides operations for assigning them to beds and searching availability.
 */
public class Relationship {

    private Person person1;
    private Person person2;
    private String relationType;

    /**
     * Default constructor
     */
    public Relationship() {
    }

    /**
     * Constructor with parameters
     */
    public Relationship(Person person1, Person person2, String relationType) {
        this.person1 = person1;
        this.person2 = person2;
        this.relationType = relationType;
    }

    public Person getPerson1() {
        return person1;
    }

    public Person getPerson2() {
        return person2;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    /**
     * Adds a person to this relationship. Supports one or two persons.
     */
    public void addPerson(Person person) {
        if (person1 == null) {
            this.person1 = person;
        } 
        else if (person2 == null) {
            this.person2 = person;
        } 
        else {
            throw new IllegalStateException("This relationship already has two people.");
        }
    }

    /**
     * Searches available beds matching criteria for the relationship group.
     */
    public List<Bed> searchAvailableBed(String gender, int age, LocalDate arrival, LocalDate departure, List<Room> rooms) {
        List<Bed> matchingBeds = new ArrayList<>();
        for (Room room : rooms) {
            for (Bed bed : room.getAvailableBeds(arrival, departure)) {
                // Example filter: check gender, age, or compatibility here if needed
                matchingBeds.add(bed);
            }
        }
        return matchingBeds;
    }

    // Methode a refaire dans service (stayService)
    /**
     * Assigns the people in the relationship to available beds, respecting room sharing if required.
     */
    public boolean assignGroup(List<Room> rooms, LocalDate arrival, LocalDate departure, boolean sameRoom) {
        List<Bed> allAvailableBeds = new ArrayList<>();

        if (sameRoom) {
            for (Room room : rooms) {
                List<Bed> availableBeds = room.getAvailableBeds(arrival, departure);
                if (availableBeds.size() >= 2) {
                    // Try to assign both persons to beds in the same room
                    Bed bed1 = availableBeds.get(0);
                    Bed bed2 = availableBeds.get(1);
                    boolean success1 = true; // demander pourquoi
                    boolean success2 = true;

                    //success1 = bed1.assignPerson(person1, arrival, departure); // demander pourquoi
                    //success2 = bed2.assignPerson(person2, arrival, departure);
                    return success1 && success2;
                }
            }
        } 
        else {
            for (Room room : rooms) {
                allAvailableBeds.addAll(room.getAvailableBeds(arrival, departure));
            }
            if (allAvailableBeds.size() >= 2) {
                Bed bed1 = allAvailableBeds.get(0);
                Bed bed2 = allAvailableBeds.get(1);
                boolean success1 = true;
                boolean success2 = true;
                //success1 = bed1.assignPerson(person1, arrival, departure);
                //success2 = bed2.assignPerson(person2, arrival, departure);
                return success1 && success2;
            }
        }
        return false; // No suitable beds found
    }

    /**
     * Displays current occupation of persons in the relationship.
     */
    public void displayOccupation() {
        System.out.println("Occupation status:");
        if (person1 != null) {
            System.out.println("- " + person1.getFirstName() + " " + person1.getLastName());
        }
        if (person2 != null) {
            System.out.println("- " + person2.getFirstName() + " " + person2.getLastName());
        }
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "person1=" + (person1 != null ? person1.getFirstName() : "null") +
                ", person2=" + (person2 != null ? person2.getFirstName() : "null") +
                ", relationType='" + relationType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Relationship r)) return false;
        return Objects.equals(person1, r.person1) &&
               Objects.equals(person2, r.person2) &&
               Objects.equals(relationType, r.relationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person1, person2, relationType);
    }
}
