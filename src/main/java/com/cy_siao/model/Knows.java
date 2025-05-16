package com.cy_siao.model;
/*import com.person.Person;
import com.person.Address;*/

public class Knows {
    private int idPerson;
    private int idAddress;

    public Knows(int idPerson, int idAddress) {
        this.idPerson = idPerson;
        this.idAddress = idAddress;
    }

    // Getters & Setters
    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    @Override
    public String toString() {
        return "Knows{" +
                "personId=" + idPerson +
                ", addressId=" + idAddress +
                '}';
    }
}
