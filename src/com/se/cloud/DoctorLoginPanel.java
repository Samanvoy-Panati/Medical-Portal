package com.se.cloud;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import java.awt.Font;

@SuppressWarnings("serial")
public class DoctorLoginPanel extends JPanel {
	
	private JLabel lblLoginId;
	private JTextField tfLoginId;
	private JPasswordField pfPwd;
	private JButton btnSubmit;
	private JButton btnCancel;
	private JLabel lblUsername;
	
	public DoctorLoginPanel(Connection con, JFrame parent) {
		setLayout(new MigLayout("", "[][][59.00][52.00][][][][][][][][][][][][]", "[][63.00][][][][][][][][][][][][][][][][]"));
		
		JLabel lblHeading = new JLabel("DOCTOR LOGIN ");
		lblHeading.setForeground(Color.BLUE);
		lblHeading.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		add(lblHeading, "cell 3 3 7 1,alignx center");
		
		lblLoginId = new JLabel("Login ID");
		add(lblLoginId, "cell 5 5,alignx left");
		
		tfLoginId= new JTextField();
		add(tfLoginId, "cell 6 5,growx");
		tfLoginId.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		add(lblPassword, "cell 5 6,alignx left");
		
		pfPwd = new JPasswordField();
		add(pfPwd, "cell 6 6,growx");
		
		btnSubmit = new JButton("Submit");
		add(btnSubmit, "cell 5 8");
		
		btnCancel = new JButton("Cancel");
		add(btnCancel, "cell 6 8");
		
		lblUsername = new JLabel("Username or Password is incorrect...Please try again");
		lblUsername.setForeground(Color.RED);
		add(lblUsername, "cell 3 10 9 1");
		
		lblUsername.setVisible(false);
		
		
		
		//Submit button functionality
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Connection con = DBUtil.getDBCon();
				ResultSet rs = null;
				PreparedStatement pst = null;
				try{
					pst = con.prepareStatement("select id, password from login where type = 'D'");
					int did = Integer.parseInt(tfLoginId.getText());
					String pwd = new String(pfPwd.getPassword());
					//String pwd = ""+pfPwd.getPassword();
					System.out.println("inside doctor loginpanel ... authenticating "+did+" and "+pwd);
					//pst.setInt(1, id);
					//pst.setString(2, pwd);
					rs = pst.executeQuery();
					int flag=1;
					while(rs.next()){
						//open his profile page and give options to select
						System.out.println("result set first result "+rs.getInt(1)+", "+rs.getString(2));
						if(did==rs.getInt(1) && pwd.equals(rs.getString(2)))
						{
							flag=0;
							System.out.println("Match Found");
							parent.setVisible(false);
							new DoctorOptions(con, did);
							break;
						}
					}
					//Actually no need for checking
					if (flag==1){
						lblUsername.setVisible(true);
						
					}
					
				}
				catch(Exception e1){
					System.out.println("Exception occured in Doctor Login panel: "+e1);
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
