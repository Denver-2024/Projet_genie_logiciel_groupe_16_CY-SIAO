package com.cy_siao.model;

import com.cy_siao.model.person.Person;
import com.cy_siao.model.person.Gender;


/**
 * Represents restriction rules applied to rooms (age and gender).
 */
public class RestrictionType {

    private int id;
    private String description;
    private Gender genderRestriction;
    private int minAge;
    private int maxAge;

    public RestrictionType(int id, String description, Gender GenderRestriction, int minAge, int maxAge){
        this.id = id;
        this.description = description;
        this.genderRestriction = GenderRestriction;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public void setId( int id){ // add seter id
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenderRestriction(Gender genderRestriction) {
        this.genderRestriction = genderRestriction;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public void setMaxAge(int maxAge) {
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
                ", description= " + description +
                ", minAge= " + minAge +
                ", maxAge= " + maxAge +
                ", genderRestriction= " + genderRestriction +
                "}";
    }
}
