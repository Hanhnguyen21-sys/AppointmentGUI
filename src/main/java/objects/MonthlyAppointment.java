package objects;
import java.time.LocalDate;

/**
 * Monthly appointments occur on the start date every month
 * Ex: July 10, 2024 - Dec 21, 2024
 * All appointments will occur on every 10 of each month from July-Dec
 */
public class MonthlyAppointment extends Appointment{
    public MonthlyAppointment(String description, LocalDate startDate, LocalDate endDate) {
        super(description, startDate,endDate);
    }
    @Override
    public boolean occursOn(LocalDate date) {
        return (super.inBetween(date) && (date.getDayOfMonth()==this.getStartDate().getDayOfMonth()));
    }
    @Override
    public String toString()
    {
        return "Monthly appointment: " + super.toString();
    }


}
