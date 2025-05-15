package com.cy_siao.service;

import java.util.List;

import com.cy_siao.dao.BedDao;
import com.cy_siao.dao.StayDao;
import com.cy_siao.dao.RoomDao;
import com.cy_siao.model.Bed;
import com.cy_siao.model.Stay;
import com.cy_siao.model.RestrictionRoom;
import com.cy_siao.model.Room;
import com.cy_siao.model.person.Person;

public class StayService {
    private EligibilityService eligibilityService;
    private BedDao bedDao;
    private RoomDao roomDao;
    private StayDao stayDao;

    public boolean assignPersonToBed(Person person, Bed bed, int arrival, int departure) {
        
        if (this.isAssignable(person, bed, arrival, departure)){
            //stayDao.create(person, bed, arrival, derparture); // refaire stay object
            return true;
        }
        return false;
    }

    public boolean isAssignable(Person person, Bed bed, int arrival, int departure){
        return false;
    }

    // isAssignable
    // assignPersonToBed
    // faire une fonction pour supprimmer le lien entre une personne et un lit si le lien existe
    // fonction pour savoir si une personne est assigné a un lit
    // modifier date arrive
    // modifier date depart
    // sejour finis : renvoie vrai si la date de depart est avant aujourd'hui
    // sejour en cours : renvoie vrai si le sejour est en cours
}
