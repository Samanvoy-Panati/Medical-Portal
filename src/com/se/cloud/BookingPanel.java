package com.se.cloud;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;
import java.awt.Font;

@SuppressWarnings("serial")
public class BookingPanel extends JPanel {
	private JTextField tfDocID;
	private JTextField tfDate;
	//private JTextField tfAppId;
	
	JButton btnSearch;
	JButton btnClear;
	JButton btnDoc1;
	JButton btnDoc2;
	JButton btnDoc3;
	JButton btnDoc4;
	JButton btnDoc5;
	JButton btnDoc6;
	
	JLabel lblAvailability;
	
	int pid;
	Connection con = null;
	private JLabel lblDateFormat;
	@SuppressWarnings("rawtypes")
	private JList listTime;
	private JLabel lblPlease;
	@SuppressWarnings("rawtypes")
	private JList listCategory;
	private JButton btnBack;
	private JFrame parent;
	private JLabel lblHeading;
	private JLabel lblSuccess;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BookingPanel(Connection con, int pid, JFrame parent) {
		setLayout(new MigLayout("", "[8.00][][18.00][60.00][60.00][11.00][100.00][60.00][]", "[][][38.00][24.00][9.00][37.00][26.00][26.00][36.00][][][25.00][][][]"));
		/*
		JLabel lblAppointmentId = new JLabel("Appointment ID");
		add(lblAppointmentId, "cell 4 2");
		
		tfAppId = new JTextField();
		tfAppId.setEditable(false);
		add(tfAppId, "cell 6 2,growx");
		tfAppId.setColumns(10);
		*/
		
		btnBack = new JButton("Back");
		add(btnBack, "cell 8 0");
		
		lblSuccess = new JLabel("Appointment booked!!!");
		lblSuccess.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSuccess.setForeground(Color.GREEN);
		add(lblSuccess, "cell 4 2 3 1,alignx center");
		
		lblHeading = new JLabel("BOOK AN APPOINTMENT ");
		lblHeading.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		lblHeading.setForeground(Color.BLUE);
		add(lblHeading, "cell 3 3 5 1,alignx center,aligny baseline");
		
		
		JLabel lblType = new JLabel("Type");
		add(lblType, "cell 4 5,alignx center,aligny center");
		
		String[] categories = {"cardiologist", "bones", "dentist"};
		listCategory = new JList(categories);
		listCategory.setVisibleRowCount(3);
		listCategory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollerCategory = new JScrollPane(listCategory);
		add(scrollerCategory, "cell 6 5,grow");
		
		JLabel lblDoctorId = new JLabel("Doctor Id");
		add(lblDoctorId, "cell 4 6,alignx center");
		
		tfDocID = new JTextField();
		add(tfDocID, "cell 6 6,growx");
		tfDocID.setColumns(10);
		
		JLabel lblDate = new JLabel("Date*");
		add(lblDate, "cell 4 7,alignx center");
		
		tfDate = new JTextField();
		add(tfDate, "cell 6 7,growx");
		tfDate.setColumns(10);
		
		lblDateFormat = new JLabel("MM-DD-YYYY");
		add(lblDateFormat, "cell 7 7,alignx center");
		
		JLabel lblTime = new JLabel("Time*");
		add(lblTime, "cell 4 8,alignx center");
		
		String[] dates = {"09:00 AM","09:30 AM","10:00 AM","10:30 AM","11:00 AM","11:30 AM","12:00 PM","12:30 PM","01:00 PM","01:30 PM","02:00 PM","02:30 PM"
				,"03:00 PM","03:30 PM","04:00 PM","04:30 PM","05:00 PM","05:30 PM","06:00 PM","06:30 PM",};
		listTime = new JList(dates);
		listTime.setVisibleRowCount(3);
		listTime.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollerTime = new JScrollPane(listTime);
		add(scrollerTime, "cell 6 8,grow");
		
		lblPlease = new JLabel("Please enter all the required fields and atleast one from TYPE and DOCTOR ID fields");
		lblPlease.setForeground(Color.RED);
		add(lblPlease, "cell 2 9 6 1");
		lblPlease.setVisible(false);
		
		btnSearch = new JButton("Search");
		add(btnSearch, "cell 4 10,alignx center");
		
		btnClear = new JButton("Clear");
		add(btnClear, "cell 6 10");
		
		lblAvailability = new JLabel("Doctor is not available for this timing..");
		lblAvailability.setForeground(Color.RED);
		add(lblAvailability, "cell 3 11 5 1,alignx center");
		
		
		btnDoc1 = new JButton("New button");
		add(btnDoc1, "cell 3 12");
		
		btnDoc2 = new JButton("New button");
		add(btnDoc2, "cell 4 12,aligny bottom");
		
		btnDoc3 = new JButton("New button");
		add(btnDoc3, "cell 6 12");
		
		btnDoc4 = new JButton("New button");
		add(btnDoc4, "cell 3 13,alignx center");
		
		btnDoc5 = new JButton("New button");
		add(btnDoc5, "cell 4 13");
		
		btnDoc6 = new JButton("New button");
		add(btnDoc6, "cell 6 13");
		
		btnDoc1.setVisible(false);
		btnDoc2.setVisible(false);
		btnDoc3.setVisible(false);
		btnDoc4.setVisible(false);
		btnDoc5.setVisible(false);
		btnDoc6.setVisible(false);
		
		lblSuccess.setVisible(false);
		lblAvailability.setVisible(false);
		
		this.parent = parent;
		this.pid = pid;
		this.con = con;
		actions();
	}
	
