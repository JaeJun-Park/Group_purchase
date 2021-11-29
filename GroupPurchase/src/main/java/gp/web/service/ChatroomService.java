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
import gp.web.entity.Message;
import gp.web.entity.Student;
import gp.web.entity.Chatroom;

public class ChatroomService {

	private Connection conn;
	
	public ChatroomService() {

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
	
	public ArrayList<Message> getChatListByRoomNum(int roomNum, int messageNum)
	{
		ArrayList<Message> chatList =  null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from Message where roomNum = ? AND messageNum >= ? order by roomNum";
		
		try
		{
			connectWithDB();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNum);
			pstmt.setInt(2, messageNum);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<Message>();
			while(rs.next())
			{
				
				String sNum = rs.getString(1);
				int rNum = rs.getInt(2);
				int mNum = rs.getInt(3);
				String text = rs.getString(4).replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll("\n", "<br>").replaceAll(">", "&gt;");
				Timestamp time = rs.getTimestamp(5);
				Message msg = new Message(
						sNum,
						rNum,
						mNum,
						text,
						time
					);
				chatList.add(msg);
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
		return chatList;
	}
	public ArrayList<Message> getChatListByRoomNumRecent(int roomNum, int recentNum)
	{
		ArrayList<Message> chatList =  null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from Message where roomNum = ? AND messageNum >= (select max(messageNum) - ? from Message) order by roomNum";
		
		try
		{
			connectWithDB();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNum);
			pstmt.setInt(2, recentNum);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<Message>();
			while(rs.next())
			{
				
				String sNum = rs.getString(1);
				int rNum = rs.getInt(2);
				int mNum = rs.getInt(3);
				String text = rs.getString(4).replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll("\n", "<br>").replaceAll(">", "&gt;");
				Timestamp time = rs.getTimestamp(5);
				Message msg = new Message(
						sNum,
						rNum,
						mNum,
						text,
						time
					);
				chatList.add(msg);
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
		return chatList;
	}
	public int getRecentMessageNum(int roomNum)
	{
		int mNum = 0; //초기값
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select max(messageNum) from message where roomnum = ?";
		try
		{
			connectWithDB();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNum);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				mNum = rs.getInt(1);
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
		return mNum;
	}
	public int submit(String studentNum, int roomNum, String chatContent)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO MESSAGE VALUES(?, ?, ?, ?, TO_DATE(?, 'yyyymmddhh24mi'))";
		int mNum = getRecentMessageNum(roomNum);
		mNum +=1;
		Timestamp time = new Timestamp(System.currentTimeMillis());
		String strTime = timestampToString(time);
		try
		{
			connectWithDB();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentNum);
			pstmt.setInt(2, roomNum);
			pstmt.setInt(3, mNum);
			pstmt.setString(4, chatContent);
			pstmt.setString(5, strTime);
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
	public String timestampToString(Timestamp time)
	{
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmm");
		String res = fm.format(time);
		
		return res;
	}
	public ArrayList<Message> getRoom(String studentNum)
	{
		ArrayList<Message> chatList =  null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * "
				+ "from message m2, (select m.roomNum, max(m.messageNum) num "
				+ "                    from participate p, message m "
				+ "                    where p.studentNum = ? "
				+ "                    and p.roomNum = m.roomNum "
				+ "                    group by m.roomNum) i "
				+ "where m2.roomNum = i.roomNum "
				+ "and   m2.messageNum = i.num "
				+ "order by time desc";
		
		try
		{
			connectWithDB();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentNum);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<Message>();
			while(rs.next())
			{
				
				String sNum = rs.getString(1);
				int rNum = rs.getInt(2);
				int mNum = rs.getInt(3);
				String text = rs.getString(4).replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll("\n", "<br>").replaceAll(">", "&gt;");
				Timestamp time = rs.getTimestamp(5);
				Message msg = new Message(
						sNum,
						rNum,
						mNum,
						text,
						time
					);
				chatList.add(msg);
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
		return chatList;
	}
	public int createChatRoom(int roomNum, int postNum)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO CHATROOM VALUES (?, ?)";
		try {
			connectWithDB();
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1,  roomNum);
			pstmt.setInt(2,  postNum);
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
	
	public int participateInChatRoom(int roomNum, String studentNum)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO PARTICIPATE VALUES(?, ?, TO_DATE(?, 'yyyymmddhh24mi'))";
		Timestamp time = new Timestamp(System.currentTimeMillis());
		String strTime = timestampToString(time);
		try {
			connectWithDB();
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1,  roomNum);
			pstmt.setString(2,  studentNum);
			pstmt.setString(3, strTime);
			return pstmt.executeUpdate();
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
