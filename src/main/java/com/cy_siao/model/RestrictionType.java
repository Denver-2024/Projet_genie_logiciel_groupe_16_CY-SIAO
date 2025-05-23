package com.cy_siao.model;

import com.cy_siao.model.person.Person;
import com.cy_siao.model.person.Gender;

/**
 * Represents restriction rules applied to rooms (age and gender).
 * This class defines restrictions for rooms based on age ranges and gender.
 */
public class RestrictionType {


    private int id; //ID of the restriction type
    private String label; //Label/description of the restriction
    private Gender genderRestriction; //Gender restriction (MALE or FEMALE) - null if no gender restriction is set.
    private Integer minAge; // minimun age allowed - null if no age restriction is set.
    private Integer maxAge; // maximum age allowed - null if no age restriction is set.

    /**
     * Constructor used for RestrictionTypeDao
     *
     * @param id                The ID of the restriction type
     * @param label             The label/description
     * @param genderRestriction The gender restriction (MALE or FEMALE)
     * @param minAge            The minimum age allowed
     * @param maxAge            The maximum age allowed
     */
    public RestrictionType(int id, String label, Gender genderRestriction, Integer minAge, Integer maxAge) {
        this.id = id;
        this.label = label;
        this.genderRestriction = genderRestriction;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    /**
     * Constructor without id parameter
     *
     * @param label             The label/description
     * @param genderRestriction The gender restriction
     * @param minAge            The minimum age allowed
     * @param maxAge            The maximum age allowed
     */
    public RestrictionType(String label, Gender genderRestriction, Integer minAge, Integer maxAge) {
        this.label = label;
        this.genderRestriction = genderRestriction;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    /**
     * Constructor for age-only restrictions
     *
     * @param label  The label/description
     * @param minAge The minimum age allowed
     * @param maxAge The maximum age allowed
     */
    public RestrictionType(String label, int minAge, int maxAge) {
        this.label = label;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    /**
     * Constructor for gender-only restrictions
     *
     * @param label             The label/description
     * @param genderRestriction The gender restriction
     */
    public RestrictionType(String label, Gender genderRestriction) {
        this.label = label;
        this.genderRestriction = genderRestriction;
    }

    /**
     * Default constructor used for RestrictionTypeDao
     */
    public RestrictionType() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Gender getGenderRestriction() {
        return genderRestriction;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setGenderRestriction(Gender genderRestriction) {
        this.genderRestriction = genderRestriction;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * Checks if a person meets the restrictions criteria
     *
     * @param person The person to check against restrictions
     * @return true if person meets all restrictions, false otherwise
     */
    public boolean isRespectedBy(Person person) {
        int age = person.getAge();
        return genderRestriction == person.getGender() &&
                age >= minAge &&
                age <= maxAge;
    }

    /**
     * Returns a string representation of the RestrictionType
     *
     * @return String containing all restriction details
     */
    @Override
    public String toString() {
        return "restrictionType{" +
                "id= " + id +
                ", description= " + label +
                ", minAge= " + minAge +
                ", maxAge= " + maxAge +
                ", genderRestriction= " + genderRestriction +
                "}";
    }
}