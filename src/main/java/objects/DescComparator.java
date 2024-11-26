package objects;

import java.util.Comparator;

public class DescComparator implements Comparator<Appointment> {
    @Override
    public int compare(Appointment o1, Appointment o2) {
        int r = o2.getDescription().compareTo(o1.getDescription());
        if(r>0){return -1;}
        else if(r==0){return 0;}
        else {return 1;}
    }
}
