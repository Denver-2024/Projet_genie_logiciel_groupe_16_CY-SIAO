package com.cy_siao.model.person;

import com.cy_siao.model.RestrictionType;

import java.util.Objects;

/**
 * Represents a person with various personal attributes such as ID, name,
 * gender, age, place of birth, social security number, and an associated address.
 * This class manages personal information and restrictions for individuals in the system.
 */
public class Person {

    private int id; // Unique identifier for the person
    private String lastName; // Person's last/family name
    private String firstName; // Person's first/given name 
    private Gender gender; // Person's gender (MALE or FEMALE)
    private int age; // Person's age in years
    private String placeOfBirth; // Person's birthplace
    private int socialSecurityNumber; // Person's social security number
    private Address address; // Person's current address
    private RestrictionType restrictionType; // Any restrictions applicable to this person

    /**
     * Default Constructor used for PersonDao
     * Creates an empty Person object.
     */
    public Person() {
    }

    /**
     * Constructor that initializes a person with their name
     *
     * @param lastName  The person's last name
     * @param firstName The person's first name
     */
    public Person(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    /**
     * Constructor that initializes a person with their name and gender
     *
     * @param lastName  The person's last name
     * @param firstName The person's first name
     * @param gender    The person's gender (MALE or FEMALE)
     * @param age       The person's age in years
     */
    public Person(String lastName, String firstName, Gender gender, int age){
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.age = age;
    }
    /**
     * Gets the person's unique identifier
     *
     * @return The ID of the person
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the person's unique identifier
     *
     * @param id The ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the person's last name
     *
     * @return The last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the person's last name
     *
     * @param lastName The last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the person's first name
     *
     * @return The first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the person's first name
     *
     * @param firstName The first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the person's gender
     *
     * @return The gender enum value
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the person's gender
     *
     * @param gender The gender enum value to set
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Gets the person's age
     *
     * @return The age in years
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the person's age
     *
     * @param age The age in years to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the person's place of birth
     *
     * @return The place of birth
     */
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    /**
     * Sets the person's place of birth
     *
     * @param placeOfBirth The place of birth to set
     */
    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    /**
     * Gets the person's social security number
     *
     * @return The social security number
     */
    public int getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    /**
     * Sets the person's social security number
     *
     * @param socialSecurityNumber The social security number to set
     */
    public void setSocialSecurityNumber(int socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    /**
     * Gets the person's address
     *
     * @return The current address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the person's address
     *
     * @param address The address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gets the person's restriction type
     *
     * @return The current restriction type
     */
    public RestrictionType getRestrictionType() {
        return restrictionType;
    }

    /**
     * Sets the person's restriction type
     *
     * @param restrictionType The restriction type to set
     */
    public void setRestrictionType(RestrictionType restrictionType) {
        this.restrictionType = restrictionType;
    }

    /**
     * Adds or updates the person's address
     *
     * @param address The new address to associate with the person
     */
    public void addAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person person)) return false;
        return id == person.id && age == person.age && socialSecurityNumber == person.socialSecurityNumber && Objects.equals(lastName, person.lastName) && Objects.equals(firstName, person.firstName) && gender == person.gender && Objects.equals(placeOfBirth, person.placeOfBirth) && Objects.equals(address, person.address) && Objects.equals(restrictionType, person.restrictionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, gender, age, placeOfBirth, socialSecurityNumber, address, restrictionType);
    }
}