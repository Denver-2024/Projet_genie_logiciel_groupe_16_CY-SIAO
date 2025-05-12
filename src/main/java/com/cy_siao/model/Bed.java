package com.cy_siao.model;

import com.cy_siao.model.person.Person;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class Bed {

    /**
     * Default constructor
     */
    public Bed() {
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private int nbPlacesMax;

    /**
     * 
     */
    private int idRoom;








    /**
     * @param dateArrival 
     * @param dateDeparture 
     * @return
     */
    public boolean isAvailable(LocalDate dateArrival, LocalDate dateDeparture) {
        // TODO implement here
        return false;
    }

    /**
     * @param person 
     * @param dateArrival 
     * @param dateDeparture 
     * @return
     */
    public void assignPerson(Person person, LocalDate dateArrival, LocalDate dateDeparture) {
        // TODO implement here
    }

    /**
     * @return
     */
    public void free() {
        // TODO implement here
    }

}