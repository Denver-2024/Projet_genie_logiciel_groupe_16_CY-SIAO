package com.cy_siao.service;

import com.cy_siao.dao.BedDao;
import com.cy_siao.dao.StayDao;
import com.cy_siao.model.Bed;
import com.cy_siao.model.person.Person;

public class StayService {
    private EligibilityService eligibilityService;
    private StayDao stayDao;
    private BedDao bedDao;

    public boolean assignPersonToBed(Person person, Bed bed, int arrival, int departure) {
    return true;
    }
}
