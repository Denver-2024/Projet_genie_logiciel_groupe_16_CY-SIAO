package com.cy_siao.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cy_siao.dao.BedDao;
import com.cy_siao.dao.RestrictionRoomDao;
import com.cy_siao.dao.RestrictionTypeDao;
import com.cy_siao.dao.StayDao;
import com.cy_siao.dao.RoomDao;
import com.cy_siao.model.Bed;
import com.cy_siao.model.Stay;
import com.cy_siao.model.Room;
import com.cy_siao.model.RestrictionRoom;
import com.cy_siao.model.RestrictionType;
import com.cy_siao.model.person.Person;

/**
 * Service de gestion des séjours (Stay). Permet d’assigner des personnes à des lits
 * selon des règles d’éligibilité et les restrictions de chambres.
 * Gère également les désassignations, la recherche de créneaux disponibles, etc.
 */
public class StayService {

    // Services internes et DAO
    private EligibilityService eligibilityService;
    private BedDao bedDao;
    private RoomDao roomDao;
    private StayDao stayDao;
    private PersonService personService;

    /**
     * Constructeur par défaut initialisant les DAO et services nécessaires.
     */
    public StayService() {
        this.eligibilityService = new EligibilityService();
        this.personService = new PersonService();
        this.bedDao = new BedDao();
        this.roomDao = new RoomDao();
        this.stayDao = new StayDao();
    }

    /**
     * Tente d’assigner une personne à un lit sur une période donnée si elle est éligible.
     *
     * @param person    La personne à assigner.
     * @param bed       Le lit concerné.
     * @param arrival   Date d’arrivée souhaitée.
     * @param departure Date de départ souhaitée.
     * @return true si l’assignation a réussi, false sinon.
     */
    public boolean assignPersonToBed(Person person, Bed bed, LocalDate arrival, LocalDate departure) {
        if (this.isAssignable(person, bed, arrival, departure)) {
            Stay stay = new Stay(bed, person, arrival, departure);
            stayDao.create(stay);
            return true;
        }
        return false;
    }

    /**
     * Retourne tous les séjours enregistrés.
     *
     * @return Liste des objets Stay.
     */
    public List<Stay> getAllStays() {
        return stayDao.findAll();
    }

