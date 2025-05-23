package com.cy_siao.service;

import com.cy_siao.dao.RestrictionRoomDao;
import com.cy_siao.dao.RestrictionTypeDao;
import com.cy_siao.model.RestrictionType;
import com.cy_siao.model.Room;
import com.cy_siao.model.person.Person;
import com.cy_siao.model.RestrictionRoom;

import java.sql.SQLException;
import java.util.List;

/**
 * Service handling eligibility checks for room assignments based on restrictions
 */
public class EligibilityService {
    //Data access object for restriction types
    private RestrictionTypeDao restrictionTypeDao;
    //Data access object for room restrictions
    private RestrictionRoomDao restrictionRoomDao;

    /**
     * Constructs a new EligibilityService with required DAOs
     */
    public EligibilityService() {
        this.restrictionTypeDao = new RestrictionTypeDao();
        this.restrictionRoomDao = new RestrictionRoomDao();
    }

    /**
     * Check if the person is eligible to be assigned to a specific room based on restrictions
     *
     * @param person Person to check eligibility for
     * @param room   Room to check restrictions against
     * @return true if the person meets all room restrictions, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean isPersonAllowedInRoom(Person person, Room room) throws SQLException {
        List<RestrictionRoom> restrictions = restrictionRoomDao.findByRoomId(room.getId());
        if (restrictions == null || restrictions.isEmpty()) {
            return true; // No restrictions means access is allowed
        }

        boolean finalResult = restrictions.get(0).getLogicOperator().equals("AND");

        for (RestrictionRoom rr : restrictions) {
            try {
                // Load RestrictionType from database
                RestrictionType restriction = restrictionTypeDao.findById(rr.getIdRestrictionType());
                if (restriction == null) {
                    continue; // Skip invalid restrictions
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
     * Evaluates if a person matches the given restriction criteria
     *
     * @param person      Person to evaluate
     * @param restriction Restriction criteria to check against
     * @return true if person matches all restriction criteria, false otherwise
     */
    private boolean matchesRestriction(Person person, RestrictionType restriction) {
        if (restriction == null) {
            return true; // No restriction means access is allowed
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