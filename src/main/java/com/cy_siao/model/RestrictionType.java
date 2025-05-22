package com.cy_siao.model;

import com.cy_siao.model.person.Person;
import com.cy_siao.model.person.Gender;

import java.util.Objects;


/**
 * Represents restriction rules applied to rooms (age and gender).
 */
public class RestrictionType {


    /**
     * Constructor used for RestrictionTypeDao
     * @param id l'id
     * @param label le nom
     * @param genderRestriction le genre de la restriction (MALE ou FEMALE)
     * @param minAge l'age minimal autorisé
     * @param maxAge l'age maximal autorisé
     */
    private int id;
    private String label;
    private Gender genderRestriction;
    private Integer minAge;
    private Integer maxAge;

    public RestrictionType(int id, String label, Gender genderRestriction, Integer minAge, Integer maxAge) {
        this.id = id;
        this.label = label;
        this.genderRestriction = genderRestriction;
        this.minAge = (minAge != null) ? minAge : 0;
        this.maxAge = (maxAge != null) ? maxAge : 200;
    }

    public RestrictionType(String label, Gender genderRestriction, Integer minAge, Integer maxAge) {
        this.label = label;
        this.genderRestriction = genderRestriction;
        this.minAge = (minAge != null) ? minAge : 0;
        this.maxAge = (maxAge != null) ? maxAge : 200;
    }

    public RestrictionType(String label, Integer minAge, Integer maxAge) {
        this(label, null, minAge, maxAge); // appel du constructeur précédent avec genderRestriction à null
    }

    public RestrictionType(String label, Gender genderRestriction) {
        this(label, genderRestriction, 0, 200); // valeurs par défaut d’âge
    }

    /**
     * Default constructor used for RestrictionTypeDao
     */
    public RestrictionType() {
        this.minAge = 0;
        this.maxAge = 200;
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

    public String getGenderNameOrEmpty() {
        return (genderRestriction != null) ? genderRestriction.name() : " ";
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

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public boolean isRespectedBy(Person person) {
        int age = person.getAge();
        boolean genderOk = (genderRestriction == null) || (genderRestriction == person.getGender());
        return genderOk && isAgeWithinLimits(age);
    }

    private boolean isAgeWithinLimits(int age) {
        int min = (minAge != null) ? minAge : 0;
        int max = (maxAge != null) ? maxAge : 200;
        return age >= min && age <= max;
    }

    @Override
    public String toString() {
        return "RestrictionType{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", genderRestriction=" + genderRestriction +
                ", minAge=" + minAge +
                ", maxAge=" + maxAge +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestrictionType that)) return false;

        if (id != that.id) return false;
        if (!Objects.equals(label, that.label)) return false;
        if (genderRestriction != that.genderRestriction) return false;
        if (!Objects.equals(minAge, that.minAge)) return false;
        return Objects.equals(maxAge, that.maxAge);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (genderRestriction != null ? genderRestriction.hashCode() : 0);
        result = 31 * result + (minAge != null ? minAge.hashCode() : 0);
        result = 31 * result + (maxAge != null ? maxAge.hashCode() : 0);
        return result;
    }
}
