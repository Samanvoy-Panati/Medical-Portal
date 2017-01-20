package com.se.cloud;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/*
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.activation.*;
*/
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class DoctorAddTreatmentPanel extends JPanel {
	
	Connection con = null;
	private JTextField tfDID;
	private JTextField tfPID;
	private JTextArea taNotes;
	private JTextArea taTreatment;
	
	@SuppressWarnings("rawtypes")
	private JList listCategory;
	@SuppressWarnings("rawtypes")
	private JList listTime;
	
	private JButton btnAdd;
	private JButton btnClear;
	private JLabel lblDate;
	private JLabel lblTime;
	private JTextField tfDate;
	private JLabel lblPlease;
	private JLabel lblBloodPressure;
	private JTextField tfBP;
	private JButton btnApply;
	private JLabel lblSuccess;
	private JButton btnApplyTreatment;
	private JButton btnEmailPatient;
	private JLabel lblMmddyyyy;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DoctorAddTreatmentPanel(Connection con, int did, JFrame parent) {
		setLayout(new MigLayout("", "[grow][grow][37.00,grow][12.00][86.00][170.00][37.00][grow][grow][grow][][][][][]", "[][36.00][][][][][][][][][][][][][]"));
		
		
		
		JButton btnBack = new JButton("Back");
		add(btnBack, "cell 14 0");
		
		this.con = con;
		
		JLabel lblHeading = new JLabel("ADD TREATMENT ");
		lblHeading.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		lblHeading.setForeground(Color.BLUE);
		add(lblHeading, "cell 4 1 5 1,alignx center");
		
		lblSuccess = new JLabel("Treatment added successfully!!!");
		lblSuccess.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSuccess.setForeground(Color.GREEN);
		add(lblSuccess, "cell 4 2 3 1,alignx center");
		
		JLabel lblDoctorId = new JLabel("Doctor Id *");
		add(lblDoctorId, "cell 4 3,alignx left");
		
		tfDID = new JTextField();
		add(tfDID, "cell 5 3,growx");
		tfDID.setColumns(10);
		
		JLabel lblPatientId = new JLabel("Patient Id *");
		add(lblPatientId, "cell 4 4,alignx left");
		
		tfPID = new JTextField();
		add(tfPID, "cell 5 4,growx");
		tfPID.setColumns(10);
		
		JLabel lblCategory = new JLabel("Category *");
		add(lblCategory, "cell 4 5,alignx left");
		
		String[] categories = {"cardiologist", "bones", "dentist"};
		listCategory = new JList(categories);
		listCategory.setVisibleRowCount(3);
		listCategory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollerCategory = new JScrollPane(listCategory);
		add(scrollerCategory, "cell 5 5,growx");
		
		lblDate = new JLabel("Date *");
		add(lblDate, "cell 4 6,alignx left");
		
		tfDate = new JTextField();
		add(tfDate, "cell 5 6,growx");
		tfDate.setColumns(10);
		
		String[] dates = {"09:00 AM","09:30 AM","10:00 AM","10:30 AM","11:00 AM","11:30 AM","12:00 PM","12:30 PM","01:00 PM","01:30 PM","02:00 PM","02:30 PM"
				,"03:00 PM","03:30 PM","04:00 PM","04:30 PM","05:00 PM","05:30 PM","06:00 PM","06:30 PM",};
		
		lblMmddyyyy = new JLabel("MM-DD-YYYY");
		add(lblMmddyyyy, "cell 6 6");
		listTime = new JList(dates);
		listTime.setVisibleRowCount(3);
		listTime.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollerTime = new JScrollPane(listTime);
		add(scrollerTime, "cell 5 7,growx");
		
		
		lblTime = new JLabel("Time *");
		add(lblTime, "cell 4 7,alignx left");
		
		lblBloodPressure = new JLabel("Blood Pressure");
		add(lblBloodPressure, "cell 4 8,alignx trailing");
		
		tfBP = new JTextField();
		add(tfBP, "cell 5 8,growx,aligny top");
		tfBP.setColumns(10);
		
		btnApply = new JButton("Apply");
		add(btnApply, "cell 6 8");
		
		btnApplyTreatment = new JButton("Apply");
		add(btnApplyTreatment, "cell 6 9,aligny center");
		btnApplyTreatment.setVisible(false);
		
		JLabel lblTreatment = new JLabel("Treatment *");
		add(lblTreatment, "cell 4 9,alignx left,aligny center");
		this.con =con;
		
		taTreatment = new JTextArea(3,20);
		taTreatment.setEditable(false);
		taTreatment.setLineWrap(true);
		JScrollPane scrollerTreatment = new JScrollPane(taTreatment);
		scrollerTreatment.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollerTreatment.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollerTreatment.setBounds(9, 5, 50, 40);
		add(scrollerTreatment, "cell 5 9,growx,aligny baseline");
		
		
		JLabel lblNotes = new JLabel("Notes");
		add(lblNotes, "cell 4 11,alignx left,aligny center");
		
		taNotes = new JTextArea(3,20);
		taNotes.setEditable(false);
		taNotes.setLineWrap(true);
		JScrollPane scrollerNote = new JScrollPane(taNotes);
		scrollerNote.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollerNote.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollerNote, "cell 5 11,growx,aligny baseline");
		
		
		
		lblPlease = new JLabel("Please enter all the required fields");
		lblPlease.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPlease.setForeground(Color.RED);
		add(lblPlease, "cell 4 12 3 1,alignx center");
		
		lblPlease.setVisible(false);
		
		btnAdd = new JButton("Add");
		add(btnAdd, "cell 4 13");
		
		btnClear = new JButton("Clear");
		add(btnClear, "cell 5 13");
		
		btnEmailPatient = new JButton("email patient");
		add(btnEmailPatient, "cell 6 11,aligny center");
		btnEmailPatient.setVisible(false);
		
		lblSuccess.setVisible(false);
		actions(did);
		bloodPressure(did);
		
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
		
		btnAdd.addActionListener(new ActionListener() {
			
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PreparedStatement pst1 = null;
				PreparedStatement pst2 = null;
				ResultSet rs1 = null;
				int bid = 0;
				int pid = 0;
				int did = 0;
				String category = null;
				String treatment = null;
				String note = null;
				String date = null;
				String time = null;
				try{
					System.out.println("Inside try");
					
					if(tfPID.getText().equals("")|| tfDID.getText().equals("") || listCategory.getSelectedValue()==null
							|| taTreatment.getText().equals("") || listTime.getSelectedValue()==null
							|| tfDate.getText().equals("")){
						lblPlease.setVisible(true);
					}
					else{
						System.out.println("In else");
						pst1 = con.prepareStatement("insert into treatment values (?,?,?,?,?,?,?,?)");
						System.out.println("after pst1");
						did = Integer.parseInt(tfDID.getText());
						pid = Integer.parseInt(tfPID.getText());
						category = listCategory.getSelectedValue().toString();
						treatment = taTreatment.getText();
						note = taNotes.getText();
						date = tfDate.getText();
						time = listTime.getSelectedValue().toString();
						System.out.println("before pst2");
						//to get bid
						pst2 = con.prepareStatement("select bid from booking where pid = ? and did = ? and "
								+ "bdate = ? and time = ?");
						pst2.setInt(1, pid);
						pst2.setInt(2, did);
						pst2.setString(3, date);
						pst2.setString(4, time);
						rs1 = pst2.executeQuery();
						if(rs1.next()){
							bid = rs1.getInt(1);
							System.out.println("retrieving bid");
						}
						pst1.setInt(1, bid);
						pst1.setInt(2, did);
						pst1.setInt(3, pid);
						pst1.setString(4, category);
						pst1.setString(5, treatment);
						pst1.setString(6, note);
						pst1.setString(7, date);
						pst1.setString(8, time);
						System.out.println("Before executing pst1");
						int i = pst1.executeUpdate();
						if(i!=0){
							System.out.println("Inserted into treatment table");
							lblSuccess.setVisible(true);
						}
						
					}
					
					
				}
				catch(Exception e1){
					System.out.println("Exception occured in Add TreatmentPanel: "+e1);
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
		
		btnClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tfDID.setText("");
				tfPID.setText("");
				taNotes.setText("");
				taTreatment.setText("");
				tfDate.setText("");
			}
		});
		
	}
	
	public void bloodPressure(int did){
		btnApply.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					BufferedReader br = new BufferedReader(new FileReader("E:\\Workspace_Mars\\Medical_Portal\\src\\1002.txt"));
				    String line = br.readLine();

				    String[] readings = line.split(" ");
				    String systolic = readings[0];
				    String diastolic = readings[1];
				    
				    //Now write this into the box
				    tfBP.setText("systolic: "+systolic+", diastolic: "+diastolic);
				    if(Integer.parseInt(systolic)>120 || Integer.parseInt(diastolic) > 90){
				    	//Adding button for directly adding prescription
				    	System.out.println("I am in this loop");
				    	btnApplyTreatment.setVisible(true);
						applyTreatment(did);
						//Showing the warning message
				    	JOptionPane.showMessageDialog(new JFrame(), "High Blood Pressure!!!","Medication Required",JOptionPane.WARNING_MESSAGE);
				    }
				    else{
				    	JOptionPane.showMessageDialog(new JFrame(), "Blood Pressure Normal!","You are Good",JOptionPane.PLAIN_MESSAGE);
				    }
				    br.close();
				    
				}
				catch(FileNotFoundException e3){
					System.out.println("File not found: "+e3);
				}
				catch(Exception e4){
					System.out.println("Exception occured: "+e4);
				}
				
			}
		});
	}
	
	public void applyTreatment(int did){
		btnApplyTreatment.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				taTreatment.setText("Lisinopril oral--Take this medication by mouth     with or without food as directed by your doctor, usually once daily.");
				taNotes.setText("It may take 2 to 4 weeks before you get the full benefit of this medication. Visit after three weeks");
				
				//btnEmailPatient.setVisible(true);
				//sendEmail(did);
			}
		});
	}
	
	//Tried to send email but not successful
	/*
	public void sendEmail(int did){
		btnEmailPatient.addActionListener(new ActionListener() {
			
			int pid = 0;
			PreparedStatement pst1 = null;
			ResultSet rs1 = null;
			PreparedStatement pst2 = null;
			ResultSet rs2 = null;
			String to = null;
			String from  = null;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					pid = Integer.parseInt(tfPID.getText());
					if(pid==0){
						lblPlease.setVisible(true);
					}
					else{
						pst1 = con.prepareStatement("select email from patient where pid = ?");
						pst1.setInt(1, pid);
						rs1 = pst1.executeQuery();
						if(rs1.next()){
							to = rs1.getString(1);
						}
						pst2 = con.prepareStatement("select email from doctor where did = ?");
						pst2.setInt(1, did);
						rs2 = pst2.executeQuery();
						if(rs2.next()){
							from = rs2.getString(1);
						}
						from = "samanvoypanati@gmail.com";
						to = "samanvoypanati@gmail.com";
						//from = "srp71@pitt.edu";
						//to = "srp71@pitt.edu";
								
						
						System.out.println("From address: "+from);
						System.out.println("To address: "+to);
						
						
						String subject = "Your prescription for the appointment on "+tfDate.getText()+" at time \n";
						String message = "Treatment: "+taTreatment.getText()+" \nNotes: "+taNotes.getText()+" \n";
						
						emailSender(from, "*******", subject, message, to);
						
					}
					
				}
				catch(Exception e1){
					System.out.println("Exception occured in DoctorAddTreatment in sending email: "+e1);
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
	}*/
	//Tried to send email but not successful
	/*
	public boolean emailSender(String from, String password, String subject, String message, String to){
		String host = "smtp.gmail.com";
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getInstance(props,null);
		MimeMessage mimeMessage = new MimeMessage(session);
		try{
			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.addRecipient(RecipientType.TO , new InternetAddress(to));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(message);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, password);
			transport.send(mimeMessage, mimeMessage.getAllRecipients());
			transport.close();
			return true;
		}
		catch(MessagingException me){
			me.printStackTrace();
		}
		return false;
	}*/
}