    /**
     * Vérifie si une personne peut être assignée à un lit à une période donnée.
     *
     * @param person    La personne.
     * @param bed       Le lit ciblé.
     * @param arrival   Date de début de séjour.
     * @param departure Date de fin de séjour.
     * @return true si l’assignation est possible.
     */
    public boolean isAssignable(Person person, Bed bed, LocalDate arrival, LocalDate departure) {
        Room room = roomDao.findById(bed.getIdRoom());

        RestrictionRoomDao restrictionRoomDao = new RestrictionRoomDao();
        List<RestrictionRoom> restrictions = restrictionRoomDao.findByIdRoom(room.getId());

        if (eligibilityService.isPersonAllowedInRoom(person, room, restrictions)) {
            if (bed.isAvailable(arrival, departure)) {
                List<Stay> allStay = this.getAllStays();
                for (Stay stay : allStay) {
                    if (person.getId() == stay.getIdPerson()) {
                        // Vérifie si la personne est déjà assignée à un autre lit pendant la même période
                        if ((stay.getDateArrival().isAfter(arrival) && stay.getDateArrival().isBefore(departure)) ||
                                (stay.getDateDeparture().isAfter(arrival) && stay.getDateDeparture().isBefore(departure))) {
                            return false;
                        }
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Vérifie si une personne est actuellement assignée à un lit.
     *
     * @param person La personne.
     * @param bed    Le lit.
     * @return true si la personne est déjà assignée à ce lit.
     */
    public boolean isAssign(Person person, Bed bed) {
        List<Stay> stays = stayDao.findAll();
        for (Stay stay : stays) {
            if (stay.getBed().equals(bed) && stay.getPerson().equals(person)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Supprime une assignation spécifique d’une personne à un lit.
     *
     * @param person La personne.
     * @param bed    Le lit.
     * @return true si la désassignation a réussi.
     */
    public boolean unassign(Person person, Bed bed) throws SQLException {
        if (isAssign(person, bed)) {
            stayDao.delete(stayDao.findByBedPerson(bed.getId(), person.getId()).getId());
            return true;
        }
        return false;
    }

    /**
     * Libère tous les séjours associés à un lit.
     *
     * @param bed Le lit à libérer.
     */
    public void freeBed(Bed bed) {
        List<Stay> stays = this.getAllStays();
        for (Stay stay : stays) {
            if (stay.getBed().equals(bed)) {
                stayDao.delete(stay.getId());
            }
        }
    }

    /**
     * Libère tous les séjours d’une personne.
     *
     * @param person La personne dont il faut libérer les séjours.
     */
    public void freePerson(Person person) {
        List<Stay> stays = this.getAllStays();
        for (Stay stay : stays) {
            if (stay.getPerson().equals(person)) {
                stayDao.delete(stay.getId());
            }
        }
    }

    // Connecte les séjours aux lits pour permettre le calcul de disponibilité.
    public void connectStayToBed(List<Bed> listBed) {
        List<Stay> allStay = stayDao.findAll();
        for (Stay stay : allStay) {
            for (Bed bed : listBed) {
                if (bed.getId() == stay.getIdBed()) {
                    bed.addStay(stay);
                }
            }
        }
    }

    // Met à jour un séjour existant.
    public void updateStay(Stay stay) {
        stayDao.update(stay);
    }

    /**
     * Remove a stay
     * 
     * @param stay to remove
     * @return true if the delete is a success
     */
    public boolean deleteStay(Stay stay) {
        return stayDao.delete(stay.getId());
    }

    /**
     * Cherche une date à laquelle une chambre peut accueillir un groupe de personnes pendant une durée donnée.
     *
     * @param persons Liste des personnes à héberger.
     * @param nbDay   Durée du séjour en jours.
     * @param room    La chambre ciblée.
     * @return Date la plus proche à laquelle la chambre peut accueillir le groupe, ou null si impossible.
     */
    public LocalDate findStayRoom(List<Person> persons, int nbDay, Room room) {
        LocalDate arrivalDay = LocalDate.now().minusDays(1);
        List<Bed> allBeds = bedDao.findAll();

        int nbBedInRoom = 0;
        for (Bed bed : allBeds) {
            if (bed.getIdRoom() == room.getId()) {
                nbBedInRoom += 1;
            }
        }

        this.connectStayToBed(allBeds);

        int countNbBedAvailable = 0;
        if (persons.size() <= nbBedInRoom) {
            while (countNbBedAvailable < persons.size()) {
                countNbBedAvailable = 0;
                arrivalDay = arrivalDay.plusDays(1);
                for (Bed bed : allBeds) {
                    if (bed.getIdRoom() == room.getId() && bed.isAvailable(arrivalDay, arrivalDay.plusDays(nbDay))) {
                        countNbBedAvailable += 1;
                    }
                }
            }
            return arrivalDay;
        } else {
            return null;
        }
    }

    /**
     * Cherche la chambre disponible la plus tôt pour un groupe de personnes sur une durée donnée.
     *
     * @param persons Liste des personnes à héberger.
     * @param nbDay   Durée du séjour en jours.
     * @return La chambre la plus rapidement disponible ou null si aucune chambre ne convient.
     */
    public Room findStay(List<Person> persons, int nbDay) {
        List<Room> allRoom = roomDao.findAll();
        int selectRoom = 0;
        LocalDate nearestDate = findStayRoom(persons, nbDay, allRoom.get(0));

        for (int i = 1; i < allRoom.size(); i++) {
            if (nearestDate != null) {
                LocalDate compareDate = findStayRoom(persons, nbDay, allRoom.get(i));
                if (compareDate != null && compareDate.isBefore(nearestDate)) {
                    selectRoom = i;
                    nearestDate = compareDate;
                }
            } else {
                nearestDate = findStayRoom(persons, nbDay, allRoom.get(i));
            }
        }

        if (findStayRoom(persons, nbDay, allRoom.get(selectRoom)) != null) {
            return allRoom.get(selectRoom);
        } else {
            return null;
        }
    }
}
