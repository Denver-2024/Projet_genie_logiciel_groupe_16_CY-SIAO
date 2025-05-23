package com.cy_siao.service;

import com.cy_siao.dao.RestrictionTypeDao;
import com.cy_siao.model.RestrictionType;
import com.cy_siao.model.Room;
import com.cy_siao.model.person.Person;
import com.cy_siao.model.RestrictionRoom;

import java.sql.SQLException;
import java.util.List;

/**
 * Service de gestion des règles d'éligibilité pour une personne dans une chambre.
 * Vérifie si une personne respecte les restrictions d'une chambre avant de pouvoir y être assignée.
 */
public class EligibilityService {
    // DAO pour accéder aux types de restrictions depuis la base de données
    private RestrictionTypeDao restrictionTypeDao;

    /**
     * Constructeur par défaut initialisant le DAO de RestrictionType.
     */
    public EligibilityService() {
        this.restrictionTypeDao = new RestrictionTypeDao();
    }

    /**
     * Vérifie si une personne est autorisée à accéder à une chambre, en fonction de ses restrictions.
     *
     * @param person       La personne à vérifier.
     * @param room         La chambre concernée.
     * @param restrictions La liste des restrictions appliquées à la chambre.
     * @return true si la personne respecte les restrictions, false sinon.
     */
    public boolean isPersonAllowedInRoom(Person person, Room room, List<RestrictionRoom> restrictions) {
        if (restrictions == null || restrictions.isEmpty()) {
            return true; // Pas de restrictions = accès autorisé
        }

        // Si la première restriction utilise "AND", on initialise à true, sinon false
        boolean finalResult = restrictions.get(0).getLogicOperator().equals("AND");

        for (RestrictionRoom rr : restrictions) {
            try {
                // Charger le RestrictionType depuis la base de données
                RestrictionType restriction = restrictionTypeDao.findById(rr.getIdRestrictionType());
                if (restriction == null) {
                    continue; // Ignorer les restrictions invalides
                }

                // Vérifie si la personne respecte la restriction
                boolean result = matchesRestriction(person, restriction);

                // Combine le résultat selon l'opérateur logique
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
     * Vérifie si une personne correspond aux critères définis par une restriction.
     * (Âge minimum/maximum et genre)
     *
     * @param person      La personne à évaluer.
     * @param restriction La restriction à appliquer.
     * @return true si la personne satisfait la restriction, false sinon.
     */
    private boolean matchesRestriction(Person person, RestrictionType restriction) {
        if (restriction == null) {
            return true; // Pas de restriction = autorisation accordée
        }

        boolean ageOk = true;
        boolean genderOk = true;

        // Vérifie l'âge de la personne
        Integer minAge = restriction.getMinAge();
        Integer maxAge = restriction.getMaxAge();

        if (minAge != null || maxAge != null) {
            int age = person.getAge();
            if (minAge != null && age < minAge) ageOk = false;
            if (maxAge != null && age > maxAge) ageOk = false;
        }

        // Vérifie le genre de la personne
        if (restriction.getGenderRestriction() != null) {
            genderOk = person.getGender().equals(restriction.getGenderRestriction());
        }

        return ageOk && genderOk;
    }
}
