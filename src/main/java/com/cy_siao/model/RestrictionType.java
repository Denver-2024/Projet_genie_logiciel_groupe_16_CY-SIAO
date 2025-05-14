package com.cy_siao.model;

import com.cy_siao.model.person.Person;

import java.util.Objects;

public class RestrictionType {

    private int id;
    private String restriction;
    private int idSalle;

    public RestrictionType() {
    }

    public RestrictionType(int id, String restriction, int idSalle) {
        this.id = id;
        this.restriction = restriction;
        this.idSalle = idSalle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

    public int getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }

    public boolean isRespectedBy(Person person) {
        if (restriction == null || restriction.isEmpty()) {
            return true;
        }

        String[] rules = restriction.split("&&");
        for (String rule : rules) {
            rule = rule.trim();

            if (rule.startsWith("age>=")) {
                int minAge = Integer.parseInt(rule.substring(5));
                if (person.getAge() < minAge) return false;
            } 
            else if (rule.startsWith("age<=")) {
                int maxAge = Integer.parseInt(rule.substring(5));
                if (person.getAge() > maxAge) return false;
            } 
            else if (rule.startsWith("gender=")) {
                String requiredGender = rule.substring(7).toUpperCase();
                if (!Objects.equals(person.getGender().toString().toUpperCase(), requiredGender)) return false;
            }
        }

        return true;
    }
}
