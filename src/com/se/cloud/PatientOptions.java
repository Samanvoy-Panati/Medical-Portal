package com.se.cloud;

import java.sql.Connection;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class PatientOptions extends JFrame{
private PatientOptionsPanel poPanel; 
	
	public PatientOptions(Connection con, int pid){
		poPanel = new PatientOptionsPanel(con, pid, this);
		setupPOFrame();
	}
	
	public void setupPOFrame(){
		this.setContentPane(poPanel);
		this.setSize(600,600);
		this.setVisible(true);
	}
}

