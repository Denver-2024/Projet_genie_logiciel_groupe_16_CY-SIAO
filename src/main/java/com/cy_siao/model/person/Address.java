package com.cy_siao.model.person;

/**
 * Represents a physical address with street information, postal code and city.
 * Used for storing location data for persons in the system.
 */
public class Address {
    private int id;
    private int streetNumber;
    private String streetName;
    private int postalCode;
    private String cityName;

    /**
     * Default constructor
     */
    public Address() {
    }

    public Address(int streetNumber, String streetName, int postalCode, String cityName) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.postalCode = postalCode;
        this.cityName = cityName;
    }

    /**
     * Gets the address ID
     *
     * @return the address ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the address ID
     *
     * @param id the address ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the street number
     *
     * @return the street number
     */
    public int getStreetNumber() {
        return streetNumber;
    }

    /**
     * Sets the street number
     *
     * @param streetNumber the street number to set
     */
    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * Gets the street name
     *
     * @return the street name
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets the street name
     *
     * @param streetName the street name to set
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Gets the postal code
     *
     * @return the postal code
     */
    public int getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code
     *
     * @param postalCode the postal code to set
     */
    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the city name
     *
     * @return the city name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Sets the city name
     *
     * @param cityName the city name to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}