package com.cy_siao.model;

import com.cy_siao.model.person.Person;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class RestrictionType {

    /**
     * Default constructor
     */
    public RestrictionType() {
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private String restriction;

    /**
     * 
     */
    private int idSalle;




    /**
     * @param person 
     * @return
     */
    public boolean isRespectedBy(Person person) {
        // TODO implement here
        return false;
    }

}