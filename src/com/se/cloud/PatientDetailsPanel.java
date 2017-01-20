package com.se.cloud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("serial")
public class PatientDetailsPanel extends JPanel {
	private JTextField tfId;
	private JTextField tfFirstName;
	private JTextField tfLastName;
	private JTextField tfSex;
	private JTextField tfEmail;
	private JTextField tfPhone;
	private JButton btnBack;
	private JLabel lblHeading;
	
	public PatientDetailsPanel(Connection con, int pid, JFrame parent) {
		setLayout(new MigLayout("", "[][24.00][53.00][46.00][60.00][9.00][121.00][][46.00][39.00][][47.00][]", "[][38.00][][][25.00][25.00][25.00][25.00][25.00][25.00][][]"));
		
		btnBack = new JButton("Back");
		add(btnBack, "cell 12 0");
		
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new PatientOptions(con, pid);
			}
		});
		
		lblHeading = new JLabel("MY PROFILE ");
		lblHeading.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		lblHeading.setForeground(Color.BLUE);
		add(lblHeading, "cell 3 2 6 1,alignx center");
		
		JLabel lblUserId = new JLabel("User ID");
		add(lblUserId, "cell 4 4,alignx center");
		
		tfId = new JTextField();
		tfId.setEditable(false);
		add(tfId, "cell 6 4,growx");
		tfId.setColumns(10);
		
		JLabel lblFirstName = new JLabel("FirstName");
		add(lblFirstName, "cell 4 5,alignx center");
		
		tfFirstName = new JTextField();
		tfFirstName.setEditable(false);
		add(tfFirstName, "cell 6 5,growx");
		tfFirstName.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		add(lblLastName, "cell 4 6,alignx center");
		
		tfLastName = new JTextField();
		tfLastName.setEditable(false);
		add(tfLastName, "cell 6 6,growx");
		tfLastName.setColumns(10);
		
		JLabel lblSex = new JLabel("Sex");
		add(lblSex, "cell 4 7,alignx center");
		
		tfSex = new JTextField();
		tfSex.setEditable(false);
		add(tfSex, "cell 6 7,growx");
		tfSex.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		add(lblEmail, "cell 4 8,alignx center");
		
		tfEmail = new JTextField();
		tfEmail.setEditable(false);
		add(tfEmail, "cell 6 8,growx");
		tfEmail.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone");
		add(lblPhone, "cell 4 9,alignx center");
		
		tfPhone = new JTextField();
		tfPhone.setEditable(false);
		add(tfPhone, "cell 6 9,growx");
		tfPhone.setColumns(10);
		
		showDetails(con, pid);
	}
	
	public void showDetails(Connection con, int id){
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			pst = con.prepareStatement("select * from patient where pid = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if(rs.next()){
				//get his details
				tfId.setText(new String(""+rs.getInt(1)));
				tfFirstName.setText(rs.getString(2));
				tfLastName.setText(rs.getString(3));
				tfSex.setText(rs.getString(4));
				tfEmail.setText(rs.getString(5));
				tfPhone.setText(rs.getString(6));
			}
			else{
				System.out.println("Cannot get person's details at the moment");
			}
		}
		catch(Exception e){
			System.out.println("Exception occured in Patient Details: "+e);
			try
			{
				if(con!=null)				
					con.rollback();
			}
			catch(Exception ex)
			{
				System.out.println("Exception: "+ex);
			}
			System.out.println("Transaction rolled back..Try Again");
		}
	}
}
