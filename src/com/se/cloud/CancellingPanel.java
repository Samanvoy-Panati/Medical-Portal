package com.se.cloud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("serial")
public class CancellingPanel extends JPanel {
	
	Connection con = null;
	ArrayList<Integer> bookingIds = new ArrayList<Integer>();
	private JLabel lblNoApp;
	private JLabel lblSuccess;
	
	private JLabel lblDoctorId;
	private JLabel lblName;
	private JLabel lblCategory;
	private JLabel lblDate;
	private JLabel lblTime;
	
	public CancellingPanel(Connection con, int pid, JFrame parent) {
		setLayout(new MigLayout("", "[][50.00][50.00][50.00][50.00][50.00][42.00][37.00][39.00][34.00][34.00][]", "[][44.00][30.00][][][][][][][]"));
		
		JButton btnBack = new JButton("Back");
		add(btnBack, "cell 11 0");
		
		lblSuccess = new JLabel("Booking Cancelled!!!");
		lblSuccess.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSuccess.setForeground(Color.GREEN);
		add(lblSuccess, "cell 3 1 6 1,alignx center");
		
		JLabel lblSelect = new JLabel("CANCEL AN APPOINTMENT ");
		lblSelect.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		lblSelect.setForeground(Color.BLUE);
		add(lblSelect, "cell 2 2 9 1,alignx center");
		
		lblNoApp = new JLabel("No Appointments found ");
		lblNoApp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNoApp.setForeground(Color.RED);
		add(lblNoApp, "cell 3 3 6 1,alignx center");
		
		lblDoctorId = new JLabel("Doctor Id");
		add(lblDoctorId, "cell 3 4");
		
		lblName = new JLabel("Name");
		add(lblName, "cell 4 4");
		
		lblCategory = new JLabel("Category");
		add(lblCategory, "cell 5 4");
		
		lblDate = new JLabel("Date");
		add(lblDate, "cell 6 4");
		
		lblTime = new JLabel("Time");
		add(lblTime, "cell 7 4");
		
		lblSuccess.setVisible(false);
		lblNoApp.setVisible(false);
		
		lblCategory.setVisible(false);
		lblDate.setVisible(false);
		lblDoctorId.setVisible(false);
		lblName.setVisible(false);
		lblTime.setVisible(false);
		this.con = con;
		
		cancel(pid);
		
		btnBack.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new PatientOptions(con, pid);
			}
		});
	}

	public void cancel(int pid){
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		try{
			pst1 = con.prepareStatement("select did, category, bdate, time, bid from booking where pid = ?");
			pst2 = con.prepareStatement("select lastname from doctor where did = ?");
			pst1.setInt(1, pid);
			rs1 = pst1.executeQuery();
			int i = 1;
			int did = 0;
			String category = null;
			String date = null;
			String time = null;
			String lastname = null;
			int flag = 0;
			//get the details and show
			while(rs1.next()){
				lblCategory.setVisible(true);
				lblDate.setVisible(true);
				lblDoctorId.setVisible(true);
				lblName.setVisible(true);
				lblTime.setVisible(true);
				
				flag = 1;
				did = rs1.getInt(1);
				category = rs1.getString(2);
				date = rs1.getString(3);
				time = rs1.getString(4);
				bookingIds.add(rs1.getInt(5));
				 
				pst2.setInt(1, did);
				rs2 = pst2.executeQuery();
				rs2.next();
				lastname = rs2.getString(1);
				
				JButton btnCancel = new JButton("Cancel "+i);
				add(btnCancel, "cell 2 "+i+4);
				
				JLabel lbl1 = new JLabel(""+did);
				add(lbl1, "cell 3 "+i+4);

				JLabel lbl2 = new JLabel("Dr. "+lastname);
				add(lbl2, "cell 4 "+i+4);
				
				JLabel lbl3 = new JLabel(category);
				add(lbl3, "cell 5 "+i+4);
				
				JLabel lbl4 = new JLabel(date);
				add(lbl4, "cell 6 "+i+4);
				
				JLabel lbl5 = new JLabel(time);
				add(lbl5, "cell 7 "+i+4);
				
				i++;
				cancelButtonActions(pid, btnCancel);
			}
			//No appointments found
			if(flag == 0){
				lblNoApp.setVisible(true);
			}
			
		}
		catch(Exception e){
			System.out.println("Exception occured in View appointments: "+e);
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
	
	public void cancelButtonActions(int pid, JButton btnCancel){
		btnCancel.addActionListener(new ActionListener() {
			String buttonText = null;
			PreparedStatement pst = null;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					pst = con.prepareStatement("delete from booking where bid =  ?");
					buttonText = btnCancel.getText();
					int rowToDelete = Integer.parseInt(""+buttonText.charAt(buttonText.length()-1));
					int bidToDelete = bookingIds.get(rowToDelete-1);
					pst.setInt(1, bidToDelete);
					pst.executeUpdate();
					System.out.println("Booking deleted succesfully");
					lblSuccess.setVisible(true);
				}
				catch(Exception e1){
					System.out.println("Exception occured in Cancel appointments: "+e1);
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
