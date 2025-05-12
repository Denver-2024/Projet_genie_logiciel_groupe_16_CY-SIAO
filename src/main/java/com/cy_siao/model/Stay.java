package com.cy_siao.model;

import com.cy_siao.model.Bed;
import com.cy_siao.model.person.Person;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class Stay {

    /**
     * Default constructor
     */
    public Stay() {
    }

    /**
     * 
     */
    private Bed bed;

    /**
     * 
     */
    private Person person;

    /**
     * 
     */
    private Date dateArrival;

    /**
     * 
     */
    private Date dateDeparture;





    /**
     * @param dateArrival 
     * @param dateDeparture 
     * @return
     */
    public boolean modifyDates(LocalDate dateArrival, LocalDate dateDeparture) {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public boolean isActiveToday() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public boolean isEditable() {
        // TODO implement here
        return false;
    }

}