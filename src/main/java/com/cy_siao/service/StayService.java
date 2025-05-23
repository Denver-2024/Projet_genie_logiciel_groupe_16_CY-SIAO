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
 * The service who manage stay
 * It check if the person is eligible in the room where the bed is at the choose date
 */
public class StayService {
    private EligibilityService eligibilityService;
    private BedDao bedDao;
    private RoomDao roomDao;
    private StayDao stayDao;
    private PersonService personService;

    /**
     * The constructor
     */
    public StayService() {
        this.eligibilityService = new EligibilityService();
        this.personService = new PersonService();
        this.bedDao = new BedDao();
        this.roomDao = new RoomDao();
        this.stayDao = new StayDao();
    }
    /**
     * 
     * @param person personn who need to be assign during arrival - departure
     * @param bed bed selected to be assigned at the person
     * @param arrival start date
     * @param departure end date
     * @return true if Person is correctly assign to the bed
     */
    public boolean assignPersonToBed(Person person, Bed bed, LocalDate arrival, LocalDate departure) {
        
        if (this.isAssignable(person, bed, arrival, departure)){
            Stay stay = new Stay(bed, person, arrival, departure);
            stayDao.create(stay);
            return true;
        }
        return false;
    }

    public List<Stay> getAllStays(){
        return stayDao.findAll();
    }

    /**
     * Check if the person is eligible at the room where the bed is
     * And check if the bed is not assigned during arrival departure
     * And check if the person is not assigned at an other bed during a part of sejour
     * @param person person who needs to be assigned during arrival - departure
     * @param bed bed selected to be assigned at the person
     * @param arrival start date
     * @param departure end date
     * @return true if we can assign the person at the bed during arrival - departure
     */
    public boolean isAssignable(Person person, Bed bed, LocalDate arrival, LocalDate departure){

        Room room;
        room = roomDao.findById(bed.getIdRoom());

        RestrictionRoomDao restrictionRoomDao = new RestrictionRoomDao();
        List<RestrictionRoom> restrictions;
        restrictions = restrictionRoomDao.findByIdRoom(room.getId());

        if (eligibilityService.isPersonAllowedInRoom(person, room, restrictions)){
            if (bed.isAvailable(arrival, departure)){
                List<Stay> allStay = this.getAllStays();
                for (Stay stay : allStay){
                    if (person.getId() == stay.getIdPerson()){
                        if ((stay.getDateArrival().isAfter(arrival) && stay.getDateArrival().isBefore(arrival)) ||
                        (stay.getDateArrival().isAfter(departure) && stay.getDateArrival().isBefore(departure))) {
                        return false;
            }
                    }
                }
                return true;
            };
            return false;
        }

        return false;
    }

    /**
     * 
     * @param person
     * @param bed
     * @return
     */
    public boolean isAssign(Person person, Bed bed){
        List<Stay> stays;
        stays = stayDao.findAll();
        for (Stay stay: stays){
            if (stay.getBed().equals(bed) && stay.getPerson().equals(person)){
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param person
     * @param bed
     * @return
     */
    public boolean unassign(Person person, Bed bed) throws SQLException {
        if (isAssign(person, bed)){
            stayDao.delete(stayDao.findByBedPerson(bed.getId(), person.getId()).getId());
            return true;
        }
        return false;
    }

    /**
     * Frees the bed for all his stay.
     *
     * @param bed Remove all stay link at this bed
     */
    public void freeBed(Bed bed) {
        List<Stay> stays;
        stays = this.getAllStays();
        for(Stay stay: stays){
            if (stay.getBed().equals(bed)){
                stayDao.delete(stay.getId());
            }
        }
    }

    /**
     * 
     * @param person
     */
    public void freePerson(Person person) {
        List<Stay> stays;
        stays = this.getAllStays();
        for(Stay stay: stays){
            if (stay.getPerson().equals(person)){
                stayDao.delete(stay.getId());
            }
        }
    }

    public void connectStayToBed(List<Bed> listBed){
        List<Stay> allStay = stayDao.findAll();
        for (Stay stay: allStay){
            for (Bed bed : listBed){
                if (bed.getId() == stay.getIdBed()){
                    bed.addStay(stay);
                }
            }
        }
    }

    public void updateStay(Stay stay){
        stayDao.update(stay);
    }

    public void deleteStay(Stay stay){
        stayDao.delete(stay.getId());
    }

    public LocalDate findStayRoom(List<Person> persons, int nbDay, Room room){

        LocalDate arrivalDay = LocalDate.now();
        arrivalDay.minusDays(1);
        List<Bed> allBeds = bedDao.findAll();
        int nbBedInRoom = 0;
        for (Bed bed : allBeds){
            if(bed.getIdRoom() == room.getId()){
                nbBedInRoom += 1;
            }
        }
        this.connectStayToBed(allBeds);
        int countNbBedAvaible = 0;
        if(persons.size() <= nbBedInRoom){
            while (countNbBedAvaible < persons.size()){
                countNbBedAvaible = 0;
                arrivalDay = arrivalDay.plusDays(1);
                for (Bed bed : allBeds){
                    if (bed.getIdRoom() == room.getId() && bed.isAvailable(arrivalDay, arrivalDay.plusDays(nbDay))){
                        countNbBedAvaible += 1;
                    }
                }
            }
            return arrivalDay;
        }else{
            return null;
        }
    }

    public Room findStay(List<Person> persons, int nbDay){

        List<Room> allRoom = roomDao.findAll();
        int selectRoom = 0;
        LocalDate nearestDate = findStayRoom(persons, nbDay, allRoom.get(0));
        for (int i = 1; i < allRoom.size(); i++){
            if (nearestDate != null){
                LocalDate compareDate = findStayRoom(persons, nbDay, allRoom.get(i));
                if (compareDate != null){
                    if (compareDate.isBefore(nearestDate)){
                        selectRoom = i;
                        nearestDate = compareDate;
                    }
                }
            }else{
                nearestDate = findStayRoom(persons, nbDay, allRoom.get(i));
            }
        }
        if (findStayRoom(persons, nbDay, allRoom.get(selectRoom)) != null){
            return allRoom.get(selectRoom);
        }else{
            return null;
        }
    }


}
