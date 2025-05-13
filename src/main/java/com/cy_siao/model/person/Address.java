package com.cy_siao.model.person;

/**
 * 
 */
public class Address {

    /**
     * Default constructor
     */
    public Address() {
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private int streetNumber;

    /**
     * 
     */
    private String streetName;

    /**
     * 
     */
    private int postalCode;

    /**
     * 
     */
    private String cityName;

    /**
     * 
     */
    private String departmentName;

    /**
     * 
     */
    private String region;



    /**
     * @param person 
     * @return
     */
    public boolean isCompatibleWith(Person person) {
        // TODO implement here
        return false;
    }

}