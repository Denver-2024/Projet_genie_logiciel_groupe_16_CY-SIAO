package com.cy_siao.model.person;

import com.cy_siao.model.Bed;

import java.util.*;

/**
 * 
 */
public class Relationship {

    /**
     * Default constructor
     */
    public Relationship() {
    }

    /**
     * 
     */
    private Person person1;

    /**
     * 
     */
    private Person person2;

    /**
     * 
     */
    private String relationType;




    /**
     * @param person 
     * @return
     */
    public void addPerson(Person person) {
        // TODO implement here
    }

    /**
     * @param gender 
     * @param age 
     * @param dates 
     * @return
     */
    public List<Bed> searchAvailableBed(String gender, int age) {
        // TODO implement here
        return null;
    }

    /**
     * @param person 
     * @param dates 
     * @param sameRoom 
     * @return
     */
    public boolean assignPerson(List<Person> person,  boolean sameRoom) {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public void displayOccupation() {
        // TODO implement here
    }

}