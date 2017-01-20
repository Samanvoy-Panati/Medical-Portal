package com.se.cloud;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class FirstFramePanel extends JPanel {
	
	Connection con = null;
	public FirstFramePanel(Connection con, JFrame parent) {
		setLayout(new MigLayout("", "[30][73.00][26.00][][][][][][][][]", "[30][60.00][][][][][][][][][]"));
		
		JLabel lblIdentifyYourself = new JLabel("IDENTIFY YOURSELF ");
		lblIdentifyYourself.setForeground(Color.BLUE);
		lblIdentifyYourself.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		add(lblIdentifyYourself, "cell 4 5 6 1,alignx center");
		
		JButton btnDoctor = new JButton("I am a Doctor");
		add(btnDoctor, "cell 5 7");
		
		JButton btnPatient = new JButton("I am a Patient");
		add(btnPatient, "cell 8 7");
		
		btnDoctor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new DoctorLogin(con);
			}
		});
		
		btnPatient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new Login(con);
			}
		});
	}
}
