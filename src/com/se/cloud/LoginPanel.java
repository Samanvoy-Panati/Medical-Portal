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
public class LoginPanel extends JPanel {
	
	private JTextField tfLoginId;
	private JPasswordField pfPwd;
	
	public LoginPanel(Connection con, JFrame parent) {
		setLayout(new MigLayout("", "[][51.00][45.00][41.00][63.00][59.00][38.00][][][][]", "[63.00][46.00][24.00][26.00][13.00][][13.00][][][25.00][]"));
		
		JLabel lblHeading = new JLabel("LOGIN ");
		lblHeading.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		lblHeading.setForeground(Color.BLUE);
		add(lblHeading, "cell 4 1 3 1,alignx center");
		
		JLabel lblLoginId = new JLabel("Login ID");
		add(lblLoginId, "cell 4 2,alignx left");
		
		tfLoginId = new JTextField();
		add(tfLoginId, "cell 5 2 2 1,growx");
		tfLoginId.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		add(lblPassword, "cell 4 3,alignx left");
		
		pfPwd = new JPasswordField();
		add(pfPwd, "cell 5 3 2 1,growx");
		
		JButton btnSubmit = new JButton("Submit");
		add(btnSubmit, "cell 4 5,alignx left");
		
		JButton btnCancel = new JButton("Cancel");
		add(btnCancel, "cell 5 5 2 1");
		
		JLabel lblNotRegisteredYet = new JLabel("Not registered yet?? Click");
		add(lblNotRegisteredYet, "cell 4 7 2 1,alignx left");
		
		JButton btnHere = new JButton("here");
		add(btnHere, "cell 6 7,alignx right");
		
		//Here button functionality
		btnHere.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.setVisible(false);
				new Register(con);
			}
		});
		
		JLabel lblUsername = new JLabel("Username or Password is incorrect..Please try again");
		lblUsername.setForeground(Color.RED);
		add(lblUsername, "cell 3 9 5 1");
		
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
					pst = con.prepareStatement("select id, password from login");
					int pid = Integer.parseInt(tfLoginId.getText());
					String pwd = new String(pfPwd.getPassword());
					//String pwd = ""+pfPwd.getPassword();
					System.out.println("inside loginpanel ... authenticating "+pid+" and "+pwd);
					//pst.setInt(1, id);
					//pst.setString(2, pwd);
					rs = pst.executeQuery();
					int flag=1;
					while(rs.next()){
						//open his profile page and give options to select
						System.out.println("I am in loginPanel while");
						System.out.println("result set first result "+rs.getInt(1)+", "+rs.getString(2));
						if(pid==rs.getInt(1) && pwd.equals(rs.getString(2)))
						{
							flag=0;
							System.out.println("Match Found");
							parent.setVisible(false);
							new PatientOptions(con, pid);
							break;
						}
					}
					//Actually no need for checking
					if (flag==1){
						System.out.println("I am in else");
						lblUsername.setVisible(true);
						
					}
					
				}
				catch(Exception e1){
					System.out.println("Error occured: "+e1);
				}
			}
		});
	}
	

}
