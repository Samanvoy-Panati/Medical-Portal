package com.se.cloud;

import java.sql.Connection;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class DoctorOptions extends JFrame {
	
	private DoctorOptionsPanel doctorOptionsPanel; 
	
	public DoctorOptions(Connection con, int did){
		doctorOptionsPanel = new DoctorOptionsPanel(con, did, this);
		setupLoginFrame();
	}
	
	public void setupLoginFrame(){
		this.setContentPane(doctorOptionsPanel);
		this.setSize(600,600);
		this.setVisible(true);
	}
}
