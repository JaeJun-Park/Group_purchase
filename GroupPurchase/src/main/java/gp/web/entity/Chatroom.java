package gp.web.entity;

public class Chatroom {
	private int roomNum;
	private	int postNum;
	
	public Chatroom() {

	}
	public Chatroom(int roomNum, int postNum) {
		this.roomNum = roomNum;
		this.postNum = postNum;
	}

	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	public int getPostNum() {
		return postNum;
	}
	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}
}
