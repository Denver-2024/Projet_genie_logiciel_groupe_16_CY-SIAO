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

    /**
     * getter of id
     * @return the id of the restriction in database
     */
    public int getId() {
        return id;
    }

    /**
     * getter of the label
     * @return the label of this restriction
     */
    public String getLabel() {
        return label;
    }

    /**
     * getter of the gender restriction
     * @return can be MALE FEMALE or null
     */
    public Gender getGenderRestriction() {
        return genderRestriction;
    }

    /**
     * getter of the minimum age of the restriction
     * @return the minimum age
     */
    public Integer getMinAge() {
        return minAge;
    }

    /**
     * getter of the maximum age of the restriction
     * @return the maximum age
     */
    public Integer getMaxAge() {
        return maxAge;
    }

    /**
     * setter of id for the restriction
     * @param id can be recover from database
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter of a label for this restriction
     * 
     * @param label new label fot the restriction
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * setter of the gender restriction
     * @param genderRestriction be MALE FEMALE or null
     */
    public void setGenderRestriction(Gender genderRestriction) {
        this.genderRestriction = genderRestriction;
    }

    /**
     * Setter of minimum age for this restriction
     * 
     * @param minAge new value of minimum age
     */
    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    /**
     * Setter of maximum age for this restriction
     * 
     * @param maxAge new value of maximum age
     */
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