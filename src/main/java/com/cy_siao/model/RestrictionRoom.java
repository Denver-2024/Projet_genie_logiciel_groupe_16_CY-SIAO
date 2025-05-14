package com.cy_siao.model;


public class RestrictionRoom {
    private int roomId;
    private int restrictionTypeId;
    private String logicOperator; // "AND" ou "OR"

    private RestrictionType restrictionType; // relation
    private Room room; // relation

    public RestrictionRoom(int roomId, int restrictionTypeId, String logicOperator) {
        this.roomId = roomId;
        this.restrictionTypeId = restrictionTypeId;
        this.logicOperator = logicOperator.toUpperCase();
    }

    // Getters & Setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRestrictionTypeId() {
        return restrictionTypeId;
    }

    public void setRestrictionTypeId(int restrictionTypeId) {
        this.restrictionTypeId = restrictionTypeId;
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
                "roomId=" + roomId +
                ", restrictionTypeId=" + restrictionTypeId +
                ", logicOperator='" + logicOperator + '\'' +
                '}';
    }
}

