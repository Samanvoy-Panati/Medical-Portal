package com.se.cloud;

import java.sql.Connection;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class DoctorAddTreatment extends JFrame {

	private DoctorAddTreatmentPanel datPanel; 
	
	public DoctorAddTreatment(Connection con, int did){
		datPanel = new DoctorAddTreatmentPanel(con, did, this);
		setupDATFrame();
	}
	
	public void setupDATFrame(){
		this.setContentPane(datPanel);
		this.setSize(600,600);
		this.setVisible(true);
	}
}
