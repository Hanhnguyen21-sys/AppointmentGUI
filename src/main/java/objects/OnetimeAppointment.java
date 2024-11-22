package objects;

import java.time.LocalDate;

public class OnetimeAppointment extends Appointment {

    public OnetimeAppointment(String description, LocalDate startDate) {
        super(description, startDate, startDate);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return this.getStartDate().equals(date);
    }

    @Override
    public String toString()
    {
        return "One time appointment: " + super.toString();
    }
}
