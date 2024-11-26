package gui;

import com.sun.jdi.event.MonitorWaitedEvent;
import objects.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;

public class MainGui {
    private JPanel panel;
    private JButton addBtn;
    private JButton editBtn;
    private JButton deleteBtn;
    private JRadioButton display;
    private JRadioButton onDateDisplay;
    private JLabel sortLabel;
    private JComboBox<String> comboBox;
    private JList<Appointment> displayList;
    private JTextField textField1;
    private AppointmentManager manager;
    private DefaultListModel<Appointment> model;
    private ButtonGroup group;

    //TODO: Validate user's inputs
    public MainGui()
    {
        group = new ButtonGroup();
        group.add(display);
        group.add(onDateDisplay);
        manager = new AppointmentManager();
        model = new DefaultListModel<>();
        displayList.setModel(model);
        display.addActionListener(this::displayAll);
        onDateDisplay.addActionListener(this::displayOnDate);
        addBtn.addActionListener(this::addAppointment);
        editBtn.addActionListener(this::addAppointment);
        deleteBtn.addActionListener(this::deleteAppointment);
        comboBox.addActionListener(this::sortBy);
        displayList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

    }
    private void sortBy(ActionEvent event) {
        //TODO: Modify comparator
        if(event.getSource()==comboBox)
        {
            Appointment[] appointments = new Appointment[manager.getAppointments().size()];
            textField1.setText("");
            if(Objects.equals(comboBox.getSelectedItem(), "By Date"))
            {
                appointments = manager.getAppointmentsOn(null,null);
                //HashSet<Appointment> appointments = manager.getAppoinments();
                
            }
            else if(Objects.equals(comboBox.getSelectedItem(), "By Description"))
            {
                appointments = manager.getAppointmentsOn(null,new DescComparator());
            }
            model.removeAllElements();
            for(Appointment app: appointments)
            {
                model.addElement(app);
            }
        }
    }

    private void displayAll(ActionEvent e) {
        textField1.setText("");
        HashSet<Appointment> appointments = manager.getAppointments();
        model.removeAllElements();
        for(Appointment app: appointments)
        {
            model.addElement(app);
        }
    }
    public void displayOnDate(ActionEvent e)
    {
        if(textField1.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this.getPanel(),"Please Enter a Date You Want to Search");
        }

        else{
            //TODO: figure out why can't use Appointment[] appointments = manager.getAppointmentsOn(date,null);
            model.removeAllElements();
            LocalDate date = LocalDate.parse(textField1.getText());
            HashSet<Appointment> appointments = manager.getAppointments();
            for(Appointment app: appointments)
            {
                if(app.occursOn(date))
                {
                    System.out.println(app);
                    model.addElement(app);
                }
            }
        }
    }

    public JPanel getPanel()
    {
        return panel;
    }
    private void deleteAppointment(ActionEvent e) {
        // get the index of appointment users want to delete
        int index = displayList.getSelectedIndex();
        // remove the appointment out of the array
        Appointment old_appointment=null;
        String selected = displayList.getSelectedValue().toString();
        String[] two_parts = selected.split(":");
        String substr = two_parts[1].substring(1);
        String[] words = substr.split(" ");
        if(two_parts[0].equals("One time appointment")){
            old_appointment = new OnetimeAppointment(words[0],LocalDate.parse(words[1]));
        }
        else if(two_parts[0].equals("Daily appointment"))
        {
            old_appointment  = new DailyAppointment(words[0],LocalDate.parse(words[1]),LocalDate.parse(words[2]));
        }
        else if(two_parts[0].equals("Monthly appointment"))
        {
            old_appointment  = new MonthlyAppointment(words[0],LocalDate.parse(words[1]),LocalDate.parse(words[2]));
        }
        manager.delete(old_appointment);
        model.removeElementAt(index);
    }


    private void addAppointment(ActionEvent e)
    {
        try{
            AddAppointmentDialog dialog = new AddAppointmentDialog();
            dialog.pack();
            dialog.setVisible(true);
            Appointment appointment = dialog.getAppointment();
            if(e.getSource().equals(addBtn))
            {
                //adding the new appointment to hashset in appointment manager
                manager.add(appointment);
                // print the appointment to jList in main window
                model.addElement(appointment);
            }
            else if(e.getSource().equals(editBtn))
            {
                Appointment old_appointment=null;
                int index = displayList.getSelectedIndex();
                String selected = displayList.getSelectedValue().toString();
                String[] two_parts = selected.split(":");
                String substr = two_parts[1].substring(1);
                String[] words = substr.split(" ");
                if(two_parts[0].equals("One time appointment")){
                    old_appointment = new OnetimeAppointment(words[0],LocalDate.parse(words[1]));
                }
                else if(two_parts[0].equals("Daily appointment"))
                {
                    old_appointment  = new DailyAppointment(words[0],LocalDate.parse(words[1]),LocalDate.parse(words[2]));
                }
                else if(two_parts[0].equals("Monthly appointment"))
                {
                    old_appointment  = new MonthlyAppointment(words[0],LocalDate.parse(words[1]),LocalDate.parse(words[2]));
                }
                if(appointment!=null)
                {
                    manager.update(old_appointment,appointment);
                    model.setElementAt(appointment,index);
                }
                else {
                    model.setElementAt(old_appointment,index);
                }
            }
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
    }

}
