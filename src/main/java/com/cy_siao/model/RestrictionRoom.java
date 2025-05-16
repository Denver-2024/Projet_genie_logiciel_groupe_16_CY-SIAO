package com.cy_siao.model;


public class RestrictionRoom {
    private int idRoom;
    private int idRestrictionType;
    private String logicOperator; // "AND" ou "OR"

    private RestrictionType restrictionType; // relation
    private Room room; // relation

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

