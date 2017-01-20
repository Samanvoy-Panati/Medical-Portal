package com.se.cloud;
import java.sql.Connection;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class PatientSearch extends JFrame {

	private PatientSearchPanel psPanel; 
	
	public PatientSearch(Connection con, int pid){
		psPanel = new PatientSearchPanel(con, pid, this);
		setupPOFrame();
	}
	
	public void setupPOFrame(){
		this.setContentPane(psPanel);
		this.setSize(600,600);
		this.setVisible(true);
	}
}
