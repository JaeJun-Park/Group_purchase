package gp.web.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import gp.web.entity.Review;

public class ReviewService {

	private Connection conn;
	
	public ReviewService() {

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
	
	public ArrayList<Review> getReviewListByEvaluateeNum(String studentNum)
	{
		ArrayList<Review> reviewList =  null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from review where evaluateeNum = ? order by reviewNum desc";
		
		try
		{
			connectWithDB();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentNum);
			rs = pstmt.executeQuery();
			reviewList = new ArrayList<Review>();
			while(rs.next())
			{
				
				int reviewNum = rs.getInt(1);
				String writer = rs.getString(2);
				String evaluatee = rs.getString(3);
				int postNum = rs.getInt(4);
				float rating = rs.getFloat(5);
				String comment = rs.getString(6).replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll("\n", "<br>").replaceAll(">", "&gt;");
				Timestamp time = rs.getTimestamp(7);
				Review rvw = new Review(
						reviewNum,
						writer,
						evaluatee,
						postNum,
						rating,
						comment,
						time
					);
				reviewList.add(rvw);
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
		return reviewList;
	}
	public ArrayList<Review> getReviewListByWriterNum(String studentNum)
	{
		ArrayList<Review> reviewList =  null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from review where writerNum = ? order by time desc";
		
		try
		{
			connectWithDB();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentNum);
			rs = pstmt.executeQuery();
			reviewList = new ArrayList<Review>();
			while(rs.next())
			{
				
				int reviewNum = rs.getInt(1);
				String writer = rs.getString(2);
				String evaluatee = rs.getString(3);
				int postNum = rs.getInt(4);
				float rating = rs.getFloat(5);
				String comment = rs.getString(6).replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll("\n", "<br>").replaceAll(">", "&gt;");
				Timestamp time = rs.getTimestamp(7);
				Review rvw = new Review(
						reviewNum,
						writer,
						evaluatee,
						postNum,
						rating,
						comment,
						time
					);
				reviewList.add(rvw);
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
		return reviewList;
	}
	public Review getReview(int reviewNum, String evaluateeNum)
	{
		Review rvw = null;
		String sql = "select * from review where reviewNum = ? and EvaluateeNum = ?";
		try
		{
			connectWithDB();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, reviewNum);
			st.setString(2, evaluateeNum);
			ResultSet rs = st.executeQuery();

			if(rs.next())
			{
				int rNum = rs.getInt(1);
				String writer = rs.getString(2);
				String evaluatee = rs.getString(3);
				int postNum = rs.getInt(4);
				float rating = rs.getFloat(5);
				String comment = rs.getString(6).replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll("\n", "<br>").replaceAll(">", "&gt;");
				Timestamp time = rs.getTimestamp(7);
				rvw = new Review(
						rNum,
						writer,
						evaluatee,
						postNum,
						rating,
						comment,
						time
					);
			}
			rs.close();
			st.close();
			conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return rvw;
	}
	public int writeReview(String writerNum, String evaluateeNum, int postNum, float rating, String comment)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO REVIEW VALUES(?, ?, ?, ?, ?, ?, TO_DATE(?, 'yyyymmddhh24mi'))";
		int reviewNum = getRecentReviewNum(evaluateeNum);
		reviewNum +=1;
		Timestamp time = new Timestamp(System.currentTimeMillis());
		String strTime = timestampToString(time);
		try
		{
			connectWithDB();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reviewNum);
			pstmt.setString(2, writerNum);
			pstmt.setString(3, evaluateeNum);
			pstmt.setInt(4, postNum);
			pstmt.setFloat(5, rating);
			pstmt.setString(6, comment);
			pstmt.setString(7, strTime);
			return pstmt.executeUpdate();
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
		
		return -1; //실패
	}
	
	public int getRecentReviewNum(String evaluateeNum)
	{
		int reviewNum = 0; //초기값
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select max(reviewNum) from review where evaluateeNum = ?";
		try
		{
			connectWithDB();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, evaluateeNum);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				reviewNum = rs.getInt(1);
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
		return reviewNum;
	}
	public int getReviewCount(String evaluateeNum)
	{
		int count = 0; //초기값
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select Count(*) from review where evaluateeNum = ?";
		try
		{
			connectWithDB();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, evaluateeNum);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				count = rs.getInt(1);
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
		return count;
	}
	public ArrayList<Review> getStudentNumCanWrite(String studentNum)
	{
		ArrayList<Review> reviewList =  null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select postNum, studentNum from join where postNum = '10' and studentNum != ? "
				+ "minus "
				+ "select postNum, evaluateeNum as studentNum from review where postNum = '10' AND writerNum = ?";
		
		try
		{
			connectWithDB();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentNum);
			pstmt.setString(2, studentNum);
			rs = pstmt.executeQuery();
			reviewList = new ArrayList<Review>();
			while(rs.next())
			{
				int postNum = rs.getInt(1);
				String sNum = rs.getString(2);
				Review rvw = new Review();
				rvw.setPostNum(postNum);
				rvw.setEvaluateeNum(sNum);
	
				reviewList.add(rvw);
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
		return reviewList;
	}
	public String timestampToString(Timestamp time)
	{
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmm");
		String res = fm.format(time);
		
		return res;
	}
}
