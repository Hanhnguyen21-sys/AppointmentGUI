package objects;

import java.io.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AppointmentManager {
    // using hashset to make sure all appointments are unique
    private HashSet<Appointment> appoinments;
    public AppointmentManager()
    {
        appoinments = new HashSet<>();
    }
    public HashSet<Appointment> getAppoinments() {
        return appoinments;
    }

    public void setAppoinments(HashSet<Appointment> appoinments) {
        this.appoinments = appoinments;
    }

    /**
     * Add appointment to the hashset
     * @param appointment
     */
    public void add(Appointment appointment)
    {
        if(!appoinments.add(appointment)){
            throw new IllegalArgumentException("Appointment already exists");
        }
    }
    public void delete(Appointment appointment)
    {
        if(!appoinments.remove(appointment)){
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
        if(!(appoinments.remove(current)))
        {
            throw new IllegalArgumentException("Current date does not exist");
        }
        else {
            appoinments.add(modified);
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
        PriorityQueue<Appointment> pq = new PriorityQueue<>();
        if(date==null)
        {
            pq.addAll(appoinments);
        }
        else {

            for(Appointment appointment: appoinments)
            {
                if(appointment.occursOn(date))
                {
                    pq.add(appointment); //add appointment occurs on that date
                }
            }
        }
        return pq.toArray(new Appointment[0]);
    }

    /**
     * Save function is used to save the set of appointments to a file
     * @param filename
     */
    public void save(String filename){
        try(FileOutputStream outfile = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(outfile))
        {
            for (Appointment a: appoinments)
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
                    appoinments.add(app);
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
