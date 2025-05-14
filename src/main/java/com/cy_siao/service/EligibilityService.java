package com.cy_siao.service;

import com.cy_siao.model.RestrictionType;
import com.cy_siao.model.Room;
import com.cy_siao.model.person.Person;
import com.cy_siao.model.RestrictionRoom;

import java.util.List;

public class EligibilityService {

    public boolean isPersonAllowedInRoom(Person person, Room room, List<RestrictionRoom> restrictions) {
        boolean finalResult = (restrictions.get(0).getLogicOperator().equals("AND")) ? true : false;

        for (RestrictionRoom rr : restrictions) {
            RestrictionType restriction = rr.getRestrictionType();
            boolean result = matchesRestriction(person, restriction);

            if (rr.getLogicOperator().equalsIgnoreCase("AND")) {
                finalResult = finalResult && result;
            } else if (rr.getLogicOperator().equalsIgnoreCase("OR")) {
                finalResult = finalResult || result;
            }
        }

        return finalResult;
    }

    private boolean matchesRestriction(Person person, RestrictionType restriction) {
        boolean ageOk = true;
        boolean genderOk = true;

        if (restriction.getMinAge() != null || restriction.getMaxAge() != null) {
            int age = person.getAge();
            if (restriction.getMinAge() != null && age < restriction.getMinAge()) ageOk = false;
            if (restriction.getMaxAge() != null && age > restriction.getMaxAge()) ageOk = false;
        }

        if (restriction.getGenderRestriction() != null) {
            genderOk = person.getGender().equals(restriction.getGenderRestriction());
        }

        return ageOk && genderOk;
    }
}

