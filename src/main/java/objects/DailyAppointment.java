package objects;

import java.time.LocalDate;

/**
 * Daily appointment happens every day in a period of time
 * Ex: July 10, 2024 - Sep 2, 2024
 * All appointments occur all days within that period of time
 * -> occursOn(Aug 2, 2024) returns true
 */
public class DailyAppointment extends Appointment{
    public DailyAppointment(String description, LocalDate startDate, LocalDate endDate) {
        super(description, startDate, endDate);
    }
    @Override
    public boolean occursOn(LocalDate date) {
        return super.inBetween(date);
    }
    @Override
    public String toString()
    {
        return "Daily appointment: " + super.toString();
    }
}

