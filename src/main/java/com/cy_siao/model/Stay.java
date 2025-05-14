package com.cy_siao.model;

import com.cy_siao.model.Bed;
import com.cy_siao.model.person.Person;

import java.util.Date;

public class Stay {

    private Bed bed;
    private Person person;
    private Date dateArrival;
    private Date dateDeparture;

    public Stay() {
    }

    public Stay(Bed bed, Person person, Date dateArrival, Date dateDeparture) {
        this.bed = bed;
        this.person = person;
        this.dateArrival = dateArrival;
        this.dateDeparture = dateDeparture;
    }

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

    public Date getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(Date dateArrival) {
        this.dateArrival = dateArrival;
    }

    public Date getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(Date dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public boolean modifyDates(Date newArrival, Date newDeparture) {
        Date today = new Date();
        if (!newArrival.before(today) && !newDeparture.before(today)) {
            this.dateArrival = newArrival;
            this.dateDeparture = newDeparture;
            return true;
        }
        return false;
    }

    public boolean isActiveToday() {
        Date today = new Date();
        return (today.equals(dateArrival) || today.after(dateArrival)) &&
               (today.equals(dateDeparture) || today.before(dateDeparture));
    }

    public boolean isEditable() {
        Date today = new Date();
        return !dateArrival.before(today);
    }
}
