package gp.web.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Participate {
	private int roomNum;
	private String studentNum;
	private String time; //필요하면 TimeStamp로 수정
	
	public Participate() {
		
	}
	
	public Participate(int roomNum, String studentNum, String time) {
		this.roomNum = roomNum;
		this.studentNum = studentNum;
		this.time = time;
	}

	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	public String getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String timestampToString(Timestamp Time)
	{
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmm");
		String res = fm.format(Time);
		
		return res;
	}
}
