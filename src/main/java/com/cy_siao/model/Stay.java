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

    public Stay() {
    }

    public Stay(Bed bed, Person person, LocalDate dateArrival, LocalDate dateDeparture) {
        this.bed = bed;
        this.person = person;
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

    public Bed getBed() {
        return bed;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    public Person getPerson() {
        return person;
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
