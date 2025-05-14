package com.cy_siao.model.person;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Gender fromString(String text) {
        for (Gender gender : Gender.values()) {
            if (gender.displayName.equalsIgnoreCase(text)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("No gender found with name: " + text);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
