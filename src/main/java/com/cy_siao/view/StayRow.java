package com.cy_siao.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cy_siao.model.Stay;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a row in the planning view table for a stay.
 * Contains properties for the bed and the days of the stay.
 */
public class StayRow {
    private final Stay stay; // The stay model object
    private final StringProperty bed; // Property for the bed label
    private final List<StringProperty> days; // Properties for each day in the stay range
    private final LocalDate startDate = LocalDate.now(); // Start date for the planning view

    /**
     * Constructs a StayRow for the given stay and number of days.
     *
     * @param stay The stay model
     * @param numDays The number of days to display
     */
    public StayRow(Stay stay, int numDays) {
        this.stay = stay;
        this.bed = new SimpleStringProperty("Bed " + stay.getIdBed());
        this.days = new ArrayList<>();

        for (int i = 0; i < numDays; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            if (!currentDate.isBefore(stay.getDateArrival()) && !currentDate.isAfter(stay.getDateDeparture())) {
                String value = stay.getPerson().getFirstName() + " " + stay.getPerson().getLastName() + " (ID " + stay.getPerson().getId() + ")";
                days.add(new SimpleStringProperty(value));
            } else {
                days.add(new SimpleStringProperty(""));
            }
        }
    }

    /**
     * Gets the bed property.
     *
     * @return The bed StringProperty
     */
    public StringProperty bedProperty() {
        return bed;
    }

    /**
     * Gets the day property at the specified index.
     *
     * @param index The day index
     * @return The StringProperty for the day
     */
    public StringProperty getDayProperty(int index) {
        return days.get(index);
    }

    /**
     * Checks if the date at the specified index is within the stay range.
     *
     * @param index The day index
     * @return true if the date is in the stay range, false otherwise
     */
    public boolean isDateInRange(int index) {
        LocalDate date = startDate.plusDays(index);
        return !date.isBefore(stay.getDateArrival()) && !date.isAfter(stay.getDateDeparture());
    }

    /**
     * Checks if the stay has inconsistencies.
     *
     * @return true if inconsistent, false otherwise
     */
    public boolean isInconsistent() {
        return stay.isInconsistent();
    }
} 
