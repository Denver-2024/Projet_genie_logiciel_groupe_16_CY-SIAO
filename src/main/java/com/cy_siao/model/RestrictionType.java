package com.cy_siao.model;

import com.cy_siao.model.person.Person;
import com.cy_siao.model.person.Gender;


/**
 * Represents restriction rules applied to rooms (age and gender).
 */
public class RestrictionType {

    private int id;
    private String description;
    private Set<Gender> GenderRestriction;
    private int minAge;
    private int maxAge;

    public RestrictionType(int id, String description, Set<Gender> GenderRestriction, int minAge, int maxAge) {
        this.id = id;
        this.description = description;
        this.GenderRestriction = GenderRestriction;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Set<Gender> getGenderRestriction() {
        return GenderRestriction;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public boolean isRespectedBy(Person person) {
        int age = person.getAge();
        return GenderRestriction.contains(person.getGender()) &&
               age >= minAge &&
               age <= maxAge;
    }
}
