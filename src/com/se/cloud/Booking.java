package com.se.cloud;

import java.sql.Connection;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Booking extends JFrame {
	
	private BookingPanel bookingPanel; 
	
	public Booking(Connection con, int pid){
		bookingPanel = new BookingPanel(con, pid, this);
		setupBookingFrame();
	}
	
	public void setupBookingFrame(){
		this.setContentPane(bookingPanel);
		this.setSize(600,600);
		this.setVisible(true);
	}
}
