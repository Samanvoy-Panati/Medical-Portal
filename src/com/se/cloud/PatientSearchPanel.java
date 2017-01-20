package com.se.cloud;

import java.awt.Color;
import java.awt.Font;
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

@SuppressWarnings("serial")
public class PatientSearchPanel extends JPanel {
	private JTextField tfDID;
	private JTextField tfLast;
	
	Connection con = null;
	
	private JLabel lblDID;
	private JLabel lblFN;
	private JLabel lblLN;
	private JLabel lblSex;
	private JLabel lblCategory;
	
	private JButton btnLNSearch;
	private JButton btnPIDSearch;
	private JButton btnBack;
	private JLabel lblNoMatch;
	
	public PatientSearchPanel(Connection con, int pid, JFrame parent) {
		setLayout(new MigLayout("", "[][][23.00][][76.00][][][][23.00][][][][][]", "[][41.00][][][][][][][][][]"));
		
		btnBack = new JButton("Back");
		add(btnBack, "cell 12 0");
		
		JLabel lblHeading = new JLabel("SEARCH A DOCTOR ");
		lblHeading.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		lblHeading.setForeground(Color.BLUE);
		add(lblHeading, "cell 3 2 7 1,alignx center");
		
		JLabel lblDoctorId = new JLabel("Doctor Id");
		add(lblDoctorId, "cell 4 4,alignx left");
		
		tfDID = new JTextField();
		add(tfDID, "cell 5 4,growx");
		tfDID.setColumns(10);
		
		btnPIDSearch = new JButton("Search");
		add(btnPIDSearch, "cell 7 4");
		
		JLabel lblLastName = new JLabel("Last Name");
		add(lblLastName, "cell 4 5,alignx left");
		
		tfLast = new JTextField();
		add(tfLast, "cell 5 5,growx");
		tfLast.setColumns(10);
		
		btnLNSearch = new JButton("Search");
		add(btnLNSearch, "cell 7 5");
		
		lblNoMatch = new JLabel("Doctor not found!!!");
		lblNoMatch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNoMatch.setForeground(Color.RED);
		add(lblNoMatch, "cell 4 6 4 1,alignx center");
		
		lblDID = new JLabel("Doctor Id");
		lblDID.setForeground(Color.BLUE);
		add(lblDID, "cell 3 7");
		lblDID.setVisible(false);
		
		lblFN = new JLabel("First Name");
		lblFN.setForeground(Color.BLUE);
		add(lblFN, "cell 4 7");
		lblFN.setVisible(false);
		
		lblLN = new JLabel("Last Name");
		lblLN.setForeground(Color.BLUE);
		add(lblLN, "cell 5 7");
		lblLN.setVisible(false);
		
		lblSex = new JLabel("Sex");
		lblSex.setForeground(Color.BLUE);
		add(lblSex, "cell 6 7");
		lblSex.setVisible(false);
		
		
		lblCategory = new JLabel("Category");
		lblCategory.setForeground(Color.BLUE);
		add(lblCategory, "cell 7 7");
		lblCategory.setVisible(false);
		
		lblNoMatch.setVisible(false);
		this.con = con;
		
		

		actions();
		
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new PatientOptions(con, pid);
			}
		});
		

	}
	
	public void actions(){
		
		btnPIDSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PreparedStatement pst1 = null;
				ResultSet rs1 = null;
				int did = 0;
				try{
					if(!tfDID.getText().equals("")){
						System.out.println("First search button if");
						did = Integer.parseInt(tfDID.getText());
						pst1 = con.prepareStatement("select did, firstname, lastname, sex, category from doctor where did = ?");
						pst1.setInt(1, did);
						rs1 = pst1.executeQuery();
						boolean flag = true;
						while(rs1.next()){
							flag = false;
							lblDID.setVisible(true);
							lblFN.setVisible(true);
							lblLN.setVisible(true);
							lblSex.setVisible(true);
							lblCategory.setVisible(true);
							
							JLabel lblNewLabel = new JLabel("New label");
							lblNewLabel.setText(""+rs1.getInt(1));
							add(lblNewLabel, "cell 3 8");
							
							JLabel lblNewLabel_1 = new JLabel("New label");
							lblNewLabel_1.setText(rs1.getString(2));
							add(lblNewLabel_1, "cell 4 8");
							
							JLabel lblNewLabel_2 = new JLabel("New label");
							lblNewLabel_2.setText(rs1.getString(3));
							add(lblNewLabel_2, "cell 5 8");
							
							JLabel lblNewLabel_3 = new JLabel("New label");
							lblNewLabel_3.setText(rs1.getString(4));
							add(lblNewLabel_3, "cell 6 8");
							
							JLabel lblNewLabel_4 = new JLabel("New label");
							lblNewLabel_4.setText(rs1.getString(5));
							add(lblNewLabel_4, "cell 7 8");							
						}
						if(flag){
							lblNoMatch.setVisible(true);
						}
					}
				}
				catch(Exception e1){
					System.out.println("Exception occured in Patient Search panel at DID: "+e1);
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
		});

btnLNSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PreparedStatement pst1 = null;
				ResultSet rs1 = null;
				String lastName = null;
				try{
					if(!tfLast.getText().equals("")){
						System.out.println("Second search button if");
						lastName = tfLast.getText();
						pst1 = con.prepareStatement("select did, firstname, lastname, sex, category from doctor where lower(lastname) = ?");
						pst1.setString(1, lastName);
						rs1 = pst1.executeQuery();
						int i = 2; 	
						boolean flag = true;
						while(rs1.next()){
							flag = false;
							lblDID.setVisible(true);
							lblFN.setVisible(true);
							lblLN.setVisible(true);
							lblSex.setVisible(true);
							lblCategory.setVisible(true);
							
							JLabel lblNewLabel = new JLabel("New label");
							lblNewLabel.setText(""+rs1.getInt(1));
							add(lblNewLabel, "cell 3 "+(i+6));
							
							JLabel lblNewLabel_1 = new JLabel("New label");
							lblNewLabel_1.setText(rs1.getString(2));
							add(lblNewLabel_1, "cell 4 "+(i+6));
							
							JLabel lblNewLabel_2 = new JLabel("New label");
							lblNewLabel_2.setText(rs1.getString(3));
							add(lblNewLabel_2, "cell 5 "+(i+6));
							
							JLabel lblNewLabel_3 = new JLabel("New label");
							lblNewLabel_3.setText(rs1.getString(4));
							add(lblNewLabel_3, "cell 6 "+(i+6));
							
							JLabel lblNewLabel_4 = new JLabel("New label");
							lblNewLabel_4.setText(rs1.getString(5));
							add(lblNewLabel_4, "cell 7 "+(i+6));		
							
							i++;
						}
						if(flag){
							lblNoMatch.setVisible(true);
						}
					}
				}
				catch(Exception e2){
					System.out.println("Exception occured in Patient Search panel at Last Name: "+e2);
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
		});
	}	
}
