package com.cy_siao.service;

import com.cy_siao.dao.RestrictionTypeDao;
import com.cy_siao.model.RestrictionType;
import com.cy_siao.model.Room;
import com.cy_siao.model.person.Person;
import com.cy_siao.model.RestrictionRoom;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 */
public class EligibilityService {
    private RestrictionTypeDao restrictionTypeDao;

    /**
     * 
     */
    public EligibilityService() {
        this.restrictionTypeDao = new RestrictionTypeDao();
    }

    /**
     * 
     * @param person
     * @param room
     * @param restrictions
     * @return
     */
    public boolean isPersonAllowedInRoom(Person person, Room room, List<RestrictionRoom> restrictions) {
        if (restrictions == null || restrictions.isEmpty()) {
            return true; // Pas de restrictions = accès autorisé
        }

        boolean finalResult = restrictions.get(0).getLogicOperator().equals("AND");

        for (RestrictionRoom rr : restrictions) {
            try {
                // Charger le RestrictionType depuis la base de données
                RestrictionType restriction = restrictionTypeDao.findById(rr.getIdRestrictionType());
                if (restriction == null) {
                    continue; // Ignorer les restrictions invalides
                }
                
                boolean result = matchesRestriction(person, restriction);

                if (rr.getLogicOperator().equalsIgnoreCase("AND")) {
                    finalResult = finalResult && result;
                } else if (rr.getLogicOperator().equalsIgnoreCase("OR")) {
                    finalResult = finalResult || result;
                }
            } catch (SQLException e) {
                System.err.println("An error occurred when trying to find RestrictionType: " + e.getMessage());
                return false;
            }
        }

        return finalResult;
    }

    /**
     * 
     * @param person
     * @param restriction
     * @return
     */
    private boolean matchesRestriction(Person person, RestrictionType restriction) {
        if (restriction == null) {
            return true; // Pas de restriction = autorisation accordée
        }

        boolean ageOk = true;
        boolean genderOk = true;

        Integer minAge = restriction.getMinAge();
        Integer maxAge = restriction.getMaxAge();
        
        if (minAge != null || maxAge != null) {
            int age = person.getAge();
            if (minAge != null && age < minAge) ageOk = false;
            if (maxAge != null && age > maxAge) ageOk = false;
        }

        if (restriction.getGenderRestriction() != null) {
            genderOk = person.getGender().equals(restriction.getGenderRestriction());
        }

        return ageOk && genderOk;
    }
}