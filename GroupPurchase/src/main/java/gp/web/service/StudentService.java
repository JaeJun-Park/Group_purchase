package gp.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gp.web.entity.Student;

public class StudentService 
{
	public Student getStudent(String field, String val)
	{
		Student stu = null;
		String sql = "select * from Student where " + field + " = ?";
		System.out.println(sql);
		try
		{
			Connection conn = connectWithDB();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, val);
			ResultSet rs = st.executeQuery();

			if(rs.next())
			{
				String sNum = rs.getString("studentnum");
				String name = rs.getString("name");
				String id = rs.getString("id");
				String pw = rs.getString("password");
				float cred = rs.getFloat("credibility");
				
				stu = new Student(
						sNum,
						name,
						id,
						pw,
						cred
					);
			}
			rs.close();
			st.close();
			conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return stu;
	}
	
	public void signUp(String studentNum, String name, String id, String pw)
	{
		
	}
	
	public Student login(String id, String pw)
	{
		Student stu = null;
		String sql = "select * from Student where ID = ? AND Password = ?";
		System.out.println(sql);
		try
		{
			Connection conn = connectWithDB();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);
			st.setString(2, pw);
			ResultSet rs = st.executeQuery();

			if(rs.next())
			{
				String sNum = rs.getString("studentnum");
				String name = rs.getString("name");
				String sId = rs.getString("id");
				String sPw = rs.getString("password");
				float cred = rs.getFloat("credibility");
				
				stu = new Student(
						sNum,
						name,
						sId,
						sPw,
						cred
					);
			}
			rs.close();
			st.close();
			conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return stu;
	}
	
	private static Connection connectWithDB()
	{
		Connection conn = null;
		String serverIP = "localhost";
		String strSID = "orcl";
		String portNum = "1521";
		String user = "GroupPurchase";
		String pass = "1234";
		String url = "jdbc:oracle:thin:@" + serverIP +":"+portNum+":"+strSID;
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pass);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return conn;
	}
}
