package objects;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AppointmentManager {
    // using hashset to make sure all appointments are unique
    private HashSet<Appointment> appointments;
    public AppointmentManager()
    {
        appointments = new HashSet<>();
    }
    public HashSet<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppoinments(HashSet<Appointment> appointments) {
        this.appointments = appointments;
    }

    /**
     * Add appointment to the hashset
     * @param appointment
     */
    public void add(Appointment appointment)
    {
        if(appointments.contains(appointment)){
            throw new IllegalArgumentException("Appointment already exists");
        }
        else {
            appointments.add(appointment);
        }
    }
    public void delete(Appointment appointment)
    {
        if(!appointments.remove(appointment)){
            throw new IllegalArgumentException("Appointment does not exist");
        }
    }

    /**
     * Update appointment by remove current appointment and add the modified one
     * @param current - appointment that users want to remove
     * @param modified - modified appointment and ready to be added
     */
    public void update(Appointment current, Appointment modified)
    {
        if(!(appointments.remove(current)))
        {
            throw new IllegalArgumentException("Current date does not exist");
        }
        else {
            appointments.add(modified);
        }

    }

    /**
     * The function is used to get appointments occurred on given date
     * @param date - a date when appointments occur, if date = null -> return all appointments
     * @param order
     * @return - an array of appointments
     */

    public Appointment[] getAppointmentsOn(LocalDate date, Comparator<Appointment> order)
    {
        /**
         * priority queue is min-heap and return as an array
         */

        PriorityQueue<Appointment> pq =  new PriorityQueue<>();

        if(date==null)
        {
            pq.addAll(appointments);
        }
        else {

            for(Appointment appointment: appointments)
            {
                if(appointment.occursOn(date))
                {
                    pq.add(appointment); //add appointment occurs on that date
                }
            }
        }
        Appointment[] result = pq.toArray(new Appointment[pq.size()]);
        if(order!=null)
        {
            Arrays.sort(result, order);
        }
        else {
            Arrays.sort(result);
        }
        return result;
        //if comparator is null, return by appointment's natural order
//        Comparator<Appointment> localOrder  ;
//        if ( order != null ){
//            localOrder = order;
//        } else {
//            localOrder = Comparator.naturalOrder();
//        }
//        //use priority queue to store the desired appointments
//        PriorityQueue<Appointment> Q = new PriorityQueue<>(localOrder);
//
//        for (Appointment appointment :appoinments) {
//            if ( date == null || appointment.getStartDate().equals(date) )
//                Q.add(appointment);
//        }
//
//        // use toArray to convert it into an Array
//        return Q.toArray(new Appointment[0]);
    }

    /**
     * Save function is used to save the set of appointments to a file
     * @param filename
     */
    public void save(String filename){
        try(FileOutputStream outfile = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(outfile))
        {
            for (Appointment a: appointments)
            {
                out.writeObject(a);
            }
            System.out.println("Successfully saved!");
            //out.close();

        }
        catch (IOException e)
        {
            System.out.println("Can not saved!");
        }

    }
    //load appointments from a file and override or add existing set
    // based on 2nd parameter
    void load(String filename, boolean override) throws FileNotFoundException {
        try(FileInputStream infile = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(infile)) {

            while (true) {
                Appointment app = (Appointment) in.readObject();
                if (app != null) {
                    appointments.add(app);
                    //System.out.println("Appointment Description: " + app.getDescription());
                }
            }
        }
        catch (EOFException e)
        {
            //end of file
        }
        catch (IOException e)
        {
            System.out.println("File not found");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Invalid object type in file");
        }
    }





}
