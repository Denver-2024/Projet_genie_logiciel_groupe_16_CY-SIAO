package com.cy_siao.model;
/*import com.person.Person;
import com.person.Address;*/

public class Knows {
    private int personId;
    private int addressId;

    public Knows(int personId, int addressId) {
        this.personId = personId;
        this.addressId = addressId;
    }

    // Getters & Setters
    public int getIdPerson() {
        return personId;
    }

    public void setIdPerson(int personId) {
        this.personId = personId;
    }

    public int getIdAddress() {
        return addressId;
    }

    public void setIdAdress(int addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "Knows{" +
                "personId=" + personId +
                ", addressId=" + addressId +
                '}';
    }
}
