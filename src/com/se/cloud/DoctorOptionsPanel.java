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
public class DoctorOptionsPanel extends JPanel {
	
	Connection con = null;
	
	private JButton btnSearch;
	private JButton btnAdd;

	public DoctorOptionsPanel(Connection con, int did, JFrame parent) {
		setLayout(new MigLayout("", "[][][52.00][37.00][][][][][][][][][][][][]", "[][47.00][39.00][][][][][][]"));
		
		JLabel lblHeading = new JLabel("SELECT AN OPTION ");
		lblHeading.setForeground(Color.BLUE);
		lblHeading.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		add(lblHeading, "cell 4 3 8 1,alignx center");
		
		btnSearch = new JButton("Search a Patient");
		add(btnSearch, "cell 4 5 4 1");
		
		btnAdd = new JButton("Add a Treatment");
		add(btnAdd, "cell 10 5 2 1");
		
		this.con = con;
		actions(did, parent);
	}
	public void actions(int did, JFrame parent){
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new DoctorSearch(con, did);
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new DoctorAddTreatment(con, did);
			}
		});
	}
}
