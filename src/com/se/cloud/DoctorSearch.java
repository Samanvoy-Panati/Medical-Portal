package com.se.cloud;

import java.sql.Connection;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class DoctorSearch extends JFrame{

	private DoctorSearchPanel dsPanel; 
	
	public DoctorSearch(Connection con, int did){
		dsPanel = new DoctorSearchPanel(con, did, this);
		setupDSFrame();
	}
	
	public void setupDSFrame(){
		this.setContentPane(dsPanel);
		this.setSize(600,600);
		this.setVisible(true);
	}
}
