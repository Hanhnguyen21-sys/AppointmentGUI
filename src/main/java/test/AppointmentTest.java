package test;

import objects.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
//include tests until milestone 4
public class AppointmentTest {
    private Appointment app;
    private AppointmentManager manager;
    private OnetimeAppointment onetime;
    private DailyAppointment daily, daily1, daily2,daily3,daily4, daily5;
    private MonthlyAppointment monthly;
    private LocalDate test;
    @BeforeEach
    public void setAppointment()
    {
        app = new Appointment("Medical", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 1)) {
            @Override
            public boolean occursOn(LocalDate date) {
                return false;
            }
        };
        manager = new AppointmentManager();
        onetime = new OnetimeAppointment("One Time",LocalDate.of(2024,1,2));
        daily = new DailyAppointment("Daily Appointment",LocalDate.of(2024,1,1),LocalDate.of(2024,2,1));
        monthly = new MonthlyAppointment("Monthly",LocalDate.of(2024,1,1),LocalDate.of(2024,3,1));
        daily1 = new DailyAppointment("Health Insurance", LocalDate.of(2024,10,15),LocalDate.of(2024,12,1));
        daily2 = new DailyAppointment("Hotel Booking", LocalDate.of(2024,5,19),LocalDate.of(2024,7,1));
        daily3 = new DailyAppointment("Class Enrollment", LocalDate.of(2024,10,15),LocalDate.of(2024,11,9));
        daily4 = new DailyAppointment("Restaurant Reservation", LocalDate.of(2024,10,15),LocalDate.of(2024,11,9));
        daily5 = new DailyAppointment("House Leasing", LocalDate.of(2024,10,15),LocalDate.of(2024,11,9));
        test = LocalDate.of(2024,10,15);
    }
    @Test
    public void addOneAppointment()
    {
        manager.add(daily);
        assertEquals(1,manager.getAppoinments().size());
    }
    @Test
    public void addMultipleAppointments()
    {
        manager.add(daily);
        manager.add(daily1);
        manager.add(daily2);
        manager.add(daily3);
        assertEquals(4,manager.getAppoinments().size());
    }
    @Test
    public void deleteAppointments()
    {
        manager.add(daily);
        manager.add(daily1);
        manager.add(daily2);
        manager.add(daily3);
        manager.delete(daily2);
        assertEquals(3,manager.getAppoinments().size());
    }
    @Test
    public void deleteMultipleAppointments()
    {
        manager.add(daily);
        manager.add(daily1);
        manager.add(daily2);
        manager.add(daily3);
        manager.delete(daily2);
        manager.delete(daily);
        assertEquals(2,manager.getAppoinments().size());
    }
    @Test
    public void notAllowDeleteNonExistedAppointments()
    {
        manager.add(daily);
        manager.add(daily1);
        manager.add(daily2);
        manager.add(daily3);
        Exception exception = assertThrows(Exception.class, () -> manager.delete(daily4));
        assertEquals("Appointment does not exist", exception.getMessage());
    }
    @Test
    public void notAllowAddSameAppointment()
    {
        manager.add(daily1);
        Exception exception = assertThrows(Exception.class, () -> manager.add(daily1));
        assertEquals("Appointment already exists", exception.getMessage());
    }
    @Test
    public void updateNoneExistedAppointment()
    {
        manager.add(daily);
        manager.add(daily1);
        manager.add(daily3);
        Exception exception = assertThrows(Exception.class, () -> manager.update(daily5,daily4));
        assertEquals("Current date does not exist", exception.getMessage());
    }
    @Test
    public void getAppointmentsOccurs()
    {
        manager.add(daily);
        manager.add(daily1);
        manager.add(daily2);
        manager.add(daily3);
        manager.add(daily4);
        manager.add(daily5);
        Appointment[] appointments = manager.getAppointmentsOn(test,null);
        //daily1, daily3, daily4, daily 5 occurs on test date
        assertEquals(4,appointments.length);


    }
    @Test
    public void beforeStartShouldBeFalse()
    {
        assertFalse(daily1.inBetween(LocalDate.of(2024,6,12)));
    }
    @Test
    public void atStartShouldBeTrue()
    {
        assertTrue(daily2.inBetween(LocalDate.of(2024,5,19)));
    }
    @Test
    public void inRangeShouldBeTrue()
    {
        assertTrue(daily1.inBetween(LocalDate.of(2024,11,21)));
    }
    @Test
    public void atEndShouldBeTrue()
    {
        assertTrue(daily5.inBetween(LocalDate.of(2024,11,9)));
    }
    @Test
    public void afterEndShouldBeFalse()
    {

        assertFalse(daily5.inBetween(LocalDate.of(2025,1,1)));
    }
    @Test
    //after constructing a onetime appointment
    //object, the end date should be the same as the start date
    public void constructorOnetime()
    {
        assertTrue(onetime.getStartDate()==onetime.getEndDate());
    }
    @Test
    //OnetimeAppointment: returns true if the parameter
    // is the exact same as the start date of appointment
    public void occursOnOneTime()
    {

        assertTrue(onetime.occursOn(LocalDate.of(2024,1,2)));
        assertFalse( onetime.occursOn(LocalDate.of(2024,2,3)));
    }
    @Test
    //returns true if the parameter is “inBetween”
    // the start and end date (inclusive)
    public void occursOnDaily()
    {
        assertTrue( daily.occursOn(LocalDate.of(2024,1,20)));
        assertTrue(daily.occursOn(LocalDate.of(2024,1,1)));
        assertFalse(daily.occursOn(LocalDate.of(2024,4,2)));
    }

    @Test
    //MonthlyAppointment: returns true if the parameter is “inBetween” the
    // start and end date (inclusive) AND the day of month is the same
    // as the startDate’s day of month of the appointment
    public void occursOnMonthly()
    {
        assertTrue(monthly.occursOn(LocalDate.of(2024,2,1)));
        assertTrue(monthly.occursOn(LocalDate.of(2024,3,1)));
        assertFalse(monthly.occursOn(LocalDate.of(2024,3,28)));
        assertFalse(monthly.occursOn(LocalDate.of(2024,1,20)));
    }

    //comparable appointments
    @Test
    // different start date
    // daily1: 10/15/2024 - 12/1/2024
    // daily2: 5/19/2024 - 7/1/2024
    public void compareDifferentStartDate()
    {

        assertTrue(daily1.compareTo(daily2)>0);
    }

    @Test
    // same start date
    // daily1: 10/15/2024 - 12/1/2024
    // daily3: 10/15/2024 - 11/9/2024
    public void compareSameStartDate()
    {
        assertTrue(daily3.compareTo(daily1)<0);
    }

    @Test
    // same start and end dates , but different description
    // daily3: 10/15/2024 - 11/9/2024 , Class Enrollment
    // daily4: 10/15/2024 - 11/9/2024, Restaurant Reservation
    // daily5: 10/15/2024 - 11/9/2024, House Leasing
    public void compareDifferentDescription()
    {
        assertTrue(daily4.compareTo(daily3)>0);
        assertTrue(daily5.compareTo(daily4)<0);
        assertTrue(daily3.compareTo(daily5)<0);
    }







}