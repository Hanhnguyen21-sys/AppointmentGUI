package gui;

import com.github.lgooddatepicker.components.DatePicker;
import objects.*;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.HashSet;

public class AddAppointmentDialog extends JDialog {

    private JLabel descLabel;
    private JTextField desText;
    private JLabel startLabel;
    private JLabel endLabel;
    private JLabel typeLabel;
    private JRadioButton oneTimebtn;
    private JRadioButton dailybtn;
    private JRadioButton monthlybtn;
    private JButton okBtn;
    private JButton cancelBtn;
    private JPanel panel;
    private DatePicker startPicker;
    private DatePicker endPicker;
    private Appointment appointment;
    private ButtonGroup group;
    private AppointmentManager manager;
    private Appointment[] appointments;
    public void initComponent(String des)
    {
        desText.setText(des);
    }
    public AddAppointmentDialog()
    {
        manager= new AppointmentManager();
        appointments = manager.getAppointmentsOn(null,null);
        setContentPane(panel);
        setModal(true);
        getRootPane().setDefaultButton(okBtn);
        // disable ok button till everything is filled out
        // ex: okBtn.setEnabled(false);
        group = new ButtonGroup();
        group.add(oneTimebtn);
        group.add(dailybtn);
        group.add(monthlybtn);
        okBtn.addActionListener(e->onOK());
        cancelBtn.addActionListener(e->onCancel());
        // want to cancel when close window
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        //TODO: call onCancel() on Escape
        panel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    public Appointment getAppointment() {
        return appointment;
    }

    private void onCancel() {
        dispose();
    }

    /**
     * Take input from user and store in a new appointment object
     */
    private void onOK() {
        if(desText.getText().isEmpty() || startPicker.getDate()==null || endPicker.getDate() == null)
        {
            JOptionPane.showMessageDialog(this,"Should fill out all information");
            return;
        }
        //TODO: make sure user enter all fields
        if(oneTimebtn.isSelected())
        {
            if(!startPicker.getDate().isEqual(endPicker.getDate()))
            {
                JOptionPane.showMessageDialog(this,"Start date and end date should be the same for one time appointment");
                return;
            }
            else{
                appointment = new OnetimeAppointment(desText.getText(),startPicker.getDate());
            }

        }
        else if(dailybtn.isSelected())
        {
            if(startPicker.getDate().isAfter(endPicker.getDate()))
            {
                JOptionPane.showMessageDialog(this,"Start date should be before end date");
                return;
            }
            else{
                appointment = new DailyAppointment(desText.getText(),startPicker.getDate(),endPicker.getDate());

            }

        }
        else if(monthlybtn.isSelected())
        {
            if(startPicker.getDate().isAfter(endPicker.getDate()))
            {
                JOptionPane.showMessageDialog(this,"Start date should be before end date");
                return;
            }
            else {
                appointment = new MonthlyAppointment(desText.getText(),startPicker.getDate(),endPicker.getDate());
            }
        }
        else{
            JOptionPane.showMessageDialog(this,"Please select appointment type");
            return;
        }
        //TODO: try to catch exception when enter existed appointment
        try{
            System.out.println(appointment);
            manager.add(appointment);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "Appointment could not be added: " + e.getMessage());
            return;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "Appointment could not be added: " + e.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(this,"Appointment successfully added!");
        dispose();
    }
}
