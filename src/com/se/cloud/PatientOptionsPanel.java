package com.se.cloud;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import java.awt.Color;

@SuppressWarnings("serial")
public class PatientOptionsPanel extends JPanel{
	
	public PatientOptionsPanel(Connection con, int pid, JFrame parent) {
		setLayout(new MigLayout("", "[43.00][96.00][134.00][23.00][][][-58.00]", "[][59.00][][40.00][][][]"));
		
		JLabel lblSelectAnOption = new JLabel("SELECT AN OPTION ");
		lblSelectAnOption.setForeground(Color.BLUE);
		lblSelectAnOption.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		add(lblSelectAnOption, "cell 1 3 3 1,alignx center");
		
		JButton btnPersonalDetails = new JButton("Personal Details");
		add(btnPersonalDetails, "cell 1 5,grow");
		
		JButton btnTreatmentHistory = new JButton("Treatment History");
		add(btnTreatmentHistory, "cell 2 5,growx");
		
		JButton btnSearch = new JButton("Search a Doctor");
		add(btnSearch, "cell 3 5,growx");
		
		JButton btnViewMyAppoinments = new JButton("View my Appoinments");
		add(btnViewMyAppoinments, "cell 1 6");
		
		JButton btnBookAnAppointment = new JButton("Book an Appointment");
		add(btnBookAnAppointment, "cell 2 6");
		
		JButton btnCancelTheAppointment = new JButton("Cancel the appointment");
		add(btnCancelTheAppointment, "cell 3 6");
	
		//Personal Details button functionality
		btnPersonalDetails.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new PatientDetails(con, pid);
			}
		});
	
		
		btnTreatmentHistory.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new Treatment(con, pid);
			}
		});
		
		//Booking button functionality
		btnBookAnAppointment.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new Booking(con, pid);
				//objBook.setupBookingFrame();
			}
		});
		
		btnCancelTheAppointment.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new Cancelling(con, pid);
			}
		});
		
		btnViewMyAppoinments.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new ViewApp(con, pid);
			}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new PatientSearch(con, pid);
			}
		});
		
	}		
}
