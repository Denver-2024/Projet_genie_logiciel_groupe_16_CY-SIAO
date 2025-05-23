package com.cy_siao.model;

import com.cy_siao.model.Bed;
import com.cy_siao.model.person.Person;

import java.time.LocalDate;

public class Stay {

    private int id;
    private Bed bed;
    private int idBed;
    private int idPerson;
    private Person person;
    private LocalDate dateArrival;
    private LocalDate dateDeparture;
    private boolean hasLeft = false; //set on false by default

    public Stay() {
    }

    public Stay(Bed bed, Person person, LocalDate dateArrival, LocalDate dateDeparture) {
        this.bed = bed;
        this.idBed = bed.getId();
        this.person = person;
        this.idPerson = person.getId();
        this.dateArrival = dateArrival;
        this.dateDeparture = dateDeparture;
    }

    public Stay(int idBed, int idPerson, LocalDate dateArrival, LocalDate dateDeparture) {
        this.idBed = idBed;
        this.idPerson = idPerson;
        this.dateArrival = dateArrival;
        this.dateDeparture = dateDeparture;
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public void setIdBed(int idBed){this.idBed = idBed;}
    
    public void setIdPerson(int idPerson){this.idPerson = idPerson;}

    public Bed getBed() {
        return bed;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    public Person getPerson() {
        return person;
    }

    public int getIdBed() {
        return this.idBed;
    }

    public int getIdPerson() {
        return this.idPerson;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDate getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(LocalDate dateArrival) {
        this.dateArrival = dateArrival;
    }

    public LocalDate getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(LocalDate dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public boolean isHasLeft(){
        return hasLeft;
    }

    public void setHasLeft(boolean hasLeft) {
        this.hasLeft = hasLeft;
    }

    public boolean modifyDates(LocalDate newArrival, LocalDate newDeparture) {
        LocalDate today = LocalDate.now();
        if (!newArrival.isBefore(today) && !newDeparture.isBefore(today)) {
            this.dateArrival = newArrival;
            this.dateDeparture = newDeparture;
            return true;
        }
        return false;
    }

    public boolean isActiveToday() {
        LocalDate today = LocalDate.now();
        return (today.equals(dateArrival) || today.isAfter(dateArrival)) &&
                (today.equals(dateDeparture) || today.isBefore(dateDeparture));
    }

    public boolean isEditable() {
        LocalDate today = LocalDate.now();
        return !dateArrival.isBefore(today);
    }

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
