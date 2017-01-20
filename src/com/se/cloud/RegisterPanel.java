package com.se.cloud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("serial")
public class RegisterPanel extends JPanel {
	private JTextField tfFirstName;
	private JTextField tfLastName;
	private JTextField tfEmail;
	private JTextField tfPhone;
	private JTextField tfLogin;
	private JPasswordField pfPwd;
	private JPasswordField pfRePwd;
	
	private JButton btnSubmit;
	private JButton btnCancel;
	private JButton btnBack;
	private JList<String> listSex;
	private JLabel lblPlease;
	private JLabel lblSuccess;
	private JLabel lblHeading;
	private JLabel lblDoNotMatch;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RegisterPanel(Connection con, JFrame parent) {
		setLayout(new MigLayout("", "[][41.00][43.00][45.00][106.00][126.00][53.00][52.00]", "[][40.00][][9.00][25.00][25.00][22.00][22.00][22.00][22.00][22.00][22.00][14.00][][][][]"));
		
		btnBack = new JButton("Back");
		add(btnBack, "cell 7 0");
		
		lblHeading = new JLabel("REGISTER HERE ");
		lblHeading.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		lblHeading.setForeground(Color.BLUE);
		add(lblHeading, "cell 4 2 2 1,alignx center");
		
		JLabel lblLoginId = new JLabel("Login ID *");
		add(lblLoginId, "cell 4 4,alignx left");
		
		tfLogin = new JTextField();
		add(tfLogin, "cell 5 4,growx");
		tfLogin.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name *");
		add(lblFirstName, "cell 4 5,alignx left");
		
		tfFirstName = new JTextField();
		add(tfFirstName, "cell 5 5,growx");
		tfFirstName.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name *");
		add(lblLastName, "cell 4 6,alignx left");
		
		tfLastName = new JTextField();
		add(tfLastName, "cell 5 6,growx");
		tfLastName.setColumns(10);
		
		JLabel lblSex = new JLabel("Sex *");
		add(lblSex, "cell 4 7,alignx left");
		
		String[] sexTypes = {"M", "F"};  
		listSex = new JList(sexTypes);
		listSex.setSelectedIndex(0);
		listSex.setVisibleRowCount(3);
		listSex.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollerSex = new JScrollPane(listSex);
		add(scrollerSex, "cell 5 7,grow");
		
		JLabel lblPassword = new JLabel("Password *");
		add(lblPassword, "cell 4 8,alignx left");
		
		pfPwd = new JPasswordField();
		add(pfPwd, "cell 5 8,growx");
		
		JLabel lblReenterPassword = new JLabel("Re-enter Password *");
		add(lblReenterPassword, "cell 4 9,alignx left");
		
		pfRePwd = new JPasswordField();
		add(pfRePwd, "cell 5 9,growx");
		
		JLabel lblEmail = new JLabel("Email Address *");
		add(lblEmail, "cell 4 10,alignx left");
		
		tfEmail = new JTextField();
		add(tfEmail, "cell 5 10,growx,aligny top");
		tfEmail.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		add(lblPhoneNumber, "cell 4 11,alignx left");
		
		tfPhone = new JTextField();
		add(tfPhone, "cell 5 11,growx");
		tfPhone.setColumns(10);
		
		lblDoNotMatch = new JLabel("Passwords do not match!!!");
		lblDoNotMatch.setForeground(Color.RED);
		add(lblDoNotMatch, "cell 4 12 2 1,alignx center");
		
		btnSubmit = new JButton("Submit");
		add(btnSubmit, "cell 4 13");
		
		btnCancel = new JButton("Cancel");
		add(btnCancel, "cell 5 13");
		
		lblPlease = new JLabel("Please Enter all the required fields");
		lblPlease.setForeground(Color.RED);
		add(lblPlease, "cell 4 14 2 1,alignx center");
		
		lblPlease.setVisible(false);
		
		lblSuccess = new JLabel("Registration  Successful!!!");
		lblSuccess.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSuccess.setForeground(Color.GREEN);
		add(lblSuccess, "cell 4 15 2 1,alignx center");
		lblSuccess.setVisible(false);
		
		lblDoNotMatch.setVisible(false);
		
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e){
				// TODO Auto-generated method stub
				System.out.println("Populating the database");
				
				String sex =  listSex.getSelectedValue().toString();
				System.out.println("Sex: "+sex);
				populateDatabase(sex, con);
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tfEmail.setText("");
				tfFirstName.setText("");
				tfLastName.setText("");
				tfLogin.setText("");
				tfPhone.setText("");
				pfPwd.setText("");
				pfRePwd.setText("");
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new Login(con);
			}
		});
	}
	
	
	public void populateDatabase(String sex, Connection con){
		
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		String patient = "P";
		
		try{
			con.setAutoCommit(false);
			pst1 = con.prepareStatement("insert into login values (?,?,?)");
			pst2 = con.prepareStatement("insert into patient values (?,?,?,?,?,?)");
			
			int pid = 0;
			String fn = null;
			String ln = null;
			String pwd = null;
			String email = null;
			String phone = null;
			
			if(!tfLogin.getText().equals(""))
				pid = Integer.parseInt(tfLogin.getText());
			if(!tfFirstName.getText().equals(""))
				fn = tfFirstName.getText();
			if(!tfLastName.getText().equals(""))
				ln = tfLastName.getText();
			if(!pfPwd.getPassword().equals(""))
				pwd = new String(pfPwd.getPassword());
			if(!tfEmail.getText().equals(""))
				email = tfEmail.getText();
			if(!tfPhone.getText().equals(""))
				phone = tfPhone.getText();
			
			
			if(pid==0 || fn==null || ln== null || pwd == null || email==null){
				lblPlease.setVisible(true);
			}
			else{
				//add code for checking password and repassword match
				String rePwd = new String(pfRePwd.getPassword());
				if(!rePwd.equals(pwd))
					lblDoNotMatch.setVisible(true);
				else{
					pid = Integer.parseInt(tfLogin.getText());
					fn = tfFirstName.getText();
					ln = tfLastName.getText();
					pwd = new String(pfPwd.getPassword());
					email = tfEmail.getText();
					phone = tfPhone.getText();
					
					pst1.setInt(1, pid);
					pst1.setString(2, patient);
					pst1.setString(3, pwd);
					pst1.executeUpdate();
						
					System.out.println("Login table updated");
					
					pst2.setInt(1, pid);
					pst2.setString(2, fn);
					pst2.setString(3, ln);
					pst2.setString(4, sex);
					pst2.setString(5, email);
					pst2.setString(6, phone);
					pst2.executeUpdate();
					con.commit();
					
					System.out.println("Patient table updated");
					lblSuccess.setVisible(true);					
				}
			}
		}
		catch(Exception e2){
			System.out.println("Error occured: "+e2);
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