	public void actions(){
		
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				btnDoc1.setVisible(false);
				btnDoc2.setVisible(false);
				btnDoc3.setVisible(false);
				btnDoc4.setVisible(false);
				btnDoc5.setVisible(false);
				btnDoc6.setVisible(false);
				
				Statement st = null;
				PreparedStatement pst = null;
				PreparedStatement pst1 = null;
				PreparedStatement pst2 = null;
				ResultSet rs = null;
				ResultSet rs1 = null;
				ResultSet rs2 = null;
				ResultSet rs3 = null;
				ArrayList<Integer> doctors = new ArrayList<Integer>(); 
				ArrayList<Integer> doctorsAvailable = new ArrayList<Integer>();
				
				try{
					//getting booking ID by counting rows
					st = con.createStatement();
					rs1 = st.executeQuery("select count(*) from booking");
					rs1.next();
					int bookId = rs1.getInt(1)+1;
					
					int docId = 0;
					String category = null;
					String time = null;
					String date = null;
					
					//getting doctor ID or type or both
					if(!tfDocID.getText().equals("")){
						System.out.println("getting DocId from tf");
						docId = Integer.parseInt(tfDocID.getText());
					}
					if(listCategory.getSelectedValue()!=null){
						System.out.println("getting category from tf");
						category = listCategory.getSelectedValue().toString();
					}
					if(!tfDate.getText().equals("")){
						System.out.println("Getting date from tf");
						date = tfDate.getText();
					}
						
					if (listTime.getSelectedValue()!=null){
						time = listTime.getSelectedValue().toString();
					}
					if( (docId==0 && category==null)|| time==null || date == null){
						lblPlease.setVisible(true);
					}
					else{
						if (docId!=0 && category!=null)
						{
							System.out.println("loop1");
							pst1 = con.prepareStatement("select * from booking where bdate = ? and time = ? and did = ? and category = ? ");
							pst1.setString(1, date);
							pst1.setString(2, time);
							pst1.setInt(3, docId);
							pst1.setString(4, category);
							rs1 = pst1.executeQuery();
							//Show the message that doctor is unavailable
							if(rs1.next()){
								System.out.println("loop1 if");
								lblAvailability.setVisible(true);
							}
							//Show the available doctors
							else{
								System.out.println("loop1 else");
								rs2 = st.executeQuery("select lastname from doctor where did = "+docId );
								rs2.next();
								String doc = rs2.getString(1);
								btnDoc2.setText(doc);
								btnDoc2.setVisible(true);
								inneractions(btnDoc2, bookId, docId, category, date, time);
							}
						}
						else if(docId == 0 && category!=null)
						{
							System.out.println("loop2");
							//Get the doctorIds from the booking table
							System.out.println("bdate: "+date+", time: "+time+", category: "+category);
							pst2 = con.prepareStatement("select did from booking where bdate = ? and time = ? and category = ? ");
							pst2.setString(1, date);
							pst2.setString(2, time);
							pst2.setString(3, category);
							rs2 = pst2.executeQuery();
							while(rs2.next()){
								System.out.println("loop2 while1");
								doctors.add(rs2.getInt(1));
							}
							//Get the doctorIds of the required category from doctor table
							rs3 = st.executeQuery("select did from doctor where category = '"+category+"'");
							
							while(rs3.next()){
								System.out.println("loop2 while2");
								int did = rs3.getInt(1);
								if(!doctors.contains(did)){
									doctorsAvailable.add(did);
								}
							}
							
							pst = con.prepareStatement("select lastname from doctor where did = ?");
							for(int i=1;i<=doctorsAvailable.size();i++){
								System.out.println("loop2 for");
								String buttonName = "btnDoc"+i;
								if(buttonName.equals("btnDoc1")){
									pst.setInt(1, doctorsAvailable.get(i-1));
									rs = pst.executeQuery();
									rs.next();
									btnDoc1.setText(rs.getString(1));
									btnDoc1.setVisible(true);
									inneractions(btnDoc1, bookId, doctorsAvailable.get(i-1), category, date, time);
								}
								if(buttonName.equals("btnDoc2")){
									pst.setInt(1, doctorsAvailable.get(i-1));
									rs = pst.executeQuery();
									rs.next();
									btnDoc2.setText(rs.getString(1));
									btnDoc2.setVisible(true);
									inneractions(btnDoc2, bookId, doctorsAvailable.get(i-1), category, date, time);
								}
								if(buttonName.equals("btnDoc3")){
									pst.setInt(1, doctorsAvailable.get(i-1));
									rs = pst.executeQuery();
									rs.next();
									btnDoc3.setText(rs.getString(1));
									btnDoc3.setVisible(true);
									inneractions(btnDoc3, bookId, doctorsAvailable.get(i-1), category, date, time);
								}
								if(buttonName.equals("btnDoc4")){
									pst.setInt(1, doctorsAvailable.get(i-1));
									rs = pst.executeQuery();
									rs.next();
									btnDoc4.setText(rs.getString(1));
									btnDoc4.setVisible(true);
									inneractions(btnDoc4, bookId, doctorsAvailable.get(i-1), category, date, time);
								}
								if(buttonName.equals("btnDoc5")){
									pst.setInt(1, doctorsAvailable.get(i-1));
									rs = pst.executeQuery();
									rs.next();
									btnDoc5.setText(rs.getString(1));
									btnDoc5.setVisible(true);
									inneractions(btnDoc5, bookId, doctorsAvailable.get(i-1), category, date, time);
								}
								if(buttonName.equals("btnDoc6")){
									pst.setInt(1, doctorsAvailable.get(i-1));
									rs = pst.executeQuery();
									rs.next();
									btnDoc6.setText(rs.getString(1));
									btnDoc6.setVisible(true);
									inneractions(btnDoc6, bookId, doctorsAvailable.get(i-1), category, date, time);
								}
							}
							if(doctorsAvailable.size()==0){
								lblAvailability.setVisible(true);
							}
							
						}
						else if(docId!=0 && category == null)
						{
							System.out.println("loop3");
							pst1 = con.prepareStatement("select * from booking where bdate = ? and time = ? and did = ? ");
							pst1.setString(1, date);
							pst1.setString(2, time);
							pst1.setInt(3, docId);
							rs3 = pst1.executeQuery();
							//Show the message that doctor is unavailable
							if(rs3.next()){
								System.out.println("loop3 if");
								lblAvailability.setVisible(true);
							}
							//Show the available doctors
							else{
								System.out.println("loop3 else");
								rs3 = st.executeQuery("select lastname from doctor where did = "+docId);
								rs3.next();
								String doc = rs3.getString(1);
								btnDoc2.setText(doc);
								btnDoc2.setVisible(true);
								inneractions(btnDoc2, bookId, docId, category, date, time);
							}
						}	
						
					}
						
					}
				catch(Exception e1){
					System.out.println("Exception occured in BookingPanel actions: "+e1);
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
				//tfAppId.setText("");
				tfDate.setText("");
				tfDocID.setText("");
				//list convert to default value if possible
				//listTime.setSelectedValue(anObject, shouldScroll);
				//tfTime.setText("");
				//tfType.setText("");
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new PatientOptions(con, pid);
			}
		});
	}
	
	public void inneractions(JButton b, int bookId, int did, String category, String date, String time) {
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//booking table has BID, DID, PID, category, bdate, time
				PreparedStatement pst = null;
				
				try{
					pst = con.prepareStatement("insert into booking values (?,?,?,?,?,?)");
					pst.setInt(1, bookId);
					pst.setInt(2, did);
					pst.setInt(3, pid);
					pst.setString(4, category);
					pst.setString(5, date);
					pst.setString(6, time);
					pst.executeUpdate();
					lblSuccess.setVisible(true);
				}
				catch(Exception e1){
					System.out.println("Exception occured in BookingPanel inneractions: "+e1);
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
