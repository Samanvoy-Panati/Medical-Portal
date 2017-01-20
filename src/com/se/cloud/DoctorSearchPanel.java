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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class DoctorSearchPanel extends JPanel {
	
		Connection con = null;
	
		private JTextField tfPID;
		private JTextField tfLast;
		private JLabel lblPID;
		private JLabel lblFN;
		private JLabel lblLN;
		private JLabel lblSex;
		private JLabel lblEmail;
		
		private JButton btnLNSearch;
		private JButton btnPIDSearch;
		private JButton btnBack;
		private JLabel lblNoMatch;
		
		public DoctorSearchPanel(Connection con, int did, JFrame parent) {
			setLayout(new MigLayout("", "[][][30][31.00][19.00][][76.00][][][][21.00][][][]", "[][34.00][][][][][][][][][]"));
			
			btnBack = new JButton("Back");
			add(btnBack, "cell 12 0");
			
			JLabel lblHeading = new JLabel("SEARCH A PATIENT ");
			lblHeading.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
			lblHeading.setForeground(Color.BLUE);
			add(lblHeading, "cell 4 2 8 1,alignx center");
			
			JLabel lblPatientId = new JLabel("Patient Id");
			add(lblPatientId, "cell 6 4,alignx left");
			
			tfPID = new JTextField();
			add(tfPID, "cell 7 4,growx");
			tfPID.setColumns(10);
			
			btnPIDSearch = new JButton("Search");
			add(btnPIDSearch, "cell 9 4");
			
			JLabel lblLastName = new JLabel("Last Name");
			add(lblLastName, "cell 6 5,alignx left");
			
			tfLast = new JTextField();
			add(tfLast, "cell 7 5,growx");
			tfLast.setColumns(10);
			
			btnLNSearch = new JButton("Search");
			add(btnLNSearch, "cell 9 5");
			
			lblNoMatch = new JLabel("Patient not found!!!");
			lblNoMatch.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNoMatch.setForeground(Color.RED);
			add(lblNoMatch, "cell 6 6 4 1,alignx center");
			
			lblPID = new JLabel("Patient Id");
			lblPID.setForeground(Color.BLUE);
			add(lblPID, "cell 5 7");
			lblPID.setVisible(false);
			
			lblFN = new JLabel("First Name");
			lblFN.setForeground(Color.BLUE);
			add(lblFN, "cell 6 7");
			lblFN.setVisible(false);
			
			lblLN = new JLabel("Last Name");
			lblLN.setForeground(Color.BLUE);
			add(lblLN, "cell 7 7");
			lblLN.setVisible(false);
			
			lblSex = new JLabel("Sex");
			lblSex.setForeground(Color.BLUE);
			add(lblSex, "cell 8 7");
			lblSex.setVisible(false);
			
			
			lblEmail = new JLabel("Email");
			lblEmail.setForeground(Color.BLUE);
			add(lblEmail, "cell 9 7");
			lblEmail.setVisible(false);
			
			
			lblNoMatch.setVisible(false);
			this.con = con;
			
			
			actions(did);
			
			btnBack.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					parent.setVisible(false);
					new DoctorOptions(con, did);
				}
			});
			

		}
		
		public void actions(int did){
			
			btnPIDSearch.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					PreparedStatement pst1 = null;
					ResultSet rs1 = null;
					int pid = 0;
					try{
						if(!tfPID.getText().equals("")){
							System.out.println("First search button if");
							pid = Integer.parseInt(tfPID.getText());
							pst1 = con.prepareStatement("select pid, firstname, lastname, sex, email from patient where pid = ?");
							pst1.setInt(1, pid);
							rs1 = pst1.executeQuery();
							boolean flag = true;
							while(rs1.next()){
								flag = false;
								lblPID.setVisible(true);
								lblFN.setVisible(true);
								lblLN.setVisible(true);
								lblSex.setVisible(true);
								lblEmail.setVisible(true);
								
								JLabel lblNewLabel = new JLabel("New label");
								lblNewLabel.setText(""+rs1.getInt(1));
								add(lblNewLabel, "cell 5 8");
								
								JLabel lblNewLabel_1 = new JLabel("New label");
								lblNewLabel_1.setText(rs1.getString(2));
								add(lblNewLabel_1, "cell 6 8");
								
								JLabel lblNewLabel_2 = new JLabel("New label");
								lblNewLabel_2.setText(rs1.getString(3));
								add(lblNewLabel_2, "cell 7 8");
								
								JLabel lblNewLabel_3 = new JLabel("New label");
								lblNewLabel_3.setText(rs1.getString(4));
								add(lblNewLabel_3, "cell 8 8");
								
								JLabel lblNewLabel_4 = new JLabel("New label");
								lblNewLabel_4.setText(rs1.getString(5));
								add(lblNewLabel_4, "cell 9 8");							
							}
							if(flag){
								lblNoMatch.setVisible(true);
							}
							//pid is found... So show his treatment history
							else{
								showTreatments(pid);
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

	//No changes made--want to remove this
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
							pst1 = con.prepareStatement("select pid, firstname, lastname, sex, email from patient where lower(lastname) = ?");
							pst1.setString(1, lastName);
							rs1 = pst1.executeQuery();
							int i = 2; 	
							boolean flag = true;
							while(rs1.next()){
								flag = false;
								lblPID.setVisible(true);
								lblFN.setVisible(true);
								lblLN.setVisible(true);
								lblSex.setVisible(true);
								lblEmail.setVisible(true);
								
								JLabel lblNewLabel = new JLabel("New label");
								lblNewLabel.setText(""+rs1.getInt(1));
								add(lblNewLabel, "cell 5 "+(i+6));
								
								JLabel lblNewLabel_1 = new JLabel("New label");
								lblNewLabel_1.setText(rs1.getString(2));
								add(lblNewLabel_1, "cell 6 "+(i+6));
								
								JLabel lblNewLabel_2 = new JLabel("New label");
								lblNewLabel_2.setText(rs1.getString(3));
								add(lblNewLabel_2, "cell 7 "+(i+6));
								
								JLabel lblNewLabel_3 = new JLabel("New label");
								lblNewLabel_3.setText(rs1.getString(4));
								add(lblNewLabel_3, "cell 8 "+(i+6));
								
								JLabel lblNewLabel_4 = new JLabel("New label");
								lblNewLabel_4.setText(rs1.getString(5));
								add(lblNewLabel_4, "cell 9 "+(i+6));		
								
								i++;
							}
							if(flag){
								lblNoMatch.setVisible(true);
							}
							//Last name is found... So show his treatment history
							else{
								PreparedStatement pst2 = null;
								ResultSet rs2 = null;
								int pid= 0;
								pst2 = con.prepareStatement("select pid from patient where lower(lastname) = ?");
								pst2.setString(1, lastName);
								rs2 = pst2.executeQuery();
								if(rs2.next())
									pid = rs2.getInt(1);
								showTreatments(pid);
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
		public void showTreatments(int pid){
			
			PreparedStatement pst1 = null;
			//PreparedStatement pst2 = null;
			ResultSet rs1 = null;
			//ResultSet rs2 = null;
			JLabel lbl1;
			JLabel lbl2;
			JLabel lbl3;
			JLabel lbl4;
			JLabel lbl5;
			
			try{
				pst1 = con.prepareStatement("select bdate, time, category, treatment, note from treatment where pid = ?");
				pst1.setInt(1, pid);
				rs1 = pst1.executeQuery();
				int i = 1;
				//Create labels and text areas foe each row
				String date = null;
				String category = null;
				String treatment = null;
				String note = null;
				String time = null;
				
				lbl1 = new JLabel("Category");
				lbl2 = new JLabel("Date");
				lbl3 = new JLabel("Time");
				lbl4 = new JLabel("Note");
				lbl5 = new JLabel("Treatment");
				lbl1.setForeground(Color.BLUE);
				lbl2.setForeground(Color.BLUE);
				lbl3.setForeground(Color.BLUE);
				lbl4.setForeground(Color.BLUE);
				lbl5.setForeground(Color.BLUE);
				
				add(lbl1, "cell 4 9 2 1");
				add(lbl2, "cell 5 9 2 1");
				add(lbl3, "cell 6 9 2 1");
				add(lbl5, "cell 7 9 2 1");
				add(lbl4, "cell 9 9 2 1");
				
				
				JLabel lblNo = new JLabel("No previous treatments found!!!");
				lblNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblNo.setForeground(new Color(255, 0, 0));
				add(lblNo, "cell 6 9 4 1,alignx center");
				lblNo.setVisible(false);
				
				boolean flag = true;
				while(rs1.next()){
					flag = false;
					
					date = rs1.getString(1);
					time = rs1.getString(2);
					category = rs1.getString(3);
					treatment = rs1.getString(4);
					note = rs1.getString(5);
					/*
					pst2 = con.prepareStatement("select lastname from doctor where did = ?");
					pst2.setInt(1, did);
					rs2 = pst2.executeQuery();
					
					if(rs2.next()){
						lastName = rs2.getString(1); 
					}
					*/
					
					JLabel lblDate = new JLabel("New label");
					lblDate.setText(date);
					add(lblDate, "cell 4 "+(i+9));
					
					JLabel lblTime = new JLabel("New label");
					lblTime.setText(time);
					add(lblTime, "cell 5 "+(i+9));
					
					JLabel lblCategory = new JLabel("New label");
					lblCategory.setText(category);
					add(lblCategory, "cell 6 "+(i+9));
					
					JTextArea taTreatment = new JTextArea(3,60);
					taTreatment.setEditable(false);
					taTreatment.setText(treatment);
					taTreatment.setLineWrap(true);
					JScrollPane scrollerTreatment = new JScrollPane(taTreatment);
					scrollerTreatment.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					scrollerTreatment.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					scrollerTreatment.setBounds((i+9), 4, 50, 40);
					add(scrollerTreatment, "cell 7 "+(i+9)+" 2 1");
					
					JTextArea taNote = new JTextArea(3,60);
					taNote.setEditable(false);
					taNote.setText(note);
					taNote.setLineWrap(true);
					JScrollPane scrollerNote = new JScrollPane(taNote);
					scrollerNote.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					scrollerNote.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					add(scrollerNote, "cell 9 "+(i+9)+" 2 1");
					
					i++;
				}
				if(flag){
					lblNo.setVisible(true);
					lbl1.setVisible(false);
					lbl2.setVisible(false);
					lbl3.setVisible(false);
					lbl4.setVisible(false);
					lbl5.setVisible(false);
				}
			}
			catch(Exception e){
				System.out.println("Exception occured in TreatmentPanel: "+e);
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


