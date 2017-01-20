package com.se.cloud;

import java.sql.Connection;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Cancelling extends JFrame {
	private CancellingPanel cancelPanel; 	
	public Cancelling(Connection con, int pid){
		cancelPanel = new CancellingPanel(con, pid, this);
		setupCancellingFrame();
	}
	
	public void setupCancellingFrame(){
		this.setContentPane(cancelPanel);
		this.setSize(600,600);
		this.setVisible(true);
	}
}
