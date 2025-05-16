package com.cy_siao.service;

import java.time.LocalDate;
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

public class StayService {
    private EligibilityService eligibilityService;
    private BedDao bedDao;
    private RoomDao roomDao;
    private StayDao stayDao;

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

    /**
     * Check if the person is eligible at the room where the bed is
     * And check if the bed is not assigned during arrival departure
     * And check if the person is not assigned at an other bed during a part of sejour
     * @param person
     * @param bed
     * @param arrival
     * @param departure
     * @return true if we can assign the person at the bed during arrival - departure
     */
    public boolean isAssignable(Person person, Bed bed, LocalDate arrival, LocalDate departure){

        Room room;
        room = roomDao.findById(bed.getIdRoom());

        RestrictionRoomDao restrictionRoomDao = new RestrictionRoomDao();
        List<RestrictionRoom> restrictions;
        restrictions = restrictionRoomDao.findAll();

        if (eligibilityService.isPersonAllowedInRoom(person, room, restrictions)){
            List<Stay> stays;
            stays = stayDao.findAll(); // remove person and bed when is corriged
            for (Stay stay: stays){
                // bed already occuped
                if (!(stay.getBed().isAvailable(arrival, departure)) && stay.getBed() == bed){
                    return false;
                }
                // person already assign at a bed during a part of selected period
                if (stay.getPerson() == person &&
                    ((stay.getDateArrival().isAfter(arrival) && stay.getDateArrival().isBefore(departure)) ||
                    (stay.getDateDeparture().isAfter(arrival) && stay.getDateDeparture().isBefore(departure)))){
                    return false;
                }
            }
            return true;
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
            stays = stayDao.findAll(); // remove person and bed when is corriged
            for (Stay stay: stays){
                if (stay.getBed() == bed && stay.getPerson() == person){
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
    public boolean unassign(Person person, Bed bed){
        if (isAssign(person, bed)){
            stayDao.delete(person.getId());
            return true;
        }
        return false;
    }

    /**
     * Frees the bed for a given person by removing their stays.
     *
     * @param person Person to remove from the bed
     */
    public void free(Bed bed, Person person) {
        bed.getStays().removeIf(stay -> stay.getPerson().equals(person));
    }


}
