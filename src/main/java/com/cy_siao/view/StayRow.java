package com.cy_siao.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cy_siao.model.Stay;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StayRow {
    private final Stay stay;
    private final StringProperty bed;
    private final List<StringProperty> days;
    private final LocalDate startDate = LocalDate.now();

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

    public StringProperty bedProperty() {
        return bed;
    }

    public StringProperty getDayProperty(int index) {
        return days.get(index);
    }

    public boolean isDateInRange(int index) {
        LocalDate date = startDate.plusDays(index);
        return !date.isBefore(stay.getDateArrival()) && !date.isAfter(stay.getDateDeparture());
    }

    public boolean isInconsistent() {
        return stay.isInconsistent();
    }
} 
