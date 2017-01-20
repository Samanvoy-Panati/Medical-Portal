package com.se.cloud;

import java.sql.Connection;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Register extends JFrame {
	
	private RegisterPanel registerPanel; 
	
	public Register(Connection con){
		registerPanel = new RegisterPanel(con, this);
		setupRegisterFrame();
	}
	
	public void setupRegisterFrame(){
		this.setContentPane(registerPanel);
		this.setSize(600,600);
		this.setVisible(true);
	}
}
