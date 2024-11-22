package objects;
import java.time.LocalDate;

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
