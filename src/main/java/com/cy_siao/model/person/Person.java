package com.cy_siao.model.person;

import com.cy_siao.model.RestrictionType;

/**
 * Represents a person with various personal attributes such as ID, name,
 * gender, age, place of birth, social security number, and an associated address.
 * This class also allows gender specification and provides functionality to manage addresses.
 */
public class Person {

    private int id; // the id of the person
    private String lastName;
    private String firstName;
    private Gender gender;
    private int age;
    private String placeOfBirth;
    private int socialSecurityNumber;
    private Address address;
    private RestrictionType restrictionType;

    /**
     * Default constructor
     */
    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public int getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(int socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public RestrictionType getRestrictionType() {
        return restrictionType;
    }

    public void setRestrictionType(RestrictionType restrictionType) {
        this.restrictionType = restrictionType;
    }

    /**
     * @param address the person's address
     * @return
     */
    public void addAddress(Address address) {
        this.address = address;
    }
}