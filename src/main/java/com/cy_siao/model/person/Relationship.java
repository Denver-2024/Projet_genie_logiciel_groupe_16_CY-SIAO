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
     * Construcor with parameter
     * 
     * @param person1 first person of the relation
     * @param person2 second person of the relation
     * @param relationType type of relation between first and second person
     */
    public Relationship(Person person1, Person person2, String relationType) {
        this.person1 = person1;
        this.person2 = person2;
        this.relationType = relationType;
    }

    /**
     * setter of the first person of the relation
     * 
     * @param person the first person of the relation
     */
    public void setPerson1(Person person) {
        person1 = person;
    }

    /**
     * setter of the second person of the relation
     * 
     * @param person the second person of the relation
     */
    public void setPerson2(Person person) {
        person2 = person;
    }

    /**
     * getter of the first person of the relation
     * 
     * @return the first person of the relation
     */
    public Person getPerson1() {
        return person1;
    }

    /**
     * getter of the second person of the relation
     * 
     * @return the second person of the relation
     */
    public Person getPerson2() {
        return person2;
    }

    /**
     * getter of the type of relationship
     * 
     * @return the type of the relationship
     */
    public String getRelationType() {
        return relationType;
    }

    /**
     * setter to define a type for this relation
     * 
     * @param relationType type of relation
     */
    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    /**
     * Adds a person to this relationship. Supports one or two persons.
     * 
     * @param person who will add to the relationship
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
     * @param gender of the person we want to match with bed
     * @param age of the person we want to match with bed
     * @param arrival date of the person for his stay
     * @param departure date of the person for his stay
     * @param rooms list of rooms where we search beds
     * @return list of bed where we can place the person with this criteria
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

    /**
     * Assigns the people in the relationship to available beds, respecting room sharing if required.
     * 
     * @param rooms list of room to choose a room between them to assign the two people
     * @param arrival date when the two people come
     * @param departure date when the two people go
     * @param sameRoom boolean which are true if the two people need to be in the same room
     * @return true if is possible to find a room or rooms with beds which respect group condition, false otherwise
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
                boolean success1 = true; // demander pourquoi
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

    /**
     * Returns the display of the Relationship.
     *
     * @return the display of the name of two people who compose the relation and the relation type
     */
    @Override
    public String toString() {
        return "Relationship{" +
                "person1=" + (person1 != null ? person1.getFirstName() : "null") +
                ", person2=" + (person2 != null ? person2.getFirstName() : "null") +
                ", relationType='" + relationType + '\'' +
                '}';
    }

    /**
     * Compares this address to the specified object for equality.
     *
     * @param o the object to compare with
     * @return true if the person are equals and relationtype too, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Relationship r)) return false;
        return Objects.equals(person1, r.person1) &&
               Objects.equals(person2, r.person2) &&
               Objects.equals(relationType, r.relationType);
    }

    /**
     * Returns a hash code value for the relationship.
     *
     * @return a hash code value for this relationship
     */
    @Override
    public int hashCode() {
        return Objects.hash(person1, person2, relationType);
    }
}
