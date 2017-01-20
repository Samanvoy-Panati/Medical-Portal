package com.se.cloud;

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

import net.miginfocom.swing.MigLayout;
import java.awt.Color;

@SuppressWarnings("serial")
public class ViewAppPanel extends JPanel {
	
	Connection con = null;
	
	private JLabel lblView;
	private JLabel lblDoctorId;
	private JLabel lblDoctorName;
	private JLabel lblCategory;
	private JLabel lblDate;
	private JLabel lblTime;
	public ViewAppPanel(Connection con, int pid, JFrame parent) {
		setLayout(new MigLayout("", "[32.00][80.00][80.00][80.00][80.00][80.00][80.00][30.00][][]", "[][41.00][30.00][][][][][][][][][][][][][]"));
		
		JButton btnBack = new JButton("Back");
		add(btnBack, "cell 9 0");
		
		lblView = new JLabel("MY APPOINTMENTS ");
		lblView.setForeground(new Color(0, 0, 255));
		lblView.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		add(lblView, "cell 2 2 5 1,alignx center");
		
		lblDoctorId = new JLabel("Doctor Id");
		lblDoctorId.setForeground(Color.BLUE);
		add(lblDoctorId, "cell 2 4");
		
		lblDoctorName = new JLabel("Name");
		lblDoctorName.setForeground(Color.BLUE);
		add(lblDoctorName, "cell 3 4");
		
		lblCategory = new JLabel("Category");
		lblCategory.setForeground(Color.BLUE);
		add(lblCategory, "cell 4 4");
		
		lblDate = new JLabel("Date");
		lblDate.setForeground(Color.BLUE);
		add(lblDate, "cell 5 4");
		
		lblTime = new JLabel("Time");
		lblTime.setForeground(Color.BLUE);
		add(lblTime, "cell 6 4");
		
		
		lblCategory.setVisible(false);
		lblDate.setVisible(false);
		lblDoctorId.setVisible(false);
		lblDoctorName.setVisible(false);
		lblTime.setVisible(false);
		this.con = con;
		getAppointments(pid);
		
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new PatientOptions(con, pid);
			}
		});
	}
	
	public void getAppointments(int pid){
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		try{
			pst1 = con.prepareStatement("select did, category, bdate, time from booking where pid = ?");
			pst2 = con.prepareStatement("select lastname from doctor where did = ?");
			pst1.setInt(1, pid);
			rs1 = pst1.executeQuery();
			int i = 1;
			int did = 0;
			String category = null;
			String date = null;
			String time = null;
			String lastname = null;
			//get the details and show
			while(rs1.next()){
				lblCategory.setVisible(true);
				lblDate.setVisible(true);
				lblDoctorId.setVisible(true);
				lblDoctorName.setVisible(true);
				lblTime.setVisible(true);
				
				did = rs1.getInt(1);
				category = rs1.getString(2);
				date = rs1.getString(3);
				time = rs1.getString(4);
				 
				pst2.setInt(1, did);
				rs2 = pst2.executeQuery();
				rs2.next();
				lastname = rs2.getString(1);
				System.out.println("row: "+i+4);
				JLabel lbl1 = new JLabel(""+did);
				add(lbl1, "cell 2 "+(i+4));

				JLabel lbl2 = new JLabel("Dr. "+lastname);
				add(lbl2, "cell 3 "+(i+4));
				
				JLabel lbl3 = new JLabel(category);
				add(lbl3, "cell 4 "+(i+4));
				
				JLabel lbl4 = new JLabel(date);
				add(lbl4, "cell 5 "+(i+4));
				
				JLabel lbl5 = new JLabel(time);
				add(lbl5, "cell 6 "+(i+4));
				
				i++;
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

}
