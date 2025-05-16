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
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
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
