package com.cy_siao.model;

/**
 * Represents an association between a Room and a RestrictionType with a logical operator.
 * Links restrictions to rooms and specifies how multiple restrictions should be combined.
 */
public class RestrictionRoom {
    private int idRoom;                        // ID of the associated room
    private int idRestrictionType;             // ID of the associated restriction type  
    private String logicOperator;              // "AND" or "OR" operator to combine restrictions

    private RestrictionType restrictionType;   // Relation to RestrictionType object
    private Room room;                         // Relation to Room object

    /**
     * Creates a new restriction-room association.
     *
     * @param idRoom            ID of the room
     * @param idRestrictionType ID of the restriction type
     * @param logicOperator     Logic operator ("AND"/"OR") to combine with other restrictions
     */
    public RestrictionRoom(int idRoom, int idRestrictionType, String logicOperator) {
        this.idRoom = idRoom;
        this.idRestrictionType = idRestrictionType;
        this.logicOperator = logicOperator.toUpperCase();
    }

    /**
     * getter of idRoom
     * @return the id of the Room
     */
    public int getIdRoom() {
        return idRoom;
    }

    /**
     * Setter of id room
     * @param idRoom new value of idRoom
     */
    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    /**
     * getter of idRestrictionType
     * @return the id of the retsriction type
     */
    public int getIdRestrictionType() {
        return idRestrictionType;
    }

    /**
     * setter of the restriction type
     * @param idRestrictionType new value of id of the restriction type
     */
    public void setIdRestrictionType(int idRestrictionType) {
        this.idRestrictionType = idRestrictionType;
    }

    /**
     * getter of logical operator
     * @return can be AND or OR
     */
    public String getLogicOperator() {
        return logicOperator;
    }

    /**
     * Sets the logic operator, validating that it is either "AND" or "OR"
     *
     * @param logicOperator The logic operator to set
     * @throws IllegalArgumentException if operator is not "AND" or "OR"
     */
    public void setLogicOperator(String logicOperator) {
        if (!logicOperator.equalsIgnoreCase("AND") && !logicOperator.equalsIgnoreCase("OR")) {
            throw new IllegalArgumentException("LogicOperator must be 'AND' or 'OR'");
        }
        this.logicOperator = logicOperator.toUpperCase();
    }

    /**
     * getter of restriction type
     * @return restriction type
     */
    public RestrictionType getRestrictionType() {
        return restrictionType;
    }

    /**
     * setter of restriction type
     * @param restrictionType the new restriction type
     */
    public void setRestrictionType(RestrictionType restrictionType) {
        this.restrictionType = restrictionType;
    }

    /**
     * getter of the room which is affected by the restriction
     * @return the room of the restriction
     */
    public Room getRoom() {
        return room;
    }

    /**
     * setter of the room
     * @param room set the room which is affected by the restriction
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Override of the to string to print restrition
     * 
     * @return print restriction with details
     */
    @Override
    public String toString() {
        return "RestrictionRoom{" +
                "roomId=" + idRoom +
                ", restrictionTypeId=" + idRestrictionType +
                ", logicOperator='" + logicOperator + '\'' +
                '}';
    }
}