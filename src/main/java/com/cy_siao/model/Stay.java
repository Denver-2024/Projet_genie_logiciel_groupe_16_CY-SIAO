package com.cy_siao.model;

import com.cy_siao.model.Bed;
import com.cy_siao.model.person.Person;

import java.time.LocalDate;

/**
 * Represents a stay in a bed by a person for a specific time period.
 * Manages the relationship between beds, persons and stay dates.
 */
public class Stay {

    private int id; // Unique identifier for the stay
    private Bed bed; // Associated bed object 
    private int idBed; // ID of the associated bed
    private int idPerson; // ID of the associated person
    private Person person; // Associated person object
    private LocalDate dateArrival; // Date when stay begins
    private LocalDate dateDeparture; // Date when stay ends
    private boolean hasLeft = false; //set on false by default

    /**
     * Default constructor.
     */
    public Stay() {
    }

    /**
     * Constructor with object references.
     *
     * @param bed           The bed associated with this stay
     * @param person        The person staying
     * @param dateArrival   Start date of the stay
     * @param dateDeparture End date of the stay
     */
    public Stay(Bed bed, Person person, LocalDate dateArrival, LocalDate dateDeparture) {
        this.bed = bed;
        this.idBed = bed.getId();
        this.person = person;
        this.idPerson = person.getId();
        this.dateArrival = dateArrival;
        this.dateDeparture = dateDeparture;
    }

    /**
     * Constructor with IDs.
     *
     * @param idBed         ID of the bed
     * @param idPerson      ID of the person
     * @param dateArrival   Start date of the stay
     * @param dateDeparture End date of the stay
     */
    public Stay(int idBed, int idPerson, LocalDate dateArrival, LocalDate dateDeparture) {
        this.idBed = idBed;
        this.idPerson = idPerson;
        this.dateArrival = dateArrival;
        this.dateDeparture = dateDeparture;
    }

    /**
     * Gets the stay's unique identifier.
     *
     * @return The stay ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the stay's unique identifier.
     *
     * @param id The ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the bed ID for this stay.
     *
     * @param idBed The bed ID to set
     */
    public void setIdBed(int idBed) {
        this.idBed = idBed;
    }

    /**
     * Sets the person ID for this stay.
     *
     * @param idPerson The person ID to set
     */
    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    /**
     * Gets the bed associated with this stay.
     *
     * @return The bed object
     */
    public Bed getBed() {
        return bed;
    }

    /**
     * Sets the bed associated with this stay.
     *
     * @param bed The bed to set
     */
    public void setBed(Bed bed) {
        this.bed = bed;
    }

    /**
     * Gets the person associated with this stay.
     *
     * @return The person object
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Gets the ID of the bed associated with this stay.
     *
     * @return The bed ID
     */
    public int getIdBed() {
        return this.idBed;
    }

    /**
     * Gets the ID of the person associated with this stay.
     *
     * @return The person ID
     */
    public int getIdPerson() {
        return this.idPerson;
    }

    /**
     * Sets the person associated with this stay.
     *
     * @param person The person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Gets the arrival date of this stay.
     *
     * @return The arrival date
     */
    public LocalDate getDateArrival() {
        return dateArrival;
    }

    /**
     * Sets the arrival date for this stay.
     *
     * @param dateArrival The arrival date to set
     */
    public void setDateArrival(LocalDate dateArrival) {
        this.dateArrival = dateArrival;
    }

    /**
     * Gets the departure date of this stay.
     *
     * @return The departure date
     */
    public LocalDate getDateDeparture() {
        return dateDeparture;
    }

    /**
     * Sets the departure date for this stay.
     *
     * @param dateDeparture The departure date to set
     */
    public void setDateDeparture(LocalDate dateDeparture) {
        this.dateDeparture = dateDeparture;
    }
    /**
     * getter of hasLeft which correspond if the person leave after his stay
     * 
     * @return hasLeft
     */
    public boolean isHasLeft(){
        return hasLeft;
    }

    /**
     * Sets the value of hasLeft which correspond if the person leave after his stay
     * 
     * @param hasLeft new value of hasLeft
     */
    public void setHasLeft(boolean hasLeft) {
        this.hasLeft = hasLeft;
    }

    /**
     * Attempts to modify the arrival and departure dates.
     * Only allows modifications if new dates are not in the past.
     *
     * @param newArrival   The new arrival date
     * @param newDeparture The new departure date
     * @return true if dates were modified successfully, false otherwise
     */
    public boolean modifyDates(LocalDate newArrival, LocalDate newDeparture) {
        LocalDate today = LocalDate.now();
        if (!newArrival.isBefore(today) && !newDeparture.isBefore(today)) {
            this.dateArrival = newArrival;
            this.dateDeparture = newDeparture;
            return true;
        }
        return false;
    }

    /**
     * Checks if the stay is currently active (today falls between arrival and departure dates).
     *
     * @return true if stay is active, false otherwise
     */
    public boolean isActiveToday() {
        LocalDate today = LocalDate.now();
        return (today.equals(dateArrival) || today.isAfter(dateArrival)) &&
                (today.equals(dateDeparture) || today.isBefore(dateDeparture));
    }

    /**
     * Checks if the stay can be edited (hasn't started yet).
     *
     * @return true if stay can be edited, false if stay has already begun
     */
    public boolean isEditable() {
        LocalDate today = LocalDate.now();
        return !dateArrival.isBefore(today);
    }

    /**
     * Check if the person respect his stay
     * 
     * @return true if the person stay more day than is stay
     */
    public boolean isInconsistent() {
        return !hasLeft && dateDeparture.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return "Stay{" +
                "id=" + id +
                ", bed=" + idBed +
                ", person=" + idPerson +
                ", dateArrival=" + dateArrival +
                ", dateDeparture=" + dateDeparture +
                '}';
    }
}