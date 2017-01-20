package com.se.cloud;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBCon
{
	static Connection con=null;
	public static Connection getDBCon()
	{ 
	
		try{
			// Register the oracle driver.  
			Class.forName("com.mysql.jdbc.Driver");
			
			
			con = DriverManager.getConnection(
                    "jdbc:mysql://sql5.freesqldatabase.com/sql598767", "sql598767", "qyZyP5KCdj");
			
			System.out.println("connected");
			if(con!=null)
			{
				System.out.println("Connection obtained");
			}
			else
			{
				System.out.println("Connection not obtained");
			}
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		return con;
	}
	
	public static void close(Connection con)
	{
		try
		{
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	/*
	public static void main(String[] args)  {
		DBCon.getDBCon();
	}
	*/
}




