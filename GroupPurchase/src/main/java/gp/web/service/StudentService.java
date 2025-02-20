package gp.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import gp.web.entity.Student;

public class StudentService 
{
	private Connection conn;
	
	public StudentService() {

	}

	private void connectWithDB()
	{
		try {
			InitialContext initCtx = new InitialContext();
			Context envContext = (Context) initCtx.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/GroupPurchase");
			conn = ds.getConnection();
			initCtx.close(); //?
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public Student getStudent(String field, String val)
	{
		Student stu = null;
		String sql = "select * from Student where " + field + " = ?";
		try
		{
			connectWithDB();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, val);
			ResultSet rs = st.executeQuery();

			if(rs.next())
			{
				String sNum = rs.getString(1);
				String name = rs.getString(2);
				String sId = rs.getString(3);
				String sPw = rs.getString(4);
				float cred = rs.getFloat(5);
				
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
	
	public Student login(String id, String pw)
	{

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Student stu = null;
		String sql = "SELECT * from STUDENT where ID = ? AND Password = ?";
		
		try
		{
			
			connectWithDB();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				String sNum = rs.getString(1);
				String name = rs.getString(2);
				String sId = rs.getString(3);
				String sPw = rs.getString(4);
				float cred = rs.getFloat(5);
				stu = new Student(
						sNum,
						name,
						sId,
						sPw,
						cred
					);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return stu;
	}
	
	public int IDCheck(String userID)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Select * from STUDENT where ID = ?";
		try {
			connectWithDB();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1,  userID);
			rs = pstmt.executeQuery();
			if(rs.next() || userID.equals(""))
			{
				return 0; //이미존제
			}
			else
			{
				return 1; //가입가능
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return -1; //디비오류
	}
	public int NumCheck(String sNum)
	{
		if(sNum.length() != 6 || !sNum.matches("20(.*)"))
			return 0; //잘못된 입력
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Select * from STUDENT where StudentNum = ?";
		try {
			connectWithDB();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1,  sNum);
			rs = pstmt.executeQuery();
			if(rs.next() || sNum.equals(""))
			{
				return 0; //이미존재
			}
			else
			{
				return 1; //가입가능
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return -1; //디비오류
	}
	public int register(String sNum, String name, String id, String pw)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO STUDENT VALUES (?, ?, ?, ?, ?)";
		try {
			connectWithDB();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1,  sNum);
			pstmt.setString(2,  name);
			pstmt.setString(3,  id);
			pstmt.setString(4,  pw);
			pstmt.setFloat(5, 0.0f);
			int count = pstmt.executeUpdate();
			return count;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return -1; //디비오류
	}
	
	public int update(String sNum, String name, String pw)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql =  "UPDATE STUDENT SET Name = ?, password = ? where StudentNum = ?";
		try {
			connectWithDB();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1,  name);
			pstmt.setString(2,  pw);
			pstmt.setString(3,  sNum);
			int count = pstmt.executeUpdate();
			return count;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return -1; //디비오류
	}
	public int updateCredibility(String sNum, Connection con)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql =  "UPDATE  Student "
				+ "SET     Credibility = (SELECT  AVG(R.Rating) "
				+ "FROM    Student S, REVIEW R "
				+ "WHERE   R.EvaluateeNum = S.StudentNum "
				+ "AND     S.StudentNum = ?) "
				+ "WHERE   StudentNum = ?";
		try {
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1,  sNum);
			pstmt.setString(2,  sNum);
			int count = pstmt.executeUpdate();
			return count;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return -1; //디비오류
	}
}
