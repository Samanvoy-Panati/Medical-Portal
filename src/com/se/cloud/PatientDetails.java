package com.se.cloud;

import java.sql.Connection;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class PatientDetails extends JFrame {
	
	private PatientDetailsPanel pdPanel; 
	
	public PatientDetails(Connection con, int id){
		pdPanel = new PatientDetailsPanel(con, id, this);
		setupPDFrame();
	}
	
	public void setupPDFrame(){
		this.setContentPane(pdPanel);
		this.setSize(600,600);
		this.setVisible(true);
	}
}
