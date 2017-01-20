package com.se.cloud;

import java.sql.Connection;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Treatment extends JFrame {
private TreatmentPanel treatmentPanel; 
	
	public Treatment(Connection con, int pid){
		treatmentPanel = new TreatmentPanel(con, pid, this);
		setupTreatmentFrame();
	}
	
	public void setupTreatmentFrame(){
		this.setContentPane(treatmentPanel);
		this.setSize(600,600);
		this.setVisible(true);
	}
}
