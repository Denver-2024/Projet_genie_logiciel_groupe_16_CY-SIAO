package com.cy_siao.model;

import com.cy_siao.model.person.Person;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Room {

    /**
     * Default constructor
     */
    public Room() {
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
    private int nbBedsMax;

    /**
     * 
     */
    private RestrictionType restrictionType;





    /**
     * @param bed 
     * @return
     */
    public void addBed(Bed bed) {
        // TODO implement here
    }

    /**
     * @param bed 
     * @return
     */
    public void removeBed(Bed bed) {
        // TODO implement here
    }

    /**
     * @param r 
     * @return
     */
    public void addRestriction(RestrictionType r) {
        // TODO implement here
    }

    /**
     * @param r 
     * @return
     */
    public void removeRestriction(RestrictionType r) {
        // TODO implement here
    }

    /**
     * @param person 
     * @return
     */
    public boolean isCompatible(Person person) {
        // TODO implement here
        return false;
    }

}