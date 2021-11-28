package gp.web.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Message {
	private String studentNum;
	private int roomNum;
	private int messageNum;
	private String text;
	private Timestamp time;
	
	public Message() {
		super();
	}

	public Message(String studentNum, int roomNum, int messageNum, String text, Timestamp time) {
		this.studentNum = studentNum;
		this.roomNum = roomNum;
		this.messageNum = messageNum;
		this.text = text;
		this.time = time;
	}

	public String getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public int getMessageNum() {
		return messageNum;
	}

	public void setMessageNum(int messageNum) {
		this.messageNum = messageNum;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	public String getStrTime()
	{
		return timestampToString();
	}
	public String timestampToString()
	{
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmm");
		String res = fm.format(time);
		
		return res;
	}
}
