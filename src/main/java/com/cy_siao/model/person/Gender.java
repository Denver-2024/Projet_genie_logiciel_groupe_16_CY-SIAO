package com.cy_siao.model.person;

/**
 * Represents a person's gender with display name support.
 * Provides enumeration of possible gender values and conversion from string.
 */
public enum Gender {
    /** Male gender */
    MALE("Male"),
    /** Female gender */
    FEMALE("Female");



    private final String displayName;

    /**
     * Constructs a Gender enum constant with the specified display name.
     *
     * @param displayName the display name for the gender
     */
    Gender(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the gender.
     *
     * @return the display name of the gender
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Converts a string to the corresponding Gender enum value.
     * The comparison is case-insensitive.
     *
     * @param text the string representation of the gender to convert
     * @return the matching Gender enum value
     * @throws IllegalArgumentException if no matching gender is found
     */
    public static Gender fromString(String text) {
        for (Gender gender : Gender.values()) {
            if (gender.displayName.equalsIgnoreCase(text)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("No gender found with name: " + text);
    }

    /**
     * Returns the display name of the gender.
     *
     * @return the display name of the gender
     */
    @Override
    public String toString() {
        return displayName;
    }
}
