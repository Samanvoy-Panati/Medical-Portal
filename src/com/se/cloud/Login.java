package com.se.cloud;

import java.sql.Connection;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Login extends JFrame {

	private LoginPanel loginPanel; 
	
	public Login(Connection con){
		loginPanel = new LoginPanel(con, this);
		setupLoginFrame();
	}
	
	public void setupLoginFrame(){
		this.setContentPane(loginPanel);
		this.setSize(600,600);
		this.setVisible(true);
	}
}
