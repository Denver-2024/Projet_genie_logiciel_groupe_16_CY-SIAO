package com.cy_siao.model;

import com.cy_siao.model.person.Person;
import com.cy_siao.model.person.Gender;


/**
 * Represents restriction rules applied to rooms (age and gender).
 */
public class RestrictionType {

    private int id;
    private String label;
    private Gender genderRestriction;
    private Integer minAge;
    private Integer maxAge;


    /**
     * Constructor used for RestrictionTypeDao
     * @param id l'id
     * @param label le nom
     * @param genderRestriction le genre de la restriction (MALE ou FEMALE)
     * @param minAge l'age minimal autorisé
     * @param maxAge l'age maximal autorisé
     */
    public RestrictionType(int id, String label, Gender genderRestriction, Integer minAge, Integer maxAge) {
        this.id = id;
        this.label = label;
        this.genderRestriction = genderRestriction;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public RestrictionType(String label, Gender genderRestriction, Integer minAge, Integer maxAge) {
        this.label = label;
        this.genderRestriction = genderRestriction;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }
    public RestrictionType(String label, int minAge, int maxAge) {
        this.label = label;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }
    public RestrictionType(String label, Gender genderRestriction) {
        this.label = label;
        this.genderRestriction = genderRestriction;
    }


    /**
     * Default constructor used for RestrictionTypeDao
     */
    public RestrictionType() {
    }

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

    public boolean isRespectedBy(Person person) {
        int age = person.getAge();
        return genderRestriction==person.getGender()&&
                age >= minAge &&
                age <= maxAge;
    }

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