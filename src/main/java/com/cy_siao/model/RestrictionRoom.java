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

    // Getters & Setters
    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public int getIdRestrictionType() {
        return idRestrictionType;
    }

    public void setIdRestrictionType(int idRestrictionType) {
        this.idRestrictionType = idRestrictionType;
    }

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

    public RestrictionType getRestrictionType() {
        return restrictionType;
    }

    public void setRestrictionType(RestrictionType restrictionType) {
        this.restrictionType = restrictionType;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "RestrictionRoom{" +
                "roomId=" + idRoom +
                ", restrictionTypeId=" + idRestrictionType +
                ", logicOperator='" + logicOperator + '\'' +
                '}';
    }
}