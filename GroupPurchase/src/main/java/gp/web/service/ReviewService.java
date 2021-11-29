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
	public String timestampToString(Timestamp time)
	{
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmm");
		String res = fm.format(time);
		
		return res;
	}
}
