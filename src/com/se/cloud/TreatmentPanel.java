package com.se.cloud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("serial")
public class TreatmentPanel extends JPanel {
	
	Connection con = null;
	private JButton btnBack;
	private JLabel lblHeading;
	private JLabel lblDoctorId;
	private JLabel lblName;
	private JLabel lblCategory;
	private JLabel lblTreatment;
	private JLabel lblNote;
	private JLabel lblNo;
	
	
	public TreatmentPanel(Connection con, int pid, JFrame parent) {
		setLayout(new MigLayout("", "[38.00][50.00][50.00][50.00][100.00][100.00][50.00][50.00][][][]", "[57.00][34.00][24.00][][][][][][][]"));
		
		btnBack = new JButton("Back");
		add(btnBack, "cell 10 0");
		
		lblHeading = new JLabel("TREATMENT HISTORY ");
		lblHeading.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		lblHeading.setForeground(Color.BLUE);
		add(lblHeading, "cell 2 2 5 1,alignx center");
		
		lblNo = new JLabel("No previous treatments found");
		lblNo.setForeground(Color.RED);
		add(lblNo, "cell 2 3 6 1,alignx center");
		
		lblDoctorId = new JLabel("Doctor Id");
		lblDoctorId.setForeground(Color.BLUE);
		add(lblDoctorId, "cell 1 4");
		
		lblName = new JLabel("Name");
		lblName.setForeground(Color.BLUE);
		add(lblName, "cell 2 4");
		
		lblCategory = new JLabel("Category");
		lblCategory.setForeground(Color.BLUE);
		add(lblCategory, "cell 3 4");
		
		lblTreatment = new JLabel("Treatment");
		lblTreatment.setForeground(Color.BLUE);
		add(lblTreatment, "cell 4 4");
		
		lblNote = new JLabel("Note");
		lblNote.setForeground(Color.BLUE);
		add(lblNote, "cell 6 4");
		
		lblCategory.setVisible(false);
		lblDoctorId.setVisible(false);
		lblName.setVisible(false);
		lblNote.setVisible(false);
		lblTreatment.setVisible(false);
		
		this.con = con;
		lblNo.setVisible(false);
		actions(pid);
		
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new PatientOptions(con, pid);
			}
		});
	}
	
	public void actions(int pid){
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		try{
			pst1 = con.prepareStatement("select did, category, treatment, note from treatment where pid = ?");
			pst1.setInt(1, pid);
			rs1 = pst1.executeQuery();
			int i = 1;
			//Create labels and text areas foe each row
			int did = 0;
			String category = null;
			String treatment = null;
			String note = null;
			String lastName = null;
			
			boolean flag = true;
			while(rs1.next()){
				flag = false;
				lblCategory.setVisible(true);
				lblDoctorId.setVisible(true);
				lblName.setVisible(true);
				lblNote.setVisible(true);
				lblTreatment.setVisible(true);
				
				did = rs1.getInt(1);
				category = rs1.getString(2);
				treatment = rs1.getString(3);
				note = rs1.getString(4);
				
				pst2 = con.prepareStatement("select lastname from doctor where did = ?");
				pst2.setInt(1, did);
				rs2 = pst2.executeQuery();
				
				if(rs2.next()){
					lastName = rs2.getString(1); 
				}
				
				JLabel lblDid = new JLabel("New label");
				lblDid.setText(""+did);
				add(lblDid, "cell 1 "+(i+4));
				
				JLabel lblLastName = new JLabel("New label");
				lblLastName.setText(lastName);
				add(lblLastName, "cell 2 "+(i+4));
				
				JLabel lblCategory = new JLabel("New label");
				lblCategory.setText(category);
				add(lblCategory, "cell 3 "+(i+4));
				
				JTextArea taTreatment = new JTextArea(3,60);
				taTreatment.setEditable(false);
				taTreatment.setText(treatment);
				taTreatment.setLineWrap(true);
				JScrollPane scrollerTreatment = new JScrollPane(taTreatment);
				scrollerTreatment.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollerTreatment.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scrollerTreatment.setBounds((i+4), 4, 50, 40);
				add(scrollerTreatment, "cell 4 "+(i+4)+" 2 1");
				
				JTextArea taNote = new JTextArea(3,60);
				taNote.setEditable(false);
				taNote.setText(note);
				taNote.setLineWrap(true);
				JScrollPane scrollerNote = new JScrollPane(taNote);
				scrollerNote.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollerNote.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				add(scrollerNote, "cell 6 "+(i+4)+" 2 1");
				
				i++;
			}
			if(flag){
				lblNo.setVisible(true);
				lblCategory.setVisible(false);
				lblDoctorId.setVisible(false);
				lblName.setVisible(false);
				lblNote.setVisible(false);
				lblTreatment.setVisible(false);
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
