package com.se.cloud;

import java.sql.Connection;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ViewApp extends JFrame {
	private ViewAppPanel viewAppPanel; 	
	
	public ViewApp(Connection con, int pid){
		viewAppPanel = new ViewAppPanel(con, pid, this);
		setupViewAppFrame();
	}
	
	public void setupViewAppFrame(){
		this.setContentPane(viewAppPanel);
		this.setSize(600,600);
		this.setVisible(true);
	}
}
