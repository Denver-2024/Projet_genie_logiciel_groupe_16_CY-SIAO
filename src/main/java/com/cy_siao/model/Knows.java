package com.cy_siao.model;
/*import com.person.Person;
import com.person.Address;*/

/**
 * Represents a relationship between a person and an address they know.
 * Used to track addresses that people are familiar with.
 */
public class Knows {
    // ID of the person who knows the address
    private int idPerson;
    // ID of the address that is known
    private int idAddress;

    /**
     * Creates a new Knows relationship between a person and address
     *
     * @param idPerson  ID of the person
     * @param idAddress ID of the address
     */
    public Knows(int idPerson, int idAddress) {
        this.idPerson = idPerson;
        this.idAddress = idAddress;
    }

    /**
     * getter of idPerson
     * @return The ID of the person
     */
    public int getIdPerson() {
        return idPerson;
    }

    /**
     * setter of idPerson
     * @param idPerson The ID of the person to set
     */
    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    /**
     * getter of adress return id of the adress
     * @return The ID of the address
     */
    public int getIdAddress() {
        return idAddress;
    }

    /**
     * Setter of adress
     * @param idAddress The ID of the address to set
     */
    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    /**
     * Override to string of adress
     * @return the id of the person and the id of the adress in string to print
     */
    @Override
    public String toString() {
        return "Knows{" +
                "personId=" + idPerson +
                ", addressId=" + idAddress +
                '}';
    }
}