package com.cy_siao.model;

import com.cy_siao.model.person.Person;
import com.cy_siao.model.person.Gender;


/**
 * Represents restriction rules applied to rooms (age and gender).
 */
public class RestrictionType {

    private int id;
    private String description;
    private Gender GenderRestriction;
    private Integer minAge;
    private Integer maxAge;

    public RestrictionType(int id, String description, Gender GenderRestriction, Integer minAge, Integer maxAge) {
        this.id = id;
        this.description = description;
        this.GenderRestriction = GenderRestriction;
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
        return GenderRestriction;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

}
