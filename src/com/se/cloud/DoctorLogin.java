package com.se.cloud;

import java.sql.Connection;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class DoctorLogin extends JFrame{

		private DoctorLoginPanel doctorLoginPanel; 
		
		public DoctorLogin(Connection con){
			doctorLoginPanel = new DoctorLoginPanel(con, this);
			setupLoginFrame();
		}
		
		public void setupLoginFrame(){
			this.setContentPane(doctorLoginPanel);
			this.setSize(600,600);
			this.setVisible(true);
		}
}
