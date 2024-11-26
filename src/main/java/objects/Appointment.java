package objects;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public abstract class Appointment implements Comparable<Appointment>, Comparator<Appointment>
{
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String representation;
    public Appointment(String description, LocalDate startDate, LocalDate endDate) {
        this.description=description;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    public String getDescription(){
        return description;
    }
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    /**
     * inBetween: check whether a given date is in between start and end dates
     * @param date - data to be checked
     * @return - true if start <= date <=end ; otherwise return false
     */
    public boolean inBetween(LocalDate date)
    {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
    public abstract boolean occursOn(LocalDate date);
    @Override
    public String toString()
    {
        return String.format("%s %s %s",description,startDate, endDate);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Appointment that = (Appointment) object; // make sure object is an instance of Appointment class
        return toString().equals(that.toString());
    }
    @Override
    //hashcode representing the object
    public int hashCode() {
        return Objects.hash(toString());
    }



    /**
     * compareTo() will compare 2 dates in order of: start date, end date, the description
     * @param app the object to be compared.
     * @return - 1 if this.app > app , 0 if they are equal and -1 if this.app< app
     */
    @Override
    public int compareTo(Appointment app)
    {
        int r;
        // if start date is the same -> moving to endDate
        if (this.getStartDate().compareTo(app.getStartDate())!=0)
        {
            r = this.getStartDate().compareTo(app.getStartDate());
        }
        else if(this.getEndDate().compareTo(app.getEndDate())!=0)
        {
            r =  this.getEndDate().compareTo(app.getEndDate());
        }
        else{
            r= this.getDescription().compareTo(app.getDescription());
        }
        if (r>0) {return 1;}
        else if (r==0) { return 0;}
        else {return -1;}
    }

    @Override
    public int compare(Appointment o1, Appointment o2) {
        int r = o1.getDescription().compareTo(o2.getDescription());
        if(r>0){return -1;}
        else if(r==0){return 0;}
        else {return 1;}
    }


}